package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setMovingDay extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String movingday = getVariable("NUMBERINPUT");
		
        setVariable("movingday", movingday);
		
		if (docid.equals("none")) return;
		
/*		
		if (movingday.endsWith("#")) 
			movingday = movingday.substring(0, movingday.length() -1);
		
		
		
		if (!MyJMathLib.isNumeric(movingday))
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
			
		}
		*/
			
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = movingday;
		String cmd = "SETMOVINGDAY";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		

	}

}
