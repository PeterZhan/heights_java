package heightsre.java.fastagi.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.devdaily.opensource.database.DDConnectionBroker;


//mysql database connection spool  
public class DbConnectionApache {
	
	 static DDConnectionBroker broker = null;// the connection broker
	 static Config cfg = null;  // configuration for mysql connection
	//public static Connection con = null;
	
	 // release the mysql connction from the spool
	 public static void FreeCon(Connection con){
		 if (broker != null){
			try{
			 broker.freeConnection(con);
			}catch(Exception e){
					
				System.out.println(e);
			//	ServerLog.AppendLog(e);
			}
		 }
	 }
	 
	 
	 /**
	    Gets a connection from the properties specified
	    in the file database.properties
	    @return the database connection
	 */
     public static Connection getConnection()
        throws SQLException, IOException
     {
    	
    /*	 
    	if (con != null ){
    		if (!con.isClosed())
    		{
    			System.out.println("using the same connection!!");
    			return con;
    		}
    			
    	}
    	 */
    // if broker is null, then this is the first database connection. 	 
    if (broker == null)
    {
    /*    Properties props = new Properties();
        FileInputStream in = new FileInputStream(pfile);
        props.load(in);
        in.close();*/
    	if (cfg == null) // get configuration from configuration file
    	{
    	  cfg = new Config();
    	  cfg.getParams();
    	}
    	
        String drivers = cfg.drivers;// jdbc driver
   /*     if (drivers != null) 
 	    try{
 	      Class.forName(drivers);
 	    }
        catch(ClassNotFoundException e){return null;} */
 	//  System.setProperty("jdbc.drivers", drivers);

        String url = cfg.url_apa;  // mysql connection url
        String username = cfg.dbusername_apa;  // mysql connection username
        String password = cfg.dbpassword_apa;  // mysql connection password
        
    //    props = null;
   //     in = null;
        
        int minConnections        = 1;  // the minimal connection
        int maxConnections        = 10; // the maximum connection
        long timeout              = 100;  // time for connect
        long leaseTime            = 60000;  // time to keep connection
        String logFile            = "/var/javalib/DbConBrokerApache.log";  // log file

        
     
       try{ // create the broker for the mysql connection spool
        broker = new DDConnectionBroker(drivers,
                url,
                username,
                password,
                minConnections,
                maxConnections,
                timeout,
                leaseTime,
                logFile);
        }
        catch (SQLException se)
       {
        //	ServerLog.AppendLog(se);	
        // could not get a broker; not much reason to go on
        System.out.println( se.getMessage() );
        System.out.println( "Could not construct a broker, quitting." );
        broker = null;
        return null;
 
       }
        
     } 
        
        
        // get one connection from the mysql spool
        Connection con = null;
        try{
           con = broker.getConnection();
        }catch (Exception e){
        	
        	
          con = null;
          System.out.println(e);
       //   ServerLog.AppendLog(e);	
        	
        }
        return con;
     }
     
    // execute one sql statement 
     public static void sqlExecute(String s)
     {
    	 Connection con = null;
		 try
		 {	 // get one connection
		   con = DbConnectionApache.getConnection();
					 
		   // get one statement
		   Statement stat = con.createStatement();
		  
		   // execute the sql
		   stat.execute(s);	
		   stat.close();
			  
		 }catch(Exception e)
		 {
						
		   System.out.println(e);
		//   ServerLog.AppendLog(e);
		 }
		 finally  // in finally block to ensure the connection is release from the spool.
		 {
		   DbConnectionApache.FreeCon(con);
		 }	   
    	 
    	 
    	 
    	 
    	 
    	 
    	 
     }
	 
	
   //  public final static String pfile = "/etc/cticonfigure/logtodb.properties";
}
