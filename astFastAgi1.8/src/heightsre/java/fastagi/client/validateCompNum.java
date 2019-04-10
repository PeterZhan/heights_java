package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateCompNum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String complain = getVariable("EXTEN");
		if (complain.endsWith("#"))
			complain = complain.substring(0, complain.length()-1);
		
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
       opt[0] = complain;
   
   
   
       String cmd = "VALIDATECOMPNUM"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String valid = res[0];
	   String compdoc = res[1];
	   
	   if (valid.equals("0"))
	   {
		   setExtension("notfound");
		   setPriority("1");
		   return;
	   }
		
	   if (valid.equals("1"))
	   {   
		   setVariable("complain", complain);
		   setVariable("COMPDOC", compdoc);
		   
		   
		   setExtension("found");
		   setPriority("1");
		   return;
	   }

	}

}
