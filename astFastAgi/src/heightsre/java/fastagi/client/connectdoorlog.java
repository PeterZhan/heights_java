package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class connectdoorlog extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String callername = getVariable("CALLERID(name)");
		String callernum = getVariable("CALLERID(num)");
		
		
		String dbname = "heightscalls.nsf";
		
		String[] opts = new String[3];
		opts[0] = callername;
		opts[1] = callernum;
		opts[2] = "connect to the door phone.";
		String cmd = "SETCONNECTDOORLOG";
		try {
			String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
