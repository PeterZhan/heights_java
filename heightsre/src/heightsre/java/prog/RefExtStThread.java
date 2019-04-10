package heightsre.java.prog;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.util.*;

public class RefExtStThread extends Thread {
	
	private String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		
	private AstPortalProxy service = new AstPortalProxy();
	
	public RefExtStThread()
	{
          
		service.setEndpoint(url);
	}
	
	private void setAllExtenStatus()
	{
		
      
		 HashMap<String, String> exStatus= heightsReMain.exstatus.getAllExtenStatus();
		
		
		if (exStatus.isEmpty())
			{
			
			//System.out.println("extension status array is empty!");
			return;
			}
		
		System.out.println("extension status array NOT empty!");
			
		 for   (Object o  : exStatus.keySet()){  
			  String exten = (String)o;
			  String status= (String)exStatus.get(o);    
			
			  System.out.println("send " + exten + " : " + status);
			
			 try{
		    	 
		    	 
		     	 
			      String cmd = "SETEXTENSTATUS";
			      String[] opt = new String[2];
			      
			      opt[0] = exten;
			      opt[1] = status;
			      
			      System.out.println(exten + " " + status);
			      
			      String[] response = service.generalCommand(cmd, opt);
			
			      
			      System.out.println(response[0]);
			
			     
			
			
		     }   
		     catch(Exception e)
		     {
		    	System.out.println(e); 
		    	e.printStackTrace();
		     }finally
		     {
		    	 
		     }
		
		
		
		
		
		}

		
		
		
		exStatus = null;
		
		
		
		
		
		
	}
	
	
	
	public void run()
	{
		while(true)
		{
			
			setAllExtenStatus();
			
			try{
			  Thread.sleep(1000);
			}catch(Exception e)
			{
				
			}
		}
		
	}

}
