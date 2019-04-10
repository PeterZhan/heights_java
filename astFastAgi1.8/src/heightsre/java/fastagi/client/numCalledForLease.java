package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class numCalledForLease extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String namephone = getVariable("NAMEPHONE");
		String suffix = getVariable("SUFFIX");
		
		String cmd = "NUMCALLFORLEASERENEW";
		
		String[] opts = new String[3];
		opts[0] = docid;
		opts[1] = suffix;
		opts[2] = namephone;
 		
		try {
			ASTPORTAL.generalCommand(cmd, opts, "tenlease.nsf");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		

	}

}
