package heightsre.java.prog;

import heightsre.java.fastagi.client.NotesWSClient;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.*;

public class MeetMeJoinEventThread extends Thread {
	private MeetMeJoinEvent mje;
	
	public MeetMeJoinEventThread(MeetMeJoinEvent e)
	{
		mje = e;
	}
	
	public void run()
	{
		
		
		String chan = mje.getChannel();
		String exten = getExtenFromChan(chan);
		
		System.out.println(exten + " join the meeting");
		
		
		
		String callid = exten; //mje.getChannel();
		String confnum = mje.getMeetMe();
		
		String cmd = "JOINCONFERENCE";
		String[] opts = new String[2];
		opts[0] = confnum;
		opts[1] = callid;
		
		
		 
		
		
		String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		   
	     
	    	 
	   // AstPortalProxy service = new AstPortalProxy();  
	 //	service.setEndpoint(url);
		NotesWSClient wsclient = new NotesWSClient(url);
	    
	 	try{
		  String[] response = wsclient.generalCommand(cmd, opts);
		
	 	}catch(Exception e)
	 	{
	 		
	 	}
		
		
		
		if (exten.length() == 3)
		{
			
			String status = "INCONF";
		
			
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
