package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateSupExistPO extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String po = getVariable("EXTEN");
		String code = getVariable("BlgCode");
		if (po.endsWith("#"))
			po = po.substring(0, po.length()-1);
		
		String cmd = "VALIDATEPHONEPO";
		String[] opts = new String[2];
		opts[0] = po;
		opts[1] = code;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("0"))
		{
			setExtension("notfound");
			setPriority("1");
			return;
		}
		
		setVariable("PONUM",po);
		setVariable("PODOC", res[1]);
		setExtension("codefound");
		setPriority("1");

	}

}
