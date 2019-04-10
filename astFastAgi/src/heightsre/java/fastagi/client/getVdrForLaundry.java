package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getVdrForLaundry extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

		String BlgCode = getVariable("BlgCode");
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = BlgCode;
    
    
    
      String cmd = "GETVDRLAUNDRY"; 
	   	
      String url = "http://www.heightsre.com/Examples/Test/Machine.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
		
	   String vdrphone = res[0];
	   String vdrdoc = res[1];
	   
	   setVariable("VDRCELL", vdrphone);
	   setVariable("VDRDOC", vdrdoc);
		
		
	}

}
