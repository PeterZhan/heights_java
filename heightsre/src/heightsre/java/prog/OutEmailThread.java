package heightsre.java.prog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;

import org.asteriskjava.manager.event.HangupEvent;

public class OutEmailThread extends Thread {
	
	HangupEvent he = null;
	String  uid = "";
	
	public OutEmailThread(HangupEvent e)
	{
		he = e;
		uid = he.getUniqueId();
	}
	
	public void run()
	{
		
		trySleep(10000);
		
        Connection con = null;
        
        
        
        
        
	    
        String cmd = "/var/javalib/sendmailoutboundcall.sh";
		
		String[] cmds = new String[5];
		String sqlstr = "select userfield, src, dst, duration from cdr where accountcode=\'OUT\' "
			          + " and uniqueid=\'" + uid + "\'";

        try{
          con = DbConnection.getConnection();
          Statement stat = con.createStatement();
          ResultSet result = stat.executeQuery(sqlstr);
          
          if (result.next()){
        	
		      cmds[0] = cmd;
	 	      cmds[1] = result.getString("userfield") + ".WAV";
	 	      
	 	      
	 	     File f = new File("/var/spool/asterisk/monitor/" + cmds[1]);
	 	     
	 	     if (!f.exists()) return;
	 	      
	 	      
		      cmds[2] = result.getString("src");
		      
		      if (cmds[2].startsWith("64657264"))
		      {
		    	  cmds[2] = "6" + cmds[2].substring(8);
		      }
		      
		      cmds[3] = result.getString("dst");
		      
		      
		      cmds[4] = result.getString("duration");
		
	    			    
		
            try{
	       
	           Process proc =  Runtime.getRuntime().exec(cmds);

	        
               if (proc != null)
               {
            	  proc.waitFor();
               }


	        
	        
             }catch(Exception e)
              {
        	   System.out.println(e);
              }
	    
          } else
          {
        	  tryInternal(); 
          }
        }catch(Exception e)
        {
        	System.out.println(e);
        }
        finally
        {
        	DbConnection.FreeCon(con);
        
        	
        }
		
		
		
	}
	
	private void trySleep(long millsecs)
	{
		try{
			Thread.sleep(millsecs);
			
			
		}catch(Exception e)
		{
			
		}
		
		
		
	}
	
	
	
	private void tryInternal()
	{
        Connection con = null;
	    
        String cmd = "/var/javalib/sendmailinternal.sh";
		
		String[] cmds = new String[5];
		String sqlstr = "select userfield, src, dst, duration from cdr where accountcode=\'IN\' "
			          + " and uniqueid=\'" + uid + "\'";

        try{
          con = DbConnection.getConnection();
          Statement stat = con.createStatement();
          ResultSet result = stat.executeQuery(sqlstr);
          
          if (result.next()){
        	
		      cmds[0] = cmd;
	 	      cmds[1] = result.getString("userfield") + ".WAV";
		      cmds[2] = result.getString("src");
		      
		      if (!cmds[1].contains("internal")) return;
		      
		      if (cmds[2].startsWith("64657264"))
		      {
		    	  cmds[2] = "6" + cmds[2].substring(8);
		      }
		      
		      cmds[3] = result.getString("dst");
		      
		      
		      cmds[4] = result.getString("duration");
		
	    			    
		
            try{
	       
	           Process proc =  Runtime.getRuntime().exec(cmds);

	        
               if (proc != null)
               {
            	  proc.waitFor();
               }


	        
	        
             }catch(Exception e)
              {
        	   System.out.println(e);
              }
	    
          }
        }catch(Exception e)
        {
        	System.out.println(e);
        }
        finally
        {
        	DbConnection.FreeCon(con);
        
        	
        }
	}
	

}
