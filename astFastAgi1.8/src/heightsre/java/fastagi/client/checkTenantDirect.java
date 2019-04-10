package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class checkTenantDirect extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		
		// TODO Auto-generated method stub
		
		String callerid = getVariable("CALLERID(num)");
		
		
		String[] opts = new String[1];
		opts[0] = callerid;
		
		
		
	
		
	
	
		
		
		
		
		
		//*******************************
		
		String cmd = "CHECKFORTENANTDIRECT";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/CertMail.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		String isTenant = res[0];
		String extEmp = res[1];
		
		if (isTenant.equals("1"))
		{
			setVariable("EMPEXT", extEmp);
			setExtension("Tenant");
			setPriority("1");
		}
		
		
		
		
		

	}

}
