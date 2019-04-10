package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class faxsuperinv extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String po = getVariable("PONUM");
		String code = getVariable("BlgCode");
		
		String faxnum = getVariable("FAXNUM");
		
		String invdoc = getVariable("INVDOC");
		String wrkdoc = getVariable("WRKDOC");
		
		
		
		if (faxnum.endsWith("#"))
		{
			faxnum = faxnum.substring(0,faxnum.length()-1);
		    setVariable("FAXNUM", faxnum);
		}
		
		
		String[] opt = new String[5];
	     
	     // these are the options for the service command
      opt[0] = code;
      opt[2] = faxnum;
      opt[1] = po;
      
      opt[3] = invdoc;
      opt[4] = wrkdoc;
      
   
    
    
    
    String cmd = "FAXSPINVOICE"; 
	   	
    String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
		
		
		

	}

}
