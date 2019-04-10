package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class IncomingCall extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

		String callingNum = getVariable("CALLERID(num)");
		String callingName = getVariable("CALLERID(name)");
		String starttime = getVariable("EPOCH");
		String uid = getVariable("UNIQUEID");
		String did = getVariable("EXTEN");

		setVariable("STARTTIME", starttime);
		setVariable("LEGALP", "silence/1");
		if (callingNum != null) {
			if (callingNum.equals("Restricted"))
				setVariable("CALLERID(num)", "0000");

			if (callingNum.equals("anonymous"))
				setVariable("CALLERID(num)", "0001");

		}

		if (callingNum.startsWith("1") && callingNum.length() == 11) {
			callingNum = callingNum.substring(1);
			setVariable("CALLERID(num)", callingNum);
		}

		String[] opts = new String[6];

		opts[0] = callingNum;
		opts[1] = "in";
		opts[2] = callingName;
		opts[3] = uid;
		opts[4] = did;
		opts[5] = "none";

		try {

			Connection con = null;
			try {
				con = DbConnectionApache.getConnection();

				String sql = "SELECT FName, LName FROM tblmetacontact where PName_1='Employee' and ContactStatus='Current' and WorkDirect='"
						+ did + "'";

				// setVariable("CONNECTION", con.toString());

				Statement stat = con.createStatement();
				ResultSet result = stat.executeQuery(sql);
				// result.next();
				// setVariable("RESULT", result.getString("NameofDate"));

				if (result.next()) {

					opts[5] = result.getString("FName") + " "
							+ result.getString("LName");
					// isEmp = "1";
					// empExt = result.getString("WorkPhoneExt");

				}

			} catch (Exception e) {
				
				setVariable("Error", e.toString());
			} finally {

				DbConnectionApache.FreeCon(con);
			}

			String cmd = "CHECKIFFIRSTCALL";

			String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";

			NotesWSClient wsclient = new NotesWSClient(url);

			String[] res = wsclient.generalCommand(cmd, opts);

			if (res[5].equals("1")) {
				setExtension("h");
				setPriority("1");
				return;

			}
			
			
			setVariable("SPANISHEXT", res[7]);
			
			setVariable("vartst", res[6]);
			
			if (!res[6].equals("0"))
			{
				
				int startTime = 9;
				int endTime = 17;

				Calendar cal = Calendar.getInstance();
				int h = cal.get(Calendar.HOUR_OF_DAY);
				int w = cal.get(Calendar.DAY_OF_WEEK);

				if (w == Calendar.SATURDAY || w == Calendar.SUNDAY || h < 9 || h >= 17)
				{
					setVariable("SUPEROFFWORK", res[6]);
				}
					
				
				
				
				
				
				
				
			}
			

			String logdocid = res[3];
			setVariable("LOGDOCID", logdocid);

			// if (did.equals("2123171423") || (callingNum.length()!=10 &&
			// callingNum.length()!=11))
			// {
			// setVariable("FIRSTCALLIN", "Yes");
			// setVariable("LEGALP", "custom/legalfirst2");
			// } else
			// {

			if (res[0].equals("1") && !did.equals("6465726467")) {
				setVariable("FIRSTCALLIN", "Yes");
				setVariable("LEGALP", "custom/legalfirst2");

			}

			// }

			String isEmp = "0";// res[1];
			String empExt = "";// res[2];
			// Connection con = null;

			try {
				con = DbConnectionApache.getConnection();

				String sql = "SELECT WorkPhoneExt FROM tblmetacontact where PName_1='Employee' and ContactStatus = 'Current' and WorkPhoneExt is NOT NULL and "
						+ " WorkPhoneExt<>'' and (WorkCellular='"
						+ callingNum
						+ "' or WorkPhone='"
						+ callingNum
						+ "' or WorkPhoneFull='"
						+ callingNum
						+ "' or HomePhone='"
						+ callingNum
						+ "' or HomeCellular='"
						+ callingNum
						+ "' or WorkDirect='"
						+ callingNum
						+ "' or WorkSecondary='"
						+ callingNum
						+ "' or HomeDirect='"
						+ callingNum
						+ "' or HomeSecondary='"
						+ callingNum
						+ "')";

				// setVariable("CONNECTION", con.toString());

				Statement stat = con.createStatement();
				ResultSet result = stat.executeQuery(sql);
				// result.next();
				// setVariable("RESULT", result.getString("NameofDate"));

				if (result.next()) {

					isEmp = "1";
					empExt = result.getString("WorkPhoneExt");

				}

			} catch (Exception e) {
				setVariable("Error", e.toString());
			} finally {

				DbConnectionApache.FreeCon(con);
			}

			// String did = getVariable("EXTEN");
			if (did.equals("2123171423") && isEmp.equals("1")) {
				String vmDir = "/var/spool/asterisk/voicemail/default/"
						+ empExt + "/INBOX/";
				String[] cmds = new String[] { "/bin/bash", "-c",
						" find " + vmDir + "/msg* -name \"*.WAV\" | wc -l" };

				setVariable("VARTEST", cmds[0] + " " + cmds[1] + " " + cmds[2]);
				String num = RunCommand.runit(cmds);
				int inum = Integer.parseInt(num);
				setVariable("VARTEST", num);

				if (inum > 0) {

					setVariable("VMNUM", num);
					setVariable("EMPEXT", empExt);
					setVariable("DID", did);
					setContext("EMPLOYEEMENU");
					setExtension("s");
					setPriority("1");

				}

			}

			String msg = res[4];

			/*
			 * if (!msg.equals("0") && did.equals("2123171423")) {
			 * 
			 * setVariable("SPDATE", "Yes");
			 * 
			 * String txtFile = "/tmp/sptext.txt"; String wavFile =
			 * "/tmp/spanno.wav";
			 * 
			 * WriteToFile(txtFile, msg); ConverToWav(wavFile, txtFile);
			 * 
			 * setVariable("SPDATEPROMPT", "/tmp/spanno");
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * }
			 */

			String sql = "insert into tblcalleropts(CallerID, CallDate, AstUniID, LastModified) values('"
					+ callingNum + "'," + " NOW(), '" + uid + "', NOW())";

			DbConnectionApache.sqlExecute(sql);
			
			
		//*****************************************************************	
		/*	
			cmd = "CHECKIFSUPERDID";

			url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";

			wsclient = new NotesWSClient(url);

			res = wsclient.generalCommand(cmd, opts);	
			
			*/
			
			
			
       //******************
		} catch (Exception e) {
			setVariable("VARTEST", e.toString());
			e.printStackTrace();
		}

	}

	// write the sting c into file f
	private void WriteToFile(String f, String c) {
		try {
			FileWriter resultFile = new FileWriter(f); // get the FileWriter of
														// the file
			PrintWriter myFile = new PrintWriter(resultFile); // get the
																// PrintWriter
																// of the file

			myFile.println(c); // wirte the c into file f
			resultFile.close(); // close the file stream
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// convert the text file t to wav file
	private void ConverToWav(String w, String t) {

		String[] cmds = new String[3];
		cmds[0] = "/var/javalib/swift.sh";// the shell command to convert the
											// text into wav file
		cmds[1] = w; // the wav file
		cmds[2] = t; // the text file

		runshellcmds(cmds); // run the shell command

		String[] rmcmds = new String[2]; // run the shell command to delete the
											// text file

		rmcmds[0] = "rm"; // the "rm" command
		rmcmds[1] = t; // the text file

		runshellcmds(rmcmds); // run the command to delete the text file
		// /var/javalib/swift.sh w t

	}

	// run the linux shell command
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

}
