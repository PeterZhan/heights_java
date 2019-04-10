package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ifAllowToCall extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String callerNum = getVariable("CALLERID(num)");
		
		String[] opts = new String[1];
		opts[0] = callerNum;
		
		String cmd = "GETPASSBYCALLERNUM";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
        setVariable("VARTEST", url);
        
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifHaspass = res[1];
		String cbpass = res[2];
	//	String punchact = res[3];
		
		setVariable("VARTEST", res[0]);
		
		if(!ifEmp.equals("1"))
		{

            setExtension("NotAllowed");
            setPriority("1");
			
		    return;	
		}
		
	//	setVariable("ACTPUNCH", punchact);
		
				
		if (ifHaspass.equals("1"))
		{
			//setVariable("cbpass", cbpass);
			return;
		}
		
		
		if (ifHaspass.equals("0"))
		{
			setExtension("NoPassword");
	        setPriority("1");
			return;
		}
		

	}

}
