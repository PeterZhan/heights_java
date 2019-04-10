package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendIncomingLogEmail extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		/*
		 * 
		 * String fname = options[1];
		String ivrext = options[3];
		String callernum = options[2];
		String reclen = options[4];
		String callername = options[5];
		String calltype = options[6];
		String uid = options[7];
		 * 
		 * 
		 * 
		 */
		String fname = getVariable("INCOMINGREC");
		
		String[] opts = new String[12];
		opts[0] = "TAGCOMPLAINT";//
		opts[1] = fname;
		opts[2] = getVariable("CALLERID(num)");
		opts[3] = getVariable("SUPEREXTEN");
		opts[4] = getVariable("reclen");
		opts[5] = getVariable("CALLERID(name)");
		opts[6] = "Incoming Call For Extension";
		opts[7] = getVariable("UNIQUEID");
		opts[8] = getVariable("TENANTDOC");
		opts[9] = getVariable("SUPERDOC");
		
		String ch = getVariable("MAINC");
		opts[10] = getVariable("SHARED(DialLog," + ch +")");
		opts[11] = getVariable("SUPER");
		
		 String NoteCMD = "SENDINCOMINGWITHLOGID";

			

		 String[] res = null;
		try {
			res = ASTPORTAL.generalCommand(NoteCMD, opts, 
						              "/var/spool/asterisk/monitor/" + fname + ".WAV",
						                     "heightscalls.nsf");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

			String logid = "";

			if (res != null && res.length > 0)
				logid = res[0];
			
			String[] cmds = new String[7];
			cmds[0] = "/var/javalib/sendmailincomingcallLogId.sh";
			cmds[1] = fname + ".WAV";
			cmds[2] = getVariable("CALLERID(num)");
			cmds[3] = getVariable("SUPEREXTEN");
			cmds[4] = getVariable("reclen");
			//cmds[5] = 
			cmds[5] = getVariable("CALLERID(name)") + "*LogID*" + logid;
			cmds[6] = opts[6];

			try {

				Process proc = Runtime.getRuntime().exec(cmds);

				if (proc != null) {
					proc.waitFor();
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		
		
		
		
		
		
		
		
		
		

	}

}
