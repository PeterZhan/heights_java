package heightsre.java.prog;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.devdaily.opensource.database.DDConnectionBroker;


  
public class DbConnection {
	
	 static DDConnectionBroker broker = null;
	//public static Connection con = null;
	/**
    Gets a connection from the properties specified
    in the file database.properties
    @return the database connection
 */
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
    if (broker == null)
    {
    /*    Properties props = new Properties();
        FileInputStream in = new FileInputStream(pfile);
        props.load(in);
        in.close();*/
        String drivers = heightsReMain.cfg.drivers;;
   /*     if (drivers != null) 
 	    try{
 	      Class.forName(drivers);
 	    }
        catch(ClassNotFoundException e){return null;} */
 	//  System.setProperty("jdbc.drivers", drivers);

        String url = heightsReMain.cfg.url;
        String username = heightsReMain.cfg.dbusername;
        String password = heightsReMain.cfg.dbpassword;
        
    //    props = null;
   //     in = null;
        
        int minConnections        = 1;
        int maxConnections        = 10;
        long timeout              = 100;
        long leaseTime            = 60000;
        String logFile            = "/var/javalib/DbConBroker.log";

        
     
       try{ 
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
     
     
     public static void sqlExecute(String s)
     {
    	 Connection con = null;
		 try
		 {	 
		   con = DbConnection.getConnection();
					 
		   
		   Statement stat = con.createStatement();
		   stat.execute(s);	
		   stat.close();
			  
		 }catch(Exception e)
		 {
						
		   System.out.println(e);
		//   ServerLog.AppendLog(e);
		 }
		 finally
		 {
		   DbConnection.FreeCon(con);
		 }	   
    	 
    	 
    	 
    	 
    	 
    	 
    	 
     }
	 
	
   //  public final static String pfile = "/etc/cticonfigure/logtodb.properties";
}
