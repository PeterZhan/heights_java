package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setResiCallerid extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String callerid = getVariable("NUMBERINPUT");
		
		setVariable("CALLID", callerid);
		
		if (docid.equals("none")) return;
		
		
		String[] opts = new String[2];
		opts[0]= docid;
		opts[1] = callerid;
		String cmd = "SETCALLERID";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		

	}

}
