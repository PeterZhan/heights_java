package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setRentalType extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String rentalType = getVariable("rentaltype");
		
		if (docid.equals("none")) return;
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = rentalType;
		String cmd = "SETRENTALTYPE";  // web service command
		
		// web service url
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		// the class to send web service command to notes
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the command
		String[] res = wsclient.generalCommand(cmd, opts);
		
		

	}

}
