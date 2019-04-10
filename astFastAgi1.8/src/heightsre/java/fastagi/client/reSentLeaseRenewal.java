package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class reSentLeaseRenewal extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("TENDOC");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
      opt[0] = docid;
    
    
    
      String cmd = "SENDLEASERENEWAL"; 
	   	
      String url = "http://www.heightsre.com/Examples/Test/TenLease.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
		
	   
	   String r = res[0];
	   String pp = getVariable("pp");
	   if (r.equals("0"))
		   setVariable("ppAfter", pp + "21");
		
	   if (r.equals("1"))
		   setVariable("ppAfter", pp + "20");
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
