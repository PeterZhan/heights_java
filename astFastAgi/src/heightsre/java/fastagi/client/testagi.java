package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class testagi extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String[] opts = new String[1];
		    opts[0] = "652";
		
			
			String cmd = "GETVOICEMAILCOUNT";
			
			
	       String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			
			String[] res = wsclient.generalCommand(cmd, opts);
			
		
			setVariable("NEWCOUNT", res[0]);
			setVariable("OLDCOUNT", res[1]);
		
		
		
		
		
		
		

	}

}
