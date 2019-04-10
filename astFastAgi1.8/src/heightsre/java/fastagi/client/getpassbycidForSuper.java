package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getpassbycidForSuper extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String callerID = getVariable("CALLERID(name)");
		
		String[] opts = new String[1];
		opts[0] = callerID;
		
		String cmd = "GETPASSBYCALLERID";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifHaspass = res[1];
		String punchpass = res[2];
	//	String punchact = res[3];
		
		setVariable("VARTEST", res[0]);
		
		if(!ifEmp.equals("1"))
		{
			setContext("getPassByEmpCodeForSuper");
			setExtension("s");
			setPriority("1");
		    return;	
		}
		
	//	setVariable("ACTPUNCH", punchact);
		
				
		if (ifHaspass.equals("1"))
		{
			setVariable("punchpass", punchpass);
			setContext("superpassord");
			setExtension("s");
			setPriority("1");
			
			
			
			return;
		}
		
		
		if (ifHaspass.equals("0"))
		{
			setContext("super766test");
			setExtension("s");
			setPriority("1");
			return;
		}
		
		
		
		

	}

}
