package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class printspinvoice extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String po = getVariable("PONUM");
		String code = getVariable("BlgCode");
		
			
		String invdoc = getVariable("INVDOC");
		String wrkdoc = getVariable("WRKDOC");
		
		
		
	
		
		
		String[] opt = new String[4];
	     
	     // these are the options for the service command
      opt[0] = code;
     
      opt[1] = po;
      
      opt[2] = invdoc;
      opt[3] = wrkdoc;
      
   
    
    
    
    String cmd = "PRINTSPINVOICE"; 
	   	
    String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
		
		
		
		
		
		

	}

}
