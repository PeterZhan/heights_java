package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveTenAptDateInfo extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String[] opts = new String[2];
		opts[0] = getVariable("COMPDOC");
		opts[1] = getVariable("DATEMILLSECS");
		
		String cmd = "SAVETENAPTDATE";
   		
   		
   		
 		  
   		String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		
		
		
		
		
		

	}

}
