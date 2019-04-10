package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getpassbycid extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String callerID = getVariable("CALLERID(num)");
		
		String[] opts = new String[1];
		opts[0] = callerID;
		
		String cmd = "GETPASSBYCALLERID";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifHaspass = res[1];
		String punchpass = res[2];
		String empName = res[3];
		String empDocid = res[4];
		
		setVariable("VARTEST", res[0]);
		
		if(!ifEmp.equals("1"))
		{
			setContext("getPassByEmpCode");
			setExtension("s");
			setPriority("1");
		    return;	
		}
		
		setVariable("CALLERID(name)", empName);
		
		setVariable("EMPDOCID", empDocid);
				
		if (ifHaspass.equals("1") && !punchpass.equals("1234"))
		{
			setVariable("punchpass", punchpass);
			
			setContext("EmployeePunCh");
			setExtension("s");
			setPriority("1");
			
			
			return;
		}
		
		
		if (ifHaspass.equals("0") || punchpass.equals("1234"))
		{
			setContext("setpassforpunch");
			setExtension("s");
			setPriority("1");
			return;
		}
		
		

	}

}
