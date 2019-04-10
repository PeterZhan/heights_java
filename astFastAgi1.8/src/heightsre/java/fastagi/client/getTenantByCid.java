package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getTenantByCid extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String callerid = getVariable("CALLERID(num)");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
       opt[0] = callerid;
     
     
     
       String cmd = "GETTENANTBYCID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/CUSTOMER.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   if (res == null) return;
	   
	   if (res[0].equals("1"))
	   {
		   setVariable("TENANT", res[1]);
		   setVariable("TENDOC", res[2]);
		   setVariable("sext", res[3]);
		   setVariable("TENADDR", res[4]);
		   setVariable("APTNO", res[5]);
		   setContext("tenantcomplain");
		   setExtension("s");
		   setPriority("1");
		   
	   }
	   
	   
	   
		
		
		
		

	}

}
