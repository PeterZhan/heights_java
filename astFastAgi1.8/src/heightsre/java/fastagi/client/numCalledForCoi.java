package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class numCalledForCoi extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String namephone = getVariable("NAMEPHONE");
		//String suffix = getVariable("SUFFIX");
		
		String cmd = "NUMCALLFORCOI";
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = namephone;
		//opts[2] = namephone;
 		
		try {
			ASTPORTAL.generalCommand(cmd, opts, "Testing2.nsf");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		

	}

}
