package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getDatabasePrompt extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String dbname = "heightscalls.nsf";
		
		String[] opts = new String[1];
		opts[0] = "";
		String cmd = "GETDATABASELIST";
		try {
			String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
		
		    String prompt = res[0];
		    
		    String uid = getVariable("UNIQUEID");
		    String filename = "dblist" + uid;
		    
		    String wavFile = ASTPORTAL.CreateWavPromts(filename, prompt);
		
		
		   setVariable("pDb", wavFile);
		
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
