package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateExten extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
         String exten = getVariable("EXTEN");
		
		if (exten.endsWith("#"))
			exten = exten.substring(0, exten.length()-1);
		
	
		
		String cmd = "VALIDATEEXTEN";
		String[] opts = new String[1];
		opts[0] = exten;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/EmplList.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
			String empname = res[1];
			
			setVariable("EXTNAME", empname);
			
		
			
			setExtension("codefound");
			setPriority("1");
		}else
		{
			setExtension("notfound");
			setPriority("1");
		}
		
		
		
		
		
		
		

	}

}
