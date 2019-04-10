package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class processNotTenant extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String tenant = getVariable("TENANT");
		String phone = getVariable("PHONE");
		/*String fullname = "/var/spool/asterisk/monitor/" + fname;
		String suffix = getVariable("SUFFIX");
		String namephone = getVariable("NAMEPHONE");
		String opt = getVariable("OPT");*/
		
		String cmd = "NOTTENANTPHONENUMBER";
		String[] opts = new String[3];
		opts[0] = docid;
		opts[1] = tenant;
		opts[2] = phone;
		/*opts[2] = suffix;
		opts[3] = namephone;
		opts[4] = opt;*/
		
		try {
			ASTPORTAL.generalCommand(cmd, opts, "tenlease.nsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			setVariable("ERROR", e.toString());
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
