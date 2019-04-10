package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SaveCOIRec extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String fname = getVariable("coirec") + ".WAV";
		String fullname = "/var/spool/asterisk/monitor/" + fname;
	//	String suffix = getVariable("SUFFIX");
		String namephone = getVariable("NAMEPHONE");
		String opt = getVariable("OPT");
		
		String cmd = "SAVECOIREC";
		String[] opts = new String[4];
		opts[0] = docid;
		opts[1] = fname;
		opts[2] = namephone;
		opts[3] = opt;
	//	opts[4] = opt;
		
		try {
			ASTPORTAL.generalCommand(cmd, opts, fullname, "Testing2.nsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			setVariable("ERROR", e.toString());
			e.printStackTrace();
		}
		
		

	}

}
