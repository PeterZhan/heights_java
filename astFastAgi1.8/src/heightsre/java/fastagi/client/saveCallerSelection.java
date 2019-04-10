package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveCallerSelection extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String callerid = getVariable("CALLERID(num)");
		String calltype = getVariable("CTYPE");
		String ext = getVariable("EXTEN");
		
		
		String[] opts = new String[3];
		opts[0] = callerid;
		opts[1] = calltype;
		opts[2] = ext;
		
		
		String cmd = "SAVECALLERSELECTION";
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		
		

	}

}
