package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setResiWebId extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String webid = getVariable("NUMBERINPUT");
		
		
		setVariable("webid", webid);
		
		if (docid.equals("none")) return;
		
	/*	if (webid.endsWith("#")) 
			webid = webid.substring(0, webid.length() -1);
		
		

		if (!MyJMathLib.isNumeric(webid))
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
			
		}
		*/
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = webid;
		
		
		
		
		
		
		String cmd = "SETWEBID";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);

	}

}
