package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ifcallback extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
        String callerNum = getVariable("CALLERID(num)");
		
		String[] opts = new String[1];
		opts[0] = callerNum;
		
		String cmd = "GETIFCALLBACK";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifcallback = res[1];
	
	
		
		if(!ifEmp.equals("1"))
		{
			setExtension("NotAllowed");
			setPriority("1");
		    return;	
		}
		
	//	setVariable("ACTPUNCH", punchact);
		
				
		if (ifcallback.equals("no"))
		{

			setExtension("unchecked");
            setPriority("1");
			
			return;
		}
		
		
		if (ifcallback.equals("yes"))
		{

            
            setExtension("checked");
            setPriority("1");
			
			return;
		}
		
		
		
		
		

	}

}
