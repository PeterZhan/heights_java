package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCBPassByCnum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
        String callerNum = getVariable("CALLERID(num)");
		
		String[] opts = new String[1];
		opts[0] = callerNum;
		
		String cmd = "GETPASSBYCALLERNUM";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifHaspass = res[1];
		String cbpass = res[2];
		String docid = res[3];
		
		String ext = res[4];
		
		setVariable("VARTEST", res[0]);
		
		setVariable("DOCID", docid);
		
		setVariable("EMPEXT", ext);
		
		if(!ifEmp.equals("1"))
		{
			hangup();
		    return;	
		}
		
	//	setVariable("ACTPUNCH", punchact);
		
				
		if (ifHaspass.equals("1"))
		{
			setVariable("cbpass", cbpass);
			return;
		}
		
		
		if (ifHaspass.equals("0"))
		{
			hangup();
			return;
		}
		
		

	}

}
