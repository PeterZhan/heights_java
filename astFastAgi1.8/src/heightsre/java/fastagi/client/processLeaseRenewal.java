package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class processLeaseRenewal extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
	//	String localchan = getVariable("LOCALCHAN");
		//String varShared = "SHARED(OPT," + localchan + ")";
		String opt = getVariable("OPT");
		
		String suffix = getVariable("SUFFIX");
		String namephone = getVariable("NAMEPHONE");
		String cmd = "PROCESSLEASERENEWAL";
		
		String[] opts = new String[4];
		opts[0] = docid;
		opts[1] = opt;
		opts[2] = suffix;
		opts[3] = namephone;
 		
		try {
			String[] ret = ASTPORTAL.generalCommand(cmd, opts, "tenlease.nsf");
			
			if (ret[0].equals("0"))
				setVariable("NOEMAIL", "Yes");
			else
				setVariable("NOEMAIL", "No");
			
			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
