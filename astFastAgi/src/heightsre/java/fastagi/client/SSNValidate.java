package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SSNValidate extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String ssn = getVariable("EXTEN");
		
		if (ssn.endsWith("#")) 
			ssn = ssn.substring(0, ssn.length() -1);
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
      opt[0] = ssn;
    
    
    
      String cmd = "GETTENANTBYSSN"; 
	   	
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
		   setVariable("UNITNO", res[5]);
		   
		   setVariable("CODE", res[6]);
		   setVariable("BlgCode", res[6]);
		   setVariable("EMADDR", res[7]);
		 //  setContext("tenantcomplain");
		 //  setExtension("found");
		   setExtension("found");
		   setPriority("1");
		   
	   }else
	   {
		   setExtension("notfound");
		   setPriority("1"); 
	   }
		
		
		
		
		
		
		
		
		
		

	}

}
