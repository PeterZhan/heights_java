package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class punchtimeconfed extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String tpunch = getVariable("timeforpunch");
		
		String docid = getVariable("DOCID");
		String punchact = getVariable("ACTPUNCH");
		String empName = getVariable("CALLERID(name)");
		
		String[] opts = new String[4];
		opts[0] = docid;
		opts[1] = punchact;
		opts[2] = tpunch;
		opts[3] = empName;
		
		String cmd = "WILLPUNCH";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		if (docid.equals("none"))
		{
			setVariable("DOCID", res[0]);
		}

	}

}
