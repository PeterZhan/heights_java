package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class checkForEmployee extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String callerID = getVariable("CALLERID(num)");
		
		String[] opts = new String[1];
		opts[0] = callerID;
		
		String cmd = "CHECKEMPLOYEEBYCID";
		
		
        String url = "http://www.heightsre.com/Examples/Test/Empllist.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String iffound = res[0];
		String empdocid = res[1];
		String jobtitle = res[2];
		String empName = res[3];
	
		setVariable("RSLT", iffound);
		
		if (iffound.equals("Found"))
		{
			setVariable("EMPDOCID", empdocid);
			setVariable("JOBTITLE", jobtitle);
			setVariable("EMPNAME", empName);
			
			
			
		}
		
		
		
		
		
		
		
		

	}

}
