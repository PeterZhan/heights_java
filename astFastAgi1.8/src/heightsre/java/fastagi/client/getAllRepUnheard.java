package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllRepUnheard extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String empName = getVariable("EMPNAME");
		String[] opts = new String[1];
		opts[0] = empName;
		
		String cmd = "GETUNHEARDREPORT";
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String repnum = res[0];
		String replist = res[1];
	
		setVariable("REPNUM", repnum);
		
		setVariable("EMPDATA", res[2]);
		setVariable("INSEVEN", res[3]);
		setVariable("REPLIST", replist);

	}

}
