package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getInvoiceForSuper extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String podoc = getVariable("PODOC");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
       opt[0] = podoc;
     
     
     
     String cmd = "GETINVOICEFORSUPER"; 
	   	
     String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String ifinv = res[0];
	   
	   if (!ifinv.equals("0"))
	   {
		   setVariable("NOINV", "No");
		   String invdoc = res[0];
		   String wrkdoc = res[1];
		   
		   setVariable("INVDOC", invdoc);
		   setVariable("WRKDOC", wrkdoc);
		   
		   String faxnum = res[2];
		   
		   String faxnumblk = "";
		   
		   for (int i=0;i<=faxnum.length()-1;i++)
		   {
			   faxnumblk = faxnumblk + faxnum.substring(i,i+1) + " ";
		   }
		   
		   if (faxnumblk.endsWith(" "))
			   faxnumblk = faxnumblk.substring(0, faxnumblk.length()-1);
		   
		   
		   setVariable("FAXNUM", faxnum);
		   setVariable("FAXNUMBLK", faxnumblk);
		   
		   
		   
		   
	   }else
	   {
		   setVariable("NOINV", "Yes");
	   }
	   
	   
	   
	   
		
		

	}

}
