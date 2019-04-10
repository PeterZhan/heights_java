package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class closerfdailyPO extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String calldb = getVariable("CALLDB");
		String calldoc = getVariable("CALLDOC");
		String empName = getVariable("EMPNAME");
		
		String[] opts = new String[3];
	//	opts[0] = empName;
		opts[0] = calldb;
		opts[1] = calldoc;
		opts[2] = empName;
		
        String cmd = "CLOSERFPPONUM";
		
		
		
 		
		try {
			String res[] = ASTPORTAL.generalCommand(cmd, opts, "heightscalls.nsf");
		//	String result = res[0];
			
			
			
			
		}
		catch(Exception e)
		{
			
		}
		
		

	}

}
