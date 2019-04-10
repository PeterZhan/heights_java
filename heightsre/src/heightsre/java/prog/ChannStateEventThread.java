package heightsre.java.prog;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.*;

public class ChannStateEventThread extends Thread {
	
	NewStateEvent nse;
	public ChannStateEventThread(NewStateEvent e)
	{
		nse = e;
	}
	
	public void run()
	{
		
		String wcbid = "";
		
		
		wcbid = AstConnection.getVariable(nse.getChannel(), "WBCBID");
		
		
		if (wcbid == null || wcbid.equals("") || wcbid.equals("(null)"))
			return;
		
		
		String chanUniqueid = nse.getUniqueId();
		String chanName = nse.getChannel();
		String chanStatus = nse.getChannelStateDesc();
		String chanExten = AstConnection.getVariable(nse.getChannel(), "WBCBEXTEN");
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
		
		
		
		
		
		
	}
	

}
