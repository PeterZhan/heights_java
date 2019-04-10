package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

// this web service is for special extension for super cell
public class ForSpecalExten extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

		String chann = getVariable("CHANNEL");
		String exten = getVariable("EXTEN");
		// This call is from outside
		if (chann.startsWith("SIP/xo") || chann.startsWith("Transfered/SIP/xo")) {
			setVariable("CDR(accountcode)", "IN");// set it as incoming
			setVariable("SPDIALPAR", "M(cell-screen)");
			// setVariable("SPDIALPAR", "p");
			setVariable("__CSCALLERID", getVariable("CALLERID(num)"));
			// streamFile("custom/legalfirst2"); // play legal prompt

		} else {
			// this call is from internal
			// String unid = getVariable("UNIQUEID");
			// setVariable("CDR(accountcode)", "OUT"); // set it as outbound
			// call
			// setVariable("__OUTBOUNDREC", "outbound" + unid);
			// setVariable("SPDIALPAR", "M(trunk-dialout)"); // go to macro
			// trunk-dialout for monitoring
			// setVariable("CDR(userfield)", "outbound" + unid);
		}

		String[] opts = new String[3];
		opts[0] = getVariable("EXTEN");
		opts[1] = getVariable("CALLERID(name)");
		opts[2] = getVariable("CALLERID(num)");

		String cmd = "GETSPECIALCELL";

		String url = "http://www.heightsre.com/Examples/TEST/heightscalls.nsf/AstPortal";

		NotesWSClient wsclient = new NotesWSClient(url);

		String[] res = wsclient.generalCommand(cmd, opts);

		setVariable("CELLPHONE", res[0]);

		String exStatus = res[1];
		String punched = res[2];
		String trstrategy = res[3];
		String coext = res[4];
		String empname = res[5];

		String phones = res[6];

		String ringGroup = res[7];

		String isAbsent = res[8];
		String allowPunch = res[9];
		String abPrompt = res[10];

		setVariable("PUNCHED", punched);
		setVariable("EMPNAME", empname);
		setVariable("ABSENT", isAbsent);
        setVariable("ABPROMPT", abPrompt);
		if (isAbsent.equals("1"))
		{
			if (!isExitsTmpPrompt(exten))
			   streamString("abst", abPrompt);//empname + " is absent today");
		}
		else {
			if (allowPunch.equals("1") && !punched.equals("IN"))
				streamString("outp", empname + " is not punched in now");

		}

		if (!phones.equals("NONE")) {
			String[] vecphones = phones.split(":");

			setVariable("PHONENUM", "" + vecphones.length);

			for (int i = 0; i < vecphones.length; i++) {
				setVariable("PH" + (i + 1), vecphones[i]);

			}

		}

		int trs = Integer.parseInt(trstrategy);

		switch (trs) {
		case 0: // extension => cell
			/*
			 * String dialstatus = getVariable("DIALSTATUS"); if
			 * (dialstatus.equals("NOANSWER")) { setContext("special-vm");
			 * setExtension(exten); setPriority("1");
			 * 
			 * }
			 */

			break;

		case 1: // extension => vm/cell

			if ((punched.equals("IN")) && !exStatus.equals("UNAVAILABLE")) {
				// setVariable("LOG", "vmu"+exten);
				setContext("special-vm");
				setExtension(exten);
				setPriority("1");
			}

			String callinmode = getVariable("INCALLMODE");

			if (callinmode.equals("directin")) {
				// setVariable("LOG", "vmu"+exten);
				setContext("special-vm");
				setExtension(exten);
				setPriority("1");
			}

			break;

		case -1:
			// setVariable("LOG", "vmu"+exten);
			setContext("special-vm");
			setExtension(exten);
			setPriority("1");

			break;

		case 2:

			// setVariable("LOG", "vmu"+exten);
			setContext("extnoansermenu");
			setExtension("s");
			setPriority("1");
			setVariable("LCEXT", exten);
			setVariable("COEXT", coext);
			setVariable("EMPNAME", empname);

			break;

		case -5:

			String[] exts = coext.split(":");

			;

			if (exts[0].equals("NONE") && exts[1].equals("NONE")) {
				setContext("special-vm");
				setExtension(exten);
				setPriority("1");
				break;
			} else {
				setVariable("EMPNAME", empname);
				if (exts[0].equals("NONE")) {
					// setContext("ivr-2");
					setContext("absencealternate");
					setExtension(exts[1]);
					setPriority("1");
					break;

				}
				if (exts[1].equals("NONE")) {

					// setContext("ivr-2");
					setContext("absencealternate");
					setExtension(exts[0]);
					setPriority("1");

					break;

				}

				setContext("absencetransfer");
				setExtension("s");
				setPriority("1");
				setVariable("LCEXT", exten);
				setVariable("TRAN1", exts[0]);
				setVariable("TRAN2", exts[1]);

				break;

			}

		case 3:

			setContext("ext-group");
			setExtension(ringGroup);
			setPriority("1");

			break;

		case 4:
			String allPhones = phones.replace(":", "&SIP/xo/");
			allPhones = "SIP/xo/" + allPhones;
			setVariable("ALLPHONES", allPhones);

			setContext("RingAllCells");
			setExtension(exten);
			setPriority("1");

			break;

		default: // extension => cell

		}

	}
	boolean isExitsTmpPrompt(String exten)
	{
		boolean exits = false;
		String fullPath = "/var/spool/asterisk/voicemail/default/" + exten + "/temp.wav";
		
		File fTmp = new File(fullPath);
		
		exits = fTmp.exists();
		
		fTmp = null;
		
		
		
		return exits;
	}

	private void streamString(String pre, String prompt) throws AgiException {

		String unid = getVariable("UNIQUEID"); // get channel uniqueid

		String tmpwav = "/tmp/" + pre + unid; // define the wav file name

		String[] cmds = new String[3]; // use the linux command to get the wav
										// file
		cmds[0] = "/var/javalib/swiftStr.sh"; // the linux shell file
		cmds[1] = tmpwav + ".wav"; // the destination wav file
		cmds[2] = prompt; // the source string

		try {

			Process proc = Runtime.getRuntime().exec(cmds); // run the linux
															// command

			if (proc != null) {
				proc.waitFor(); // wait till the command is executed
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		streamFile(tmpwav); // play the wav file

		File f = new File(tmpwav + ".wav");

		f.delete(); // delete the temporay wav file.

	}

}
