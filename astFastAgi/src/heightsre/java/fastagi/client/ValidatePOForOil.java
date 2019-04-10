package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidatePOForOil extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String po = getVariable("EXTEN");
		if (po.endsWith("#"))
			po = po.substring(0, po.length()-1);
		
		String cmd = "VALIDATEPOFOROIL";
		String[] opts = new String[1];
		opts[0] = po;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/Utility.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("0"))
		{
			setExtension("notfound");
			setPriority("1");
			return;
		}
		
		setVariable("DOCID", res[1]);
		setVariable("oilcomphone", res[2]);
		setExtension("codefound");
		setPriority("1");
		
		
		

	}

}
