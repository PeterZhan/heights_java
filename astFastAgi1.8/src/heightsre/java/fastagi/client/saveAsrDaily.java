package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveAsrDaily extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String db = getVariable("DB");
		String docid = getVariable("DOCID");
		String fd = getVariable("FD");
		String st = getVariable("status");
		
		
		
        String utterance = "";
        if (st.equals("0"))
         utterance = getVariable("utterance");
		
		
		String[] opts = new String[5];
		opts[0] = db;
		opts[1] = docid;
		opts[2] = fd;
		opts[3] = st;
		opts[4] = utterance;
		
		
		String cmd = "SAVEASRDAILY";
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		

	}

}
