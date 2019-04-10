package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getTenByBldgApt extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String bldg = getVariable("BlgCode");
        String apt = getVariable("APTNO");
		
		String[] opt = new String[2];
	     
	     // these are the options for the service command
       opt[0] = bldg;
       opt[1] = apt;
      
     
     
       String cmd = "GETTENANTBYBLDGAPT"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/CUSTOMER.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   if (res == null) return;
	   
	   if (res[0].equals("1"))
	   {
		   setVariable("TENANT", res[1]);
		   setVariable("TENDOC", res[2]);
		   setVariable("sext", res[3]);
		 
		   
	   }
		
		
		
		
		
		

	}

}
