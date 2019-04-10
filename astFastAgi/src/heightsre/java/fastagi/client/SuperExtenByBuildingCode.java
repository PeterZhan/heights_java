package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SuperExtenByBuildingCode extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
String blgCode = getVariable("EXTEN");
		
		if (blgCode.endsWith("#")) 
			blgCode = blgCode.substring(0, blgCode.length() -1);
		
		
		String cmd = "GETSUPEREXTENYCODE";
		String[] opts = new String[1];
		opts[0] = blgCode;
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/Empllist.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
			
			
			
			
			setVariable("SUPEREXTEN", res[1]);
			setVariable("__SUPER", res[2]);
			setVariable("SUPERCELLS", res[3]);
			setExtension("codefound");
			setPriority("1");
			setVariable("SUPERDOC", res[4]);
			
			
			
		} else
		{
			setExtension("notfound");
			setPriority("1");
		}
		

	}

}
