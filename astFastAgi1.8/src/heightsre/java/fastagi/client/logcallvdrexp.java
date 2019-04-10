package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class logcallvdrexp extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String docid = getVariable("DOCID");
        String fphone = getVariable("FORMPHONE");
        String contact = getVariable("CONTACT");
		
		
		String cmd = "LOGCALLVDRPERMEXP";
		String[] opts = new String[3];
		opts[0] = docid;
		opts[1] = fphone;
		opts[2] = contact;
		
	//	opts[4] = opt;
		
		try {
			ASTPORTAL.generalCommand(cmd, opts, "Mechanic.nsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			setVariable("ERROR", e.toString());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		

	}

}
