package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setAnnualInc extends BaseAgiScript {

	@Override
	
	// this web service is for setting the annual incoming of the tenant
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");   // get document id
		String annIncom = getVariable("NUMBERINPUT");  // get the number input
		
		setVariable("annIncom", annIncom);
			
		if (docid.equals("none")) return;
		
		
	/*	if (annIncom.endsWith("#")) 
			annIncom = annIncom.substring(0, annIncom.length() -1);
		
		
		
		if (!MyJMathLib.isNumeric(annIncom))
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
			
		}
		*/
		
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = annIncom;
		String cmd = "SETANNINCOMING";  // web service command
		
		// web service url
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		// the class to send web service command to notes
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the command
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		// if we need to ask more questions
		setVariable("moreques",res[0]);
		
		
		

	}

}
