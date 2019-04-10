package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SaveVdrPerOPT extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String docid = getVariable("DOCID");
        String opt = getVariable("OPT");
        //String contact = getVariable("COMPANY");
		
		
		String cmd ="SAVEVENDOREXPPEROPT";
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = opt;
		//opts[2] = contact;
		
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
