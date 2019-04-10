package heightsre.java.prog;

import java.rmi.RemoteException;

import heightsre.java.fastagi.client.NotesWSClient;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.*;

public class vmNewMessage extends Thread {
	
	private String mailbox;
	private String extension;
	private int newcount;
	
	
	//private AstPortalProxy service = new AstPortalProxy();
	String url = "http://www.heightsre.com/Examples/Test/Certmail.nsf/AstPortal";
	NotesWSClient wsclient = new NotesWSClient(url);
	public vmNewMessage(MessageWaitingEvent e)
	{
		mailbox = e.getMailbox();
		extension = mailbox.substring(0, mailbox.indexOf('@'));
		newcount = e.getNew();
		
		//service.setEndpoint(url);
		
		
	}
	
	public void run()
	{
		
		String NoteCMD = "NEWVOICEMAILMESSAGE";
		String[] opts = new String[2];
		opts[0] = extension;
		opts[1] = "" + newcount;
		
		try {
			wsclient.generalCommand(NoteCMD, opts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
