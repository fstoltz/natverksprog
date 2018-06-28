package ServerSide;

import java.sql.SQLException;

/**
 * 
 * @author fstoltz
 */
public class Panel {
    private Database db; 
    Panel() {
        // The panel is the one that interfaces with the SQL database
        this.db = new Database();
    }
    
    /**
     * 
     * @param limit i.e. limit = 4, means that the last four snapshots will be returned
     * @return a string containing formatted information about the snapshots
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public String getSnapshots(int limit) throws ClassNotFoundException, SQLException{
        return this.db.getSnapshots(limit);
    }
    
    /**
     * 
     * @return int representing number of rows affected
     */
    public int snapshotNow(){
        return(this.db.snapshotAllParameters());
    }
    
    /**
    * @param val the value to log
    * @param type type of value
    * @return an int representing amount of rows affected
    */
    public int logVal(int val, String type){
        return (this.db.logVal(val, type));
    }
    
    
    /**
     * 
     * @return a string containing formatted info about the latest log in the tables holding parameter data
     */
    public String viewAll(){
        return this.db.getLatestVals();
    }
    
    
    /**
     * 
     * @return a formatted string containing info about number of Wh used today
     */
    public String getWhToday(){
        return this.db.getWHToday();
    }
    
    
    /**
     * 
     * @param type String that should represent the name of the SQL table to retrieve values from.
     * @return a formatted String containing info about last 7 days
     */
    public String weeklyReport(String type){
        return this.db.weeklyReport(type);
    }
}
