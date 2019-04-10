package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setFaxNum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("docid");  // get the document id
		String faxnum = getVariable("FaxNum"); // get the number input
		
       
		
		
		
		
	//	if (bedrooms.endsWith("#")) 
	//		bedrooms = bedrooms.substring(0, bedrooms.length() -1);
		
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = faxnum;
		String cmd = "SETFAXNUM";  // the web service command
		
		// the web service url
		String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		// the class for sending web service command
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the web service command
		String[] res = wsclient.generalCommand(cmd, opts);

	}

}
