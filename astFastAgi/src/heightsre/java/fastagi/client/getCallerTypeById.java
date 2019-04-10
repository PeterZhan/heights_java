package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCallerTypeById extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
      String callerid = getVariable("CALLERID(num)");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
       opt[0] = callerid;
     
     
     
       String cmd = "GETCALLERTYPEBYID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/Certmail.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
		
	   String typeinfo = res[0];
	   String docid = res[1];
	   String fname = res[2];
	   
	   String[] types = typeinfo.split(" ");
	   setVariable("CALLERYPE", types[0]);
	   setVariable("CALLERSTATUS", types[1]);

	   if (!types[0].equals("UNKNOWN"))
	   {
		   setVariable("INDXID", docid);
		   setVariable("FULLNAME", fname);
	   }
	   
		
		
		
		
		
		
		
		
		
		
		

	}

}
