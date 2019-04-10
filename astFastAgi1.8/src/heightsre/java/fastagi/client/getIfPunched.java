package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getIfPunched extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String docid = getVariable("DOCID");
		
		String[] opts = new String[1];
		opts[0] = docid;
		
		String cmd = "GETIFPUNCHED";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifPunched = res[0];
	
	
	
		
		if(ifPunched.equals("0"))
		{
			
			String prompNotPunch = getVariable("NOTPUNCHED");
			this.streamFile(prompNotPunch);
			
			
			
		    return;	
		}
		

		
			

	}

}
