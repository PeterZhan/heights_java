package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getEmpPassByCid extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
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
		String ifAbsent = res[5];
		
		setVariable("VARTEST", res[0]);
		
		if(!ifEmp.equals("1"))
		{
			//setContext("getPassByEmpCode");
			setExtension("unemployee");
			setPriority("1");
		    return;	
		}
		
		setVariable("EMPNAME", empName);
		
		setVariable("EMPDOCID", empDocid);
		
		
		if (ifAbsent.equals("1"))
			setVariable("ABSENT", "Yes");
				
		if (ifHaspass.equals("1"))
		{
			setVariable("emppass", punchpass);
			
		//	setContext("EmployeePunCh");
			setExtension("employee");
			setPriority("1");
			
			
			return;
		}
		
		
		

	}

}
