package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getRingGroup extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String groupNum = getVariable("ARG1");
		
		
       String dbname = "heightscalls.nsf";
		
		String[] opts = new String[1];
		opts[0] = groupNum;
		
		String cmd = "GETRINGGROUP";
		try {
			String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
			
			String members = res[0];
			String tl = res[1];
			String vm = res[2];
			
			setVariable("MEMBERS", members);
			setVariable("TL", tl);
			setVariable("VM", vm);
			
			
			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		

	}

}
