package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setIncomingCallType extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String doclogid = getVariable("LOGDOCID");
		String inCallType = getVariable("INCALLTYPE");
		
		try{
			   String[] opts = new String[2];
			   opts[0] = doclogid;
			   opts[1] = inCallType;
			
			   
			
				
				String cmd = "SAVEINCOMINGCALLTYPE";
				
				
		       String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
			    
				NotesWSClient wsclient = new NotesWSClient(url);
				
				String[] res = wsclient.generalCommand(cmd, opts);
		
		}catch(Exception e)
		   {
			   setVariable("VARTEST", e.toString());
		   }

		
		

	}

}
