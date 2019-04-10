package heightsre.java.prog;

import heightsre.java.fastagi.client.NotesWSClient;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.MeetMeLeaveEvent;

public class newSessEventThread extends Thread {
	
	NEWSESSEvent nse;
	
	public newSessEventThread(NEWSESSEvent e)
	{
		nse = e;
	}
	
	public void run()
	{
		String confnum = nse.getConf();
		
		String chann = nse.getChannel();
		
		
		System.out.print("Conf Number: " + confnum);
		System.out.print("Channel: " + chann);
		String rec = AstConnection.getVariable(chann, "MEETME_RECORDINGFILE");
		
		System.out.print("record: " + rec);
		
		AstConnection.setGlVariable("conf" + confnum + "rec", rec);
		
		
		String cmd = "NEWCONFSESS";
		String[] opts = new String[1];
		opts[0] = confnum;
		
		 
		
		
		String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		   
	     try{
	    	 NotesWSClient wsclient = new NotesWSClient(url);
	    //	 AstPortalProxy service = new AstPortalProxy();  
	 	//	  service.setEndpoint(url);
	    	 
		      String[] response = wsclient.generalCommand(cmd, opts);
		
		      AstConnection.setGlVariable("conf" + confnum + "sid", response[0]);
		      
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
