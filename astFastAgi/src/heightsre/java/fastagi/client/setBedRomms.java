package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setBedRomms extends BaseAgiScript {

	@Override
	
	// this agi is for setting the bed rooms number
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");  // get the document id
		String bedrooms = getVariable("NUMBERINPUT"); // get the number input
		
        setVariable("bedrooms", bedrooms);
		
		if (docid.equals("none")) return;
		
		
	//	if (bedrooms.endsWith("#")) 
	//		bedrooms = bedrooms.substring(0, bedrooms.length() -1);
		
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = bedrooms;
		String cmd = "SETBEDROOMS";  // the web service command
		
		// the web service url
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		// the class for sending web service command
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the web service command
		String[] res = wsclient.generalCommand(cmd, opts);

	}

}
