package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateInterPass extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String exten = getVariable("EXTEN");
        String cid = getVariable("CALLERID(num)");
		
		if (exten.endsWith("#"))
			exten = exten.substring(0, exten.length()-1);
		
	
		
		String cmd = "VALIDATEINTERPASS";
		String[] opts = new String[2];
		opts[0] = cid;
		opts[1] = exten;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
					
			setExtension("right");
			setPriority("1");
		}else
		{
			setExtension("wrong");
			setPriority("1");
		}
		
		
		
		
		
		

	}

}
