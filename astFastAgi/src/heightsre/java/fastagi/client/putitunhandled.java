package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class putitunhandled extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
	
		String replist = getVariable("REPLIST");
		String[] reps = replist.split(":");
		String curnum = getVariable("CURNUM");
		int icur = Integer.parseInt(curnum);
		
				
		String idx = reps[icur-1];
		String datadoc = getVariable("EMPDATA");
		
		
		String[] opts = new String[2];
	
		opts[0] = idx;
		opts[1] = datadoc;
	
		String cmd = "SETUNHANDLEBYINDX";
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		
		

	}

}
