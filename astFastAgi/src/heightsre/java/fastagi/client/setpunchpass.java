package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setpunchpass extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String callerID = getVariable("CALLERID(name)");
		
		String ps = getVariable("pass2");
		
		String[] opts = new String[2];
		opts[0] = callerID;
		opts[1] = ps;
		
		String cmd = "SETPUNCHPASSWORD";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
			
		
		setVariable("punchpass", ps);
		
		
	}

}
