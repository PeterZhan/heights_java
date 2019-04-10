package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;


// this agi is for rental call to get the caller id document
public class getCallerDoc extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String callernum = getVariable("CALLERID(num)");  // caller id number
		String callername = getVariable("CALLERID(name)");  // caller id name
		String newdoc = getVariable("newdoc");
		String[] opts = new String[3];
		opts[0] = callernum;
		opts[1] = callername;
		opts[2] = newdoc;
		
		String cmd = "GETCALLERDOC";  // this is the command
		
		// web service url
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the web service request
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res != null)
		{
			setVariable("DOCID", res[0]);  // get the document id
			setVariable("oldcaller", res[1]);  // if it's called before
		}
		
		
		

	}

}
