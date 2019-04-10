package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getTenOpenCredit extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String code = getVariable("CODE");
		String apt = getVariable("APTNO");
		
		String[] opts = new String[2];
		opts[0] = code;
		opts[1] = apt;
	
		
		String cmd = "GETBALANCEBYCODETEN";  // this is the command
		
		// web service url
		String url = "http://www.heightsre.com/Examples/Test/SkyRpts.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the web service request
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (!res[0].equals("0"))
		{
			setVariable("FOUND", "1");
			setVariable("NEWDATE", res[0]);
			setVariable("BALANCE", res[1]);
			
			
			
		} else
		{
			setVariable("FOUND", "0");
			
		}
		
		
		
		
		

	}

}
