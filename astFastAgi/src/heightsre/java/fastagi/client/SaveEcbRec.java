package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SaveEcbRec extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String reclen = getVariable("reclen");
		String fname = getVariable("EcbRec") + ".WAV";
		String fullname = "/var/spool/asterisk/monitor/" + fname;
	//	String suffix = getVariable("SUFFIX");
		//String namephone = getVariable("NAMEPHONE");
		//String opt = getVariable("OPT");
		
		String cmd = "SAVEECBREC";
		String[] opts = new String[3];
		opts[0] = docid;
		opts[1] = fname;
		opts[2] = reclen;
		//opts[2] = namephone;
		//opts[3] = opt;
	//	opts[4] = opt;
		
		try {
			ASTPORTAL.generalCommand(cmd, opts, fullname, "ECB.nsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			setVariable("ERROR", e.toString());
			e.printStackTrace();
		}
		
		

	}

}
