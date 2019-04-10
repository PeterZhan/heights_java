package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import it.sauronsoftware.base64.Base64;
import java.io.*;

public class getCurAllRepUnheard extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String empName = getVariable("EMPNAME");

		String replist = getVariable("REPLIST");
		String[] reps = replist.split(":");

		String curnum = getVariable("CURNUM");

		String datadoc = getVariable("EMPDATA");
		int icur = Integer.parseInt(curnum);

		String idx = reps[icur - 1];

		String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = idx;
		opts[2] = datadoc;

		String cmd = "GETREPORTBYINDX";

		String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";

		NotesWSClient wsclient = new NotesWSClient(url);

		String[] res = wsclient.generalCommand(cmd, opts);

		setVariable("VARTEST", res[9]);
		setVariable("VARTEST", res[0]);

		String filewav = res[0];

		boolean isAmr = false;
		if (filewav.contains(".amr")) {
			filewav = filewav.replace(".amr", "");
			isAmr = true;
		}

		String bldg = res[1];
		String bldgwithb = "";

		// String db = res[2];

		for (int i = 0; i < bldg.length() - 1; i++) {
			bldgwithb = bldgwithb + bldg.substring(i, i + 1) + " ";
		}

		bldgwithb = bldgwithb + bldg.substring(bldg.length() - 1);

		// setVariable("VARTEST", res[10]);

		String fname = "/var/spool/asterisk/monitor/" + filewav;

		File f = new File(fname + ".WAV");
		File famr = null;

		if (isAmr) {
			famr = new File(fname + ".amr");

		}

		// File fanother = new File("/var/javalib/" + filewav + ".WAV");

		if (res[9].equals("1") && !famr.exists()) {
			try {

				byte[] sdata = res[10].getBytes("UTF-8");
				byte[] bdata = Base64.decode(sdata);
				FileOutputStream fos = null;
				if (isAmr)
					fos = new FileOutputStream(famr);
				else
					fos = new FileOutputStream(f);
				fos.write(bdata);
				fos.close();

				if (isAmr)
					ConvertFileAmr(fname);

			} catch (Exception e) {
				setVariable("VARTEST", e.toString());
			}
		}

		// if (!f.exists() && !isAmr)
		// fname = "/var/spool/asterisk/lastmonth/" + filewav;

		if (fname.contains("&"))
			fname = fname.replace("&", "&/var/spool/asterisk/monitor/");

		setVariable("CURDAILYREP", fname);
		setVariable("BLDG", bldgwithb);
		setVariable("REPDATE", res[2]);
		setVariable("REPTIME", res[3]);
		setVariable("PLAYHS", res[4]);
		setVariable("CALLDOC", res[5]);
		setVariable("CALLDB", res[6]);
		setVariable("APTNO", res[7]);
		setVariable("DAILYTYPE", res[8]);
		setVariable("MSG", res[12]);

		String absent = getVariable("ABSENT");
		String abmode = getVariable("ABSMODE");
		String abskip = res[11];
		
		String empExt = res[13];
		
		String custDocid = res[14];
		
		String superDocid = res[15];
		
		String orgDocid = res[16];
		
	//	if (!empExt.equals("none")) setVariable("SUPEREXT", empExt);
		
		setVariable("SUPEREXT", empExt);
		setVariable("CUSTDOCID", custDocid);
		setVariable("SUPERDOCID", superDocid);
		setVariable("ORGDOCID", orgDocid);

		if (absent == null || abmode == null) {
		} else {

			if (absent.equals("Yes") && abmode.equals("1") && abskip.equals("1"))

			{

				setExtension("##");
				setPriority("1");

			}

		}

	}

	private void runshellcmds(String[] cmds) {
		Process pid = null; // the process id

		try {

			pid = Runtime.getRuntime().exec(cmds); // run the shell command

			if (pid != null) {
				pid.waitFor(); // wait for the command is finished
			}

		} catch (Exception e) {

			System.out.println(e);

		}

	}

	private void ConvertFileAmr(String fname) {

		String src = fname + ".amr";
		String mid = fname + ".wav";
		// String dst = fname + ".WAV";

		String[] cmd1 = new String[6];
		cmd1[0] = "/usr/local/bin/ffmpeg";
		cmd1[1] = "-i";
		cmd1[2] = src;
		cmd1[3] = "-ab";
		cmd1[4] = "128k";
		cmd1[5] = mid;

		runshellcmds(cmd1);

		/*
		 * 
		 * String[] cmd2 = new String[3]; cmd1[0] =
		 * "/var/javalib/fileconvert.sh"; cmd1[1] = mid; cmd1[2] = dst;
		 * 
		 * // runshellcmds(cmd2);
		 */

		// File Fsrc = new File(src);
		// if (Fsrc.exists()) Fsrc.delete();

		// File Fmid = new File(mid);
		// if (Fmid.exists()) Fmid.delete();

	}

}
