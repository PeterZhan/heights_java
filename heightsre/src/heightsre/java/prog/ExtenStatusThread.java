package heightsre.java.prog;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.*;

public class ExtenStatusThread extends Thread {
	
	ExtensionStatusEvent ExStatusEvent;
	public ExtenStatusThread(ExtensionStatusEvent e)
	{
		ExStatusEvent = e;
	}
	
	public void run()
	{
		String exten = ExStatusEvent.getExten();
		
		
		
		exten = exten.replace("SIP/", "");
		int s = ExStatusEvent.getStatus();
		
		String status = "";
		boolean newReg = false;
		
		switch (s) 
		{
		  case ExtensionStatusEvent.BUSY :
		  {
			  status = "BUSY";
			  break;
		  }
	      
		  case ExtensionStatusEvent.INUSE :
		  {
			  status = "INUSE";
			  break;
			  
		  }
		
		  case ExtensionStatusEvent.NOT_INUSE :
		  {
			  status = "IDLE";
			  break;
			  
		  }
		  
		  case ExtensionStatusEvent.RINGING :
		  {
			  status = "RINGING";
			  
			//  if (exten.equals("625"))
			  {
			    String dbtree = "BLKVM/" + exten;
			    heightsReMain.astConn.exeCommand("database deltree " + dbtree);
			  }
			  break;
			  
		  }
		  
		  case ExtensionStatusEvent.UNAVAILABLE :
		  {
			  status = "UNAVAILABLE";
			  break;
			  
		  }
		  case ExtensionStatusEvent.INUSE | ExtensionStatusEvent.RINGING :
		  {
			  
			  status = "INUSE&RINGING";
			  break;
			  
		  }
		  default :
		  {
			  status = "UNKNOWN: " + s;
			  break; 
		  }
		
		
		}
		
	//	System.out.println("extension:" + exten + "status:" + status);
		heightsReMain.exstatus.setExtenStatus(exten, status);
		
	/*	
		String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		   
	     try{
	    	 
	    	 
	    	 
	    	 AstPortalProxy service = new AstPortalProxy();  
	 		 service.setEndpoint(url);
	    	 
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
		
		*/
		
		
	}

}
