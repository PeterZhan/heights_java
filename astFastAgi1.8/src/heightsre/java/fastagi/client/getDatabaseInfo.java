package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getDatabaseInfo extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String id = getVariable("EXTEN");
		
		String dbname = "heightscalls.nsf";
		
		String[] opts = new String[1];
		opts[0] = id;
		String cmd = "GETDATABASEFORPROMPTS";
		try {
			String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
			
			if (res[0].equals("0"))
			{
				setExtension("i");
				setPriority("1");
				
				return;
				
				
				
			}
			
			
			
			setVariable("TITLE", res[1]);
			setVariable("DATABASE", res[2]);
			
			
			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
