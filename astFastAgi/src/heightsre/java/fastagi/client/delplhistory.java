package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class delplhistory extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String hs = getVariable("PLAYHS");
		String replist = getVariable("REPLIST");
		String[] reps = replist.split(":");
		String curnum = getVariable("CURNUM");
		int icur = Integer.parseInt(curnum);
		
		String datadoc = getVariable("EMPDATA");
		String idx = reps[icur-1];
		
		
		
		String[] opts = new String[3];
		opts[0] = hs;
		opts[1] = idx;
		opts[2] = datadoc;
		String cmd = "DELPLAYHSBYINDX";
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		
		

	}

}
