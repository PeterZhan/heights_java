package heightsre.java.prog;

import org.asteriskjava.manager.event.*;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MeetMeLeaveEventThread extends Thread {
private MeetMeLeaveEvent mle;
	
	public MeetMeLeaveEventThread(MeetMeLeaveEvent e)
	{
		mle = e;
	}
	
	public void run()
	{   
		
		String confnum = mle.getMeetMe();	
		
		List<String> cmdres = AstConnection.exeCommand("meetme list " + confnum);
		
		for (String l: cmdres)
		{
			if (l.contains("No active MeetMe conferences"))
			{
				
				String meetmerec = AstConnection.getGlVariable("conf" + confnum + "rec") + ".WAV";
				
				AstConnection.setGlVariable("conf" + confnum + "rec", "");
			//	String meetsid = AstConnection.getGlVariable("conf" + confnum + "sid");
		   //		AstConnection.setGlVariable("conf" + confnum + "sid", "");
				
				
				try{
				File f = new File(meetmerec);
				
			    long fsize = f.length();
			   	byte[] data = new byte[(int)fsize];
			   	
			    FileInputStream fin = new FileInputStream(f);
			   	
			    fin.read(data);
			   	
			   	fin.close();
				
				
				
				
				String cmd = "ENDCONFSESS";
				String[] opts = new String[2];
				opts[0] = confnum;
				opts[1] = f.getName();
				
				
				 
				
				
				String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
				   
			     
			    	 
			    	 AstPortalProxy service = new AstPortalProxy();  
			 		  service.setEndpoint(url);
			    	 
				      String[] response = service.generalCommand(cmd, opts, data);
				
			//**********************************
			
				      
				      
		  		      
				      
				      
				      
				      
				      
				      
				
				
				
				
			     }   
			     catch(Exception e)
			     {
			    	System.out.println(e); 
			    	e.printStackTrace();
			     }finally
			     {
			    	 
			     }
				
				
				
				
				
				
				
				
				
				
				break;
			}
		}
		
		String chan = mle.getChannel();
		
		String callid = getExtenFromChan(chan);;
		
		String  cmd = "LEAVECONFERENCE";
		String[] opts = new String[2];	
			opts[0] = confnum;
			opts[1] = callid;
			
			String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
			   
		     
	    	 
	    	 AstPortalProxy service = new AstPortalProxy();  
	 		  service.setEndpoint(url);
							
			
		    	 
		   
		    try{
		 
	 		 String[] response = service.generalCommand(cmd, opts);
		    }catch(Exception e)
		    {
		    	
		    }
	      
		
		
		String exten = getExtenFromChan(chan);
		
		if (exten.length() == 3)
		{
			
			String status = "OUTCONF";
		
			
			String exeSql = "update extenstatus set exstatus=" + "\'" + status + "\' " +
                            "where exten=\'" + exten + "\'";
			
			
			DbConnection.sqlExecute(exeSql);
			
			
		}
		
		
		
	}
	
	
	private String getExtenFromChan(String chan)
	{
		String ret = "";
		int p1=chan.indexOf('/');
		int p2=chan.indexOf('-');
		
		if (p2!=-1 && p1!=-1 && p2>p1)
		{
			ret = chan.substring(p1+1, p2);
		}
		
		
		
		return ret;
		
	}

}
