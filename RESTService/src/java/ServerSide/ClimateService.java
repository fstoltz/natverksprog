/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;


import java.sql.SQLException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author fstoltz
 */

@Path("/ClimateService")
public class ClimateService {
    
    // I would like to make this into a singleton
    Panel mainPanel = new Panel(); 
    
    @GET
    @Path("/snapshotnow")
    @Produces(MediaType.TEXT_PLAIN)
    public int snapshotNow() throws ClassNotFoundException, SQLException{//forcelog is set to 1 during manual log entry, and 0 at automatic log entry
        return mainPanel.snapshotNow();
    }
    
    
    @GET
    @Path("/getsnapshots/limit/{lim}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSnapshots(@PathParam("lim") int limit) throws ClassNotFoundException, SQLException{//forcelog is set to 1 during manual log entry, and 0 at automatic log entry
        return mainPanel.getSnapshots(limit);
    }
    
    
    @GET
    @Path("/logtempnow/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public String logTempNow(@PathParam("value") int newTemp) throws ClassNotFoundException, SQLException{//forcelog is set to 1 during manual log entry, and 0 at automatic log entry
        return "Rows inserted: " + mainPanel.logVal(newTemp, "templogs");
    }
    
    
    @GET
    @Path("/loghumnow/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public String logHumNow(@PathParam("value") int newHum) throws ClassNotFoundException, SQLException{//forcelog is set to 1 during manual log entry, and 0 at automatic log entry
        if(mainPanel.logVal(newHum, "humlogs") == 1){
            return "New humidity has been set!";
        } else {
            return "Something went wrong when trying to set new humidity.";
        }
    }
    
    
    @GET
    @Path("/loglightnow/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public String logLightNow(@PathParam("value") int newLight) throws ClassNotFoundException, SQLException{//forcelog is set to 1 during manual log entry, and 0 at automatic log entry
        if(mainPanel.logVal(newLight, "lightlogs") == 1){
            return "New dimmer-value has been set!";
        } else {
            return "Something went wrong when trying to set new light value.";
        }
    }
    
    
    @GET
    @Path("/viewall")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewAll() throws ClassNotFoundException, SQLException{
        return mainPanel.viewAll() + "\n\n" + mainPanel.getWhToday();
    }
    
    
    @GET
    @Path("/energytoday")
    @Produces(MediaType.TEXT_PLAIN)
    public String WhToday() throws ClassNotFoundException, SQLException{
        return mainPanel.getWhToday();
    }
    
    
    @GET
    @Path("/humreport")
    @Produces(MediaType.TEXT_PLAIN)
    public String humReport() throws ClassNotFoundException, SQLException{
        return mainPanel.weeklyReport("humlogs");
    }
    
    
    @GET
    @Path("/lightreport")
    @Produces(MediaType.TEXT_PLAIN)
    public String lightReport() throws ClassNotFoundException, SQLException{
        return mainPanel.weeklyReport("lightlogs");
    }
    
    
    @GET
    @Path("/tempreport")
    @Produces(MediaType.TEXT_PLAIN)
    public String tempReport() throws ClassNotFoundException, SQLException{
        return mainPanel.weeklyReport("templogs");
    }
}