package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savePropLater extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String hours = getVariable("EXTEN");
		
	     if (hours.endsWith("#")) 
	    	 hours = hours.substring(0, hours.length() -1);
		
	    setVariable("HOUR", hours);	
		
		String[] opts = new String[2];
		opts[0] = docid;
		
		opts[1] = hours;
	
		

		String cmd = "SAVEPROSPECTLATER";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		

	}

}
