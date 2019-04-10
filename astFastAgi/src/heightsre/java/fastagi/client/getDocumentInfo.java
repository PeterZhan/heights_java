package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getDocumentInfo extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String database = getVariable("DATABASE");
        String id = getVariable("EXTEN");
		
		String dbname = "heightscalls.nsf";
		
		String[] opts = new String[2];
		opts[0] = database;
		opts[1] = id;
		
		String cmd = "GETPROMPTBYId";
		try {
			String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
			
			if (res[0].equals("0"))
			{
				setExtension("i");
				setPriority("1");
				
				return;
				
				
				
			}
			
			
			
			setVariable("pp", res[1]);
			setVariable("SUM", res[2]);
			setVariable("DOCTITLE", res[3]);
			
		
		
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
