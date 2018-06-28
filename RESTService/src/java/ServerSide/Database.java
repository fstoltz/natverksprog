/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author fstoltz
 */
public class Database { //database could also be a singleton possibly
        
    public Database(){
        //empty constructor
    }
    
    public Connection connectToDB() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con;
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/climatelogs", "root", "nacka17");             
        return con;
    }

    
    //Logs temp (no specific bed, i changed design)
    public int logVal(int val, String type){
        int rowsAff = 0;
        try(Connection con = this.connectToDB()){
            Statement stmt = con.createStatement();
            rowsAff = stmt.executeUpdate("INSERT INTO "+type+" (value, created) VALUES ('"+val+"', '"+(Date.valueOf(LocalDate.now()))+"');");
        } catch(Exception e){e.printStackTrace();}
        return rowsAff;
    }
    
    public String getLatestVals(){
        String str = "";
        try(Connection con = this.connectToDB()){
            
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            ResultSet rs;
            
            rs = stmt1.executeQuery("SELECT * FROM templogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){str += "Temperature: \t\t" + rs.getString("value") + " Celsius";}
            
            rs = stmt2.executeQuery("SELECT * FROM humlogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){str += "\n\nHumidity: \t\t" + rs.getString("value") + "%";}
            
            rs = stmt3.executeQuery("SELECT * FROM lightlogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){str += "\n\nLight value: \t\t" + rs.getString("value") + "W";}
            
        } catch(Exception e){e.printStackTrace();}
        return str;
    }
    
    
    public String getWHToday(){
        String str = "";
        try(Connection con = this.connectToDB()){
            
            Statement stmt = con.createStatement();
            ResultSet rs;
            double wattRating = 0;
            
            rs = stmt.executeQuery("SELECT * FROM lightlogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){wattRating = rs.getInt("value");}
            
            double hoursPassed = LocalDateTime.now().getHour();
            
            str += "Energy used(today): \t" + (hoursPassed * wattRating)/1000 + " kWh";
            
            
        } catch(Exception e){e.printStackTrace();}
        return str;
    }
    


    public String weeklyReport(String type){
        String str = "";
        char symbol = 'E';
        //Set appropriate symbols for return string depending on type of request
        if(type=="humlogs"){symbol='%';}
        else if(type=="templogs"){symbol='C';}
        else if(type=="lightlogs"){symbol='W';}
        int count = 0, total = 0;
        try(Connection con = this.connectToDB()){
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+type+" WHERE created >= DATE_ADD(CURDATE(), INTERVAL -6 DAY) GROUP BY created;");
            
            while(rs.next()){
                int val = rs.getInt("value");
                str += rs.getDate("created") + " -> ";
                str += val;
                str += symbol + "\n";
                total += val;
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        double average = (total/count); //Calculate average for this week
        
        if(type == "lightlogs"){ //If this is a request for lightlogs, add the Cost for the latest week aswell
            str += "\nCost\t: " + ((average * 168) / 1000) * 1.2 + "SEK";
        }
        
        return str + "\nAverage value: " + average;
    }



    public int snapshotAllParameters(){
        //String str = "";
        int rowsAff = 0;
        
        try(Connection con = this.connectToDB()){

            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            Statement stmt4 = con.createStatement();
            
            int tempVal = 0, humVal = 0, lightVal = 0;
            
            ResultSet rs = stmt2.executeQuery("SELECT * FROM templogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){tempVal = rs.getInt("value");}
            
            rs = stmt3.executeQuery("SELECT * FROM humlogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){humVal = rs.getInt("value");}
            
            rs = stmt4.executeQuery("SELECT * FROM lightlogs ORDER BY id DESC LIMIT 1;");
            if(rs.next()){lightVal = rs.getInt("value");}
            
            
            stmt1 = con.createStatement();
            //LocalDateTime today = LocalDateTime.now();
            rowsAff = stmt1.executeUpdate("INSERT INTO snapshots (tempval, humval, lightval) VALUES ('"+tempVal+"', '"+humVal+"', '"+lightVal+"');");
            
            
            
            
        } catch(Exception e){e.printStackTrace();}
        
        return rowsAff;
    }
    
    
    public String getSnapshots(int limit) throws ClassNotFoundException, SQLException{ //limit sets how many snapshots the user want to retreive
        String str = "";
        try(Connection con = this.connectToDB()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM snapshots ORDER BY id DESC LIMIT "+limit+";");
            while(rs.next()){
                str += "\n";
                str += "_________________________________\n";
                Timestamp created = rs.getTimestamp("created");
                
                str += created + "\n_________________________________\n";
                str += "Temperature\t:\t " + rs.getString("tempval") + "C\n";
                str += "Humidity\t:\t " + rs.getString("humval") + "%\n";
                str += "Light\t\t:\t " + rs.getString("lightval") + "W\n";
                str += "_________________________________\n\n";
            }
        }
        return str;
    }
}
