package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SendRemForTimeSheet extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String docid = getVariable("DOCID");
        String exten = getVariable("EXTEN");
		
		
		String cmd = "RESENDREMTIMESHEET";
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = exten;
		
	//	opts[4] = opt;
		
		try {  
			
			ASTPORTAL.generalCommand(cmd, opts, "timeshet.nsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			setVariable("ERROR", e.toString());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
