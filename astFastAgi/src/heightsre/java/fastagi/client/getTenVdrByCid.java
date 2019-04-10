package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getTenVdrByCid extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String callerid = getVariable("CALLERID(num)");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
       opt[0] = callerid;
     
     
     
       String cmd = "GETTENANTVDRBYCID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/CUSTOMER.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   if (res == null) return;
	   
	   if (res[0].equals("0"))  // not found
	   {
		  // setExtension("notfound");
		   setPriority("notfound"); 
		   setVariable("CTYPE", "UNKNOWN");
	   }
	   
	   
	   
	   if (res[0].equals("1"))  // current tenant
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
		   
		   setVariable("EBILL", res[8]);
		 //  setContext("tenantcomplain");
		 //  setExtension("found");
		   setPriority("CTEN");
		   
		   setVariable("CTYPE", "TENANT");
		   
	   }
	   
	   if (res[0].equals("2"))  // former tenant
	   {
		 
		   setPriority("FTEN");
		   setVariable("CTYPE", "FORMER TENANT");
		   
	   }
	   
		
	   if (res[0].equals("3"))  // vendor
	   {
		 
		   setPriority("VDR");
		   
		   setVariable("COMPNAME", res[1]);
		 
		 
		   
		   setVariable("CODE", res[2]);
		   setVariable("CONTACT", res[3]);
		
		   setVariable("CTYPE", "VENDOR");
		   
	   }
		
		
		
		

	}

}
