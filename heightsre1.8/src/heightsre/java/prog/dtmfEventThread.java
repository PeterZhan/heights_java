package heightsre.java.prog;

import java.rmi.RemoteException;

import heightsre.java.fastagi.client.DbConnectionApache;
import heightsre.java.fastagi.client.NotesWSClient;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.DtmfEvent;

public class dtmfEventThread extends Thread {
	DtmfEvent de;
	
	
	//private AstPortalProxy service = new AstPortalProxy();
	String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	NotesWSClient wsclient;// = new NotesWSClient(url);
	
	public dtmfEventThread(DtmfEvent de){
		//service.setEndpoint(url);
		wsclient = new NotesWSClient(url);
		this.de = de;
	}

	
	
	public void run()
	{
	    String uid = de.getUniqueId();
	    String digit = de.getDigit();
	    String direction = de.getDirection();
	    String dtime = "" + de.getDateReceived();
	    
	    
	    String cmd = "SENDDTMFDIGITS";	
		String[] opts = new String[4];
		opts[0] = uid;
		opts[1] = digit;
		opts[2] = direction;
		opts[3] = dtime;
		
		try {
			wsclient.generalCommand(cmd, opts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		String log = dtime + " " + direction + " " + digit + " \\\\n";
		
		
		String sql = "update tblcalleropts set DtmfEvents=CONCAT(IFNULL(DtmfEvents,''), '" + log + "'), LastModified=NOW() where AstUniID='"
				   + uid + "'";
		
		DbConnectionApache.sqlExecute(sql);
		
		
	}
}
