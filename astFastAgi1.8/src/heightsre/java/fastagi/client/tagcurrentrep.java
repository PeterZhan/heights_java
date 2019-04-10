package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class tagcurrentrep extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String empName = getVariable("EMPNAME");
		
		String replist = getVariable("REPLIST");
		String[] reps = replist.split(":");
		
		String curnum = getVariable("CURNUM");
		
		String datadoc = getVariable("EMPDATA");
		int icur = Integer.parseInt(curnum);
		
		String idx = reps[icur-1];
		
		String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = idx;
		opts[2] = datadoc;
		
		String cmd = "TAGCURRENTDAILY";
		
		String dbname = "heightscalls.nsf";
		
		try {
			ASTPORTAL.generalCommand(cmd, opts, dbname);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
