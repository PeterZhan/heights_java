package heightsre.java.fastagi.client;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getDoorBellPass extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String dbname = "heightscalls.nsf";
		String callerid = getVariable("CALLERID(num)");
		String[] opts = new String[1];
		opts[0] = "";
		
		
		String cmd = "GETDOORBELLPASS";
		try {
			String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
			
			//String ext = res[1];
			
			Set<String> h = new HashSet<String>(
					Arrays.asList("601", 
					              "602",
					              "606",
					              "608",
					              "640",
					              "621",
					              "622",
					              "612",
					              "625",
					              "607",
					              "620",
					              "609",
					              "605",
					              "615"
					              )
					);
			
			
			
			
			if (h.contains(callerid))
			{
			//	setVariable("CALLERID(num)", ext);
				setContext("internalDoor");
				setExtension("s");
				setPriority("1");
				
			}
			
			
			setVariable("DOORPASS", res[0]);
			
			
		
		
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		

	}

}
