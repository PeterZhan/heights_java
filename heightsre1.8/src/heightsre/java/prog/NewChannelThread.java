package heightsre.java.prog;


import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.*;
import org.asteriskjava.manager.response.ManagerResponse;
import org.asteriskjava.manager.action.*;
public class NewChannelThread extends Thread {
	
	private NewChannelEvent nchEvent;
	public NewChannelThread(NewChannelEvent nce)
	{
		nchEvent = nce;
	}
	
	
	
	private void playBeepOn(String chann)
	{
		
		OriginateAction oa = new OriginateAction();
		oa.setChannel("LOCAL/s@monitorbeep/n");
		oa.setContext("playbeepmoni");
		oa.setExten("s");
		oa.setPriority(1);
		oa.setVariable("SPYCHAN",chann);
		
		try{
		   	  ManagerResponse response = AstConnection.managerConnection.sendAction(oa);
		 	   
		       	
		       	 
		    }
		    catch (Exception e){	 };
		   		
		    oa = null;
		
		
	}
	
	public void run()
	{
		
		
		String chann = nchEvent.getChannel();
		
		if (chann.startsWith("SIP/xo"))
		{
			playBeepOn(chann);
			
			
			
			
			
			
		}
		
		
	/*	
		
		
		
		String wcbid = "";
		
		
		  wcbid = AstConnection.getVariable(chann, "WBCBID");
		
		  System.out.println(nchEvent + ":");
		  System.out.println(wcbid);
		  System.out.println("*********");
		
		if (wcbid == null || wcbid.equals("") || wcbid.equals("(null)"))
			return;
		
		
		String chanUniqueid = nchEvent.getUniqueId();
		String chanName = nchEvent.getChannel();
		String chanStatus = nchEvent.getChannelStateDesc();
		String chanExten = AstConnection.getVariable(nchEvent.getChannel(), "WBCBEXTEN");
		String chanwcbid = wcbid;
		
		
		String cmd = "SETWCBSTATUS";
		String[] opts = new String[5];
		opts[0] = chanUniqueid;
		opts[1] = chanName;
		opts[2] = chanStatus;
		opts[3] = chanExten;
		opts[4] = chanwcbid;
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		   
	     try{
	    	 
	    	 AstPortalProxy service = new AstPortalProxy();  
	 		 service.setEndpoint(url);
	    	 
		    
		      
		  
		      
		      String[] response = service.generalCommand(cmd, opts);
		
		      
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
