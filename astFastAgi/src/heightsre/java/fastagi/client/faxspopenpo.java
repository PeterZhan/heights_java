package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class faxspopenpo extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String faxnum = getVariable("EXTEN");
		if (faxnum.endsWith("#"))
		{
			faxnum = faxnum.substring(0,faxnum.length()-1);
		}
		
		
		String pocount = getVariable("POCOUNT");
		
		int ipocount = Integer.parseInt(pocount);
		
		String[] opt = new String[ipocount + 3];
	     
	     // these are the options for the service command
       opt[ipocount + 1] = getVariable("BlgCode");
       opt[ipocount + 2] = faxnum;
       opt[0] = pocount;
       
       for (int i=1;i<=ipocount;i++)
       {
    	   opt[i] = getVariable("PO" + i);
       }
     
     
     
     String cmd = "FAXSPPOS"; 
	   	
     String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
		

	}

}
