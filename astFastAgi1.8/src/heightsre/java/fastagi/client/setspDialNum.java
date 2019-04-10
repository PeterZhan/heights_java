package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setspDialNum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String spnum = getVariable("SPDIALNUM");
		String phonenum = getVariable("spphone");
	

		String[] opts = new String[3];
		opts[0] = docid;
		opts[1] = spnum;
		opts[2] = phonenum;
		
        String cmd = "SETSPNUM";
		
		
        String url = "http://www.heightsre.com/Examples/Test/TrackVdr.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
	

	}

}
