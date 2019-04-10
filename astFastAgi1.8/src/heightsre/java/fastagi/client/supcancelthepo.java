package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class supcancelthepo extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String podoc = getVariable("PODOC");
		String cmd = "CANCELTHEPO";
		
		
		String[] opts = new String[1];
		opts[0] = podoc;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		

	}

}
