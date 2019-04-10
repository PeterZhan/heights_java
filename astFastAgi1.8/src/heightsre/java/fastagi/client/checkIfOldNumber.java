package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class checkIfOldNumber extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
	   String callingNum = getVariable("CALLNUM");
	  
	   
	   String uid = getVariable("UNIQUEID");
	  
	  
	
	   try{
	   String[] opts = new String[4];
	   opts[0] = callingNum;
	   opts[1] = "out";
	   opts[2] = "";
	   opts[3] = uid;
		
		String cmd = "CHECKIFFIRSTCALL";
		
		
       String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
			setVariable("__FIRSTCALLIN", "Yes");
			setVariable("__LEGALP", "custom/legalfirst2");
			
		}else
		{
			
			setVariable("__LEGALP", "silence/1");
			
		}
		
	   }catch(Exception e)
	   {
		   setVariable("VARTEST", e.toString());
	   }
		

	}

}
