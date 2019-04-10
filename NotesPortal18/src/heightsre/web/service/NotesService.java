package heightsre.web.service;

import it.sauronsoftware.base64.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.io.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import java.util.*;

import org.asteriskjava.manager.event.*;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.action.*;
import org.asteriskjava.manager.response.MailboxCountResponse;
import org.asteriskjava.manager.response.ManagerResponse;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.response.CommandResponse;

public class NotesService {

	private boolean isBeta = false;

	public void generateCall(String local, String remote, String CallerID,
			String DocID) {

		try {
			String hostname = "127.0.0.1";
			SocketFactory factory = null;

			if (false)
				factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			else
				factory = SocketFactory.getDefault();

			Socket clientRequest = factory.createSocket(hostname, 5038);

			BufferedReader input; // 输入流
			PrintWriter output; // 输出流

			InputStreamReader reader;
			OutputStreamWriter writer;

			reader = new InputStreamReader(clientRequest.getInputStream());

			input = new BufferedReader(reader);

			writer = new OutputStreamWriter(clientRequest.getOutputStream());

			output = new PrintWriter(writer, true);

			String actions = "Action: login\r\n" + "Events: off\r\n"
					+ "Username: admin\r\n" + "Secret: amp111\r\n\r\n"
					+ "Action: originate\r\n" + "Channel: SIP/" + local
					+ "\r\n" + "WaitTime: 30\r\n" + "CallerId: " + CallerID
					+ "\r\n" + "Exten: " + remote + "\r\n" + "Async: true\r\n"
					+ "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID
					+ "\r\n" + "Context: clicktodial\r\n"
					+ "Priority: 1\r\n\r\n" + "Action: Logoff\r\n\r\n";

			output.println(actions);

			String line = input.readLine();

			while (line != null) {

				line = input.readLine();
			}

			input.close();
			output.close();
			clientRequest.close();

		} catch (Exception e) {

		}

	}

	public String generateCallWithDbName(String[] options) {

		String res = "Start";

		String local = options[0];
		String remote = options[1];
		String CallerID = options[2];
		String DocID = options[3];
		String dbname = options[4];

		String ifplaylg = "yes";
		if (options.length > 5)
			ifplaylg = options[5];

		String requestnum = "";

		if (options.length > 6)
			requestnum = options[6];

		String sep = "\r\n";
		String reqnumvar = "";
		if (!requestnum.equals(""))
			reqnumvar = "Variable: REQNUM=" + requestnum + sep;

		String ifPlayLg = ",__PLAYLG=" + ifplaylg;

		String oldexten = "";
		String remoteGeneral = remote;

		if (remote.contains("-")) {
			int p = remote.indexOf('-');

			oldexten = remote.substring(p + 1);

			remoteGeneral = remote.substring(0, p);

		}

		if (remote.contains(",,,")) {
			int p = remote.indexOf(',');

			oldexten = remote.substring(p + 3);

			remoteGeneral = remote.substring(0, p);

		}

		String oldextenstr = ",REMOTEEXTEN=" + remote;

		if (!oldexten.equals("")) {
			oldextenstr = oldextenstr + ",__OLDEXTEN=" + oldexten;// +
																	// ",REMOTEEXTEN="
																	// + remote;
		}

		try {
			res = "Start connect";

			String hostname = "127.0.0.1";
			SocketFactory factory = null;

			// System.getProperty("line.seperator");

			if (false)
				factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			else
				factory = SocketFactory.getDefault();

			Socket clientRequest = factory.createSocket(hostname, 5038);

			BufferedReader input; // 输入流
			PrintWriter output; // 输出流

			InputStreamReader reader;
			OutputStreamWriter writer;

			reader = new InputStreamReader(clientRequest.getInputStream());

			input = new BufferedReader(reader);

			writer = new OutputStreamWriter(clientRequest.getOutputStream());

			output = new PrintWriter(writer, true);

			String actions = "Action: Login" + sep + "Events: off" + sep
					+ "Username: admin"
					+ sep
					+ "Secret: amp111"
					+ sep
					+ sep
					+

					"Action: Originate"
					+ sep
					+ "Channel: SIP/"
					+ local
					+ sep
					+
					// "Channel: " + "LOCAL" + "/" + local +
					// "@from-did-direct-ivr" + sep +
					"WaitTime: 30"
					+ sep
					+
					// "CallerId: 2123171423" + sep +

					"WaitTime: 30" + sep + "CallerId: " + CallerID + sep
					+ "Exten: " + remoteGeneral + sep + "Async: true" + sep
					+ "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID
					+ ",DBNAME=" + dbname + oldextenstr + ifPlayLg + sep +

					reqnumvar + "Context: clicktodial" + sep + "Priority: 1"
					+ sep + sep +

					"Action: Logoff" + sep + sep;

			// res = "start send command";
			output.println(actions);

			String line = input.readLine();

			while (line != null) {
				res = res + line + " ";

				line = input.readLine();
			}

			// res = "End sending";

			input.close();
			output.close();
			clientRequest.close();

		} catch (Exception e) {
			res = e.toString();

		}

		return res;

	}

	public String newOriginateCall(String[] options) {

		String res = "Start";

		String local = options[0];
		String remote = options[1];
		String CallerID = options[2];
		String DocID = options[3];
		String dbname = options[4];

		// String ifplaylg = "yes";
		// if (options.length > 5)
		String isMain = options[5];
		String saveAsCallReport = options[6];
		String fieldname = options[7];
		String company = options[8];
		String contact = options[9];

		// String ifPlayLg = ",__PLAYLG=" + ifplaylg;

		String oldexten = "";
		String remoteGeneral = remote;

		if (remote.contains("-")) {
			int p = remote.indexOf('-');

			oldexten = remote.substring(p + 1);

			remoteGeneral = remote.substring(0, p);

		}

		if (remote.contains(",,,")) {
			int p = remote.indexOf(',');

			oldexten = remote.substring(p + 3);

			remoteGeneral = remote.substring(0, p);

		}

		String oldextenstr = ",REMOTEEXTEN=" + remote;

		if (!oldexten.equals("")) {
			oldextenstr = oldextenstr + ",__OLDEXTEN=" + oldexten;// +
																	// ",REMOTEEXTEN="
																	// + remote;
		}

		try {
			/*
			 * res = "Start connect";
			 * 
			 * String hostname = "127.0.0.1"; SocketFactory factory = null;
			 * 
			 * String sep = "\r\n";//System.getProperty("line.seperator");
			 * 
			 * if (false) factory=
			 * (SSLSocketFactory)SSLSocketFactory.getDefault(); else factory =
			 * SocketFactory.getDefault();
			 * 
			 * Socket clientRequest = factory.createSocket(hostname, 5038);
			 * 
			 * BufferedReader input; //输入流 PrintWriter output; //输出流
			 * 
			 * InputStreamReader reader; OutputStreamWriter writer;
			 * 
			 * 
			 * reader = new InputStreamReader(clientRequest.getInputStream());
			 * 
			 * input = new BufferedReader(reader);
			 * 
			 * writer = new OutputStreamWriter(clientRequest.getOutputStream());
			 * 
			 * output = new PrintWriter(writer,true);
			 */
			String sep = "\r\n";
			String calls = "Action: Originate" + sep + "Channel: SIP/" + local
					+ sep + "WaitTime: 30" + sep + "CallerId: " + CallerID
					+ sep + "Exten: " + remoteGeneral + sep + "Async: true"
					+ sep + "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID
					+ ",DBNAME=" + dbname + oldextenstr + sep
					+ "Variable: ISMAIN=" + isMain + sep
					+ "Variable: CALLREPORT=" + saveAsCallReport + sep
					+ "Variable: FIELD=" + fieldname + sep
					+ "Variable: COMPANY=" + company + sep
					+ "Variable: CONTACT=" + contact + sep
					+ "Context: newclicktodial" + sep + "Priority: 1" + sep
					+ sep;

			// res = "start send command";
			makecalls(calls);

			// res = "End sending";

		} catch (Exception e) {
			res = e.toString();

		}

		return res;

	}

	/*
	 * public String generalInterface(String cmd, String[] options, byte[] data)
	 * { String ret = "OK";
	 * 
	 * if (cmd.equals("COPYAMRRECTOLINUX")) {
	 * 
	 * ret = copyAmrRecToLinux(options, data);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * return ret;
	 * 
	 * }
	 */

	private String copyAmrRecToLinux(String[] options) {

		String famr = options[0];
		String fdata = options[1];

		String fwav = famr.replace("amr", "wav");

		fwav = "/var/spool/asterisk/monitor/" + fwav;
		famr = "/tmp/" + famr;

		File f = new File(fwav);
		if (f.exists())
			return "FILEEXISTS";

		try {
			byte[] sdata = fdata.getBytes("UTF-8");
			byte[] bdata = Base64.decode(sdata);
			FileOutputStream fos = null;

			fos = new FileOutputStream(famr);

			fos = new FileOutputStream(f);
			fos.write(bdata);
			fos.close();

			ConvertFileAmr(famr, fwav);

			File f2 = new File(famr);

			if (f2.exists())
				f2.delete();

		} catch (Exception e) {
			return e.toString();
		}

		return "OK";
	}

	private void ConvertFileAmr(String src, String dst) {

		// String src = fname + ".amr";
		// String mid = fname + ".wav";
		// String dst = fname + ".WAV";

		String[] cmd = new String[6];
		cmd[0] = "/usr/local/bin/ffmpeg";
		cmd[1] = "-i";
		cmd[2] = src;
		cmd[3] = "-ab";
		cmd[4] = "128k";
		cmd[5] = dst;

		runshellcmds(cmd);

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

	// this is the general interface method for web service call from notes side
	public String generalInterface(String cmd, String[] options) {
		String ret = "OK";

		if (cmd.contains("(beta)")) // if it contain "beta", then it's beta
									// version
		{
			isBeta = true;

			cmd = cmd.replace("(beta)", ""); // get the command

		}

		if (cmd.equals("ORIGINATECALL")) // this command is for click to call
		{

			ret = generateCallWithDbName(options);

		}

		if (cmd.equals("NEWORIGINATECALL")) {
			ret = newOriginateCall(options);
		}

		if (cmd.equals("RUNPAYREMINDER")) // this command is for payment
											// reminder auto call
		{

			makepayremainder(options);

		}

		if (cmd.equals("RUNWEBCALL")) // this command is for web click call from
										// internal employee
		{

			ret = makewebcall(options);

		}

		if (cmd.equals("RUNLEGALOFF")) // this command is for access date auto
										// call
		{
			/*
			 * String[] cmds = new String[4];
			 * 
			 * cmds[0] = "/usr/bin/nohup"; cmds[1] = "/opt/jdkrun/jre/bin/java";
			 * cmds[2] = "heightsre.java.prog.getLegalOff"; cmds[3] = "&"; try{
			 * Runtime.getRuntime().exec(cmds); //
			 * Runtime.getRuntime().exec("exit"); }catch(Exception e) { //ret =
			 * e.toString(); }
			 * 
			 * ret = "RUNLEGALOFF exec is ok";
			 */

			makeAccessDateCalls(options);

		}

		if (cmd.equals("SUPERDAILYCALL")) // this command is for super daily
											// call
		{

			makeSuperDailyCall(options);

		}

		if (cmd.equals("CONVERTTEXTTOAUDIO")) // this command is for converting
												// text to wav file
		{

			convertTextToAudio(options);

		}

		if (cmd.equals("RFPDAILYCALL")) // this command is for rfp dialy call
		{

			rfpdailycall(options);

		}

		if (cmd.equals("RUNWEBCALLEXTEXT")) // this command is for web click to
											// call from external to external
		{

			runWebCallExtExt(options);

		}
		if (cmd.equals("RUNWEBCALLEXTINT")) // this command is for web click to
											// call from external to internal
		{
			runWebCallExtInt(options);
		}

		if (cmd.equals("RENTQUEADD")) // this command is for agent entering the
										// rental call queue
		{
			rentqueueadd(options);
		}

		if (cmd.equals("RENTQUEREMOVE")) // this command is for agent leaving
											// the rental call queue
		{
			rentqueueremove(options);
		}

		if (cmd.equals("CONFWITHSUPER")) // this command is for conferrence with
											// SUPER/MANAGER
		{

			ret = confWithSuper(options);

		}
		if (cmd.equals("RUNWEBCALLBYCELL")) // this command is for conferrence
											// with SUPER/MANAGER
		{

			ret = webcallByCell(options);

		}

		if (cmd.equals("RUNWEBCALLBYCELLFORVM")) // this command is for
													// conferrence with
													// SUPER/MANAGER
		{

			ret = webcallByCellForVm(options);

		}

		if (cmd.equals("HANGUPAUTOCALLBACK")) // this command is for conferrence
												// with SUPER/MANAGER
		{

			ret = hangupAutoCallback(options);

		}

		if (cmd.equals("EMPLOYEEDAILYCALL")) {
			employeeDailyCall(options);
		}

		if (cmd.equals("REMINDERTOPUNCHIN")) {
			reminderToPunchIn(options);
		}

		if (cmd.equals("VOICEMAILBYCELL")) // this command is for conferrence
											// with SUPER/MANAGER
		{

			ret = voicemailbycell(options);

		}

		if (cmd.equals("SUPERRFPDAILYCALL")) // this command is for conferrence
												// with SUPER/MANAGER
		{

			ret = superRFPDailyCall(options);

		}

		if (cmd.equals("SUPERVACDAILYCALL")) // this command is for conferrence
												// with SUPER/MANAGER
		{

			ret = superVACDailyCall(options);

		}

		if (cmd.equals("GETCALLING")) {
			ret = getCallingRecord(options);
		}

		if (cmd.equals("TEST")) {
			ret = test(options);
		}

		if (cmd.equals("OILFORSHIPDATE")) {
			ret = getOilShipDate(options);
		}

		if (cmd.equals("LEAVECOMMENTSFORPIC")) {
			ret = leaveCommentsForPic(options);
		}

		if (cmd.equals("FORCELLPUNCHCALLBACK")) {
			ret = cellPunchCallBack(options);
		}

		if (cmd.equals("SERVICECALLPROMPT")) {
			serviceCallPrompt(options);
		}

		if (cmd.equals("SUPERCALLPROMPT")) {
			superCallPrompt(options);
		}

		if (cmd.equals("VENDORCONFPROMPT")) {
			vendorConfPrompt(options);
		}

		if (cmd.equals("RFPPROMPT")) {
			rfpPrompt(options);
		}

		if (cmd.equals("REPORTFORLEAVEOFF")) {
			reportLeaveOffByCell(options);
		}

		if (cmd.equals("PHONECONFERENCE")) {
			ret = phoneconference(options);
		}

		if (cmd.equals("DELETECONFERENCE")) {
			ret = deleeteconference(options);
		}

		if (cmd.equals("PHONECONFBYXLITE")) {
			ret = phoneconfbyxlite(options);
		}

		if (cmd.equals("PHONECONFBYCELL")) {
			ret = phoneconfbycell(options);
		}

		if (cmd.equals("WEBCLICKTOCALLBYTYPE")) {
			ret = webClickToCallByType(options);
		}

		if (cmd.equals("DICBOARDWEBCLICKTOCALLBYTYPE")) {
			ret = discBoardWebClickCall(options);
		}

		if (cmd.equals("HPDDAILYCALLSUPER")) {
			ret = HPDDailyCallSuper(options);
		}

		if (cmd.equals("INCOMINGCALLFORWARD")) {
			ret = procIncomingForward(options);
		}

		if (cmd.equals("DELETEALLFORWARD")) {
			ret = deleIncomingForward(options);
		}

		if (cmd.equals("LISTENTODAILY")) {
			ret = listenToDaily(options);
		}

		if (cmd.equals("SAVECALLSCREENING")) {
			ret = saveCallScreening(options);
		}

		if (cmd.equals("CALLSUPERAFTERTENCOMP")) {
			ret = callSuperAfterTenComp(options);
		}

		if (cmd.equals("CALLTENANTFOREMAIL")) {
			ret = callTenForEmail(options);
		}

		if (cmd.equals("MAKECALLPROMPTS")) {
			ret = makeCallPrompts(options);
		}
		
		if (cmd.equals("SPANISHMAKECALLPROMPTS")) {
			ret = makeCallSpanishPrompts(options);
		}

		if (cmd.equals("CALLPROSPECT")) {
			ret = callProspect(options);
		}

		if (cmd.equals("CALLPROSPECTBYEXT")) {
			ret = callProspectByExt(options);
		}

		if (cmd.equals("CALLPROSPECTV2")) {
			ret = callProspectV2(options);
		}

		if (cmd.equals("CALLPROSPECTBYEXTV2")) {
			ret = callProspectByExtV2(options);
		}

		if (cmd.equals("DAILYCALLINONE")) {
			ret = dailyCallInOne(options);
		}

		if (cmd.equals("GOOGLEASR")) {
			ret = googleAsr(options);
		}

		if (cmd.equals("CONNECTSUPERTEN")) {
			ret = connectSuperTen(options);
		}

		if (cmd.equals("MAKECALLANDRECORD")) {
			ret = makeCallAndRecord(options);
		}

		if (cmd.equals("MAKECALLANDCOMMENT")) {
			ret = makeCallAndComment(options);
		}

		if (cmd.equals("MAKECALLANDCOMMENTBYEXT")) {
			ret = makeCallAndCommentByExt(options);
		}

		if (cmd.equals("BROADCASTMESSAGE")) {
			ret = broadCastMessage(options);
		}

		if (cmd.equals("BROADCASTMESSAGEINTEXT")) {
			ret = broadCastMessageInText(options);
		}

		if (cmd.equals("NEWDAILYCALLINONE")) {
			ret = newdailyCallInOne(options);
		}

		if (cmd.equals("COPYAMRRECTOLINUX")) {

			ret = copyAmrRecToLinux(options);

		}

		if (cmd.equals("CREATELUMONVOXGRAMMERS")) {

			ret = createLumonVoxGram(options);

		}

		if (cmd.equals("CREATELUMONVOXGRAMMERSFORKEY")) {

			ret = createLumonVoxGramForKey(options);

		}

		if (cmd.equals("CREATELMVOXGRAMMERS2")) {

			ret = createLumonVoxGram2(options);

		}

		if (cmd.equals("CREATELMVOXGRAMMERS2FORKEY")) {

			ret = createLumonVoxGram2ForKey(options);

		}

		if (cmd.equals("GETVOICEMAILCOUNT")) {

			ret = getVoiceMailCount(options);

		}

		if (cmd.equals("TRANSFROMEXTTOCELL")) {

			ret = transferFromExtToCell(options);

		}

		if (cmd.equals("GETRECORDINGBYFILENAME")) {

			ret = getRecordingByFileName(options);

		}

		if (cmd.equals("MAKECONFERENCETOCELL")) {

			ret = makeConfToCell(options);

		}

		if (cmd.equals("MERGEALLCALLS")) {

			ret = mergeCalls(options);

		}
		
		if (cmd.equals("CALLTENANTSFORRENEWAL")) {

			ret = callTenantForRenewal(options);

		}
		
		if (cmd.equals("CALLCOMMERCIALTENANTSFORRENEWAL")) {

			ret = callCommercialForRenewal(options);

		}
		if (cmd.equals("MAKECONFERENCETOCELLBYCHANNEL")) {

			ret = makeConfCallByChann(options);

		}
		if (cmd.equals("CALLCOMMERCIALTENANTSFORCOI")) {

			ret = callCommercialForCOI(options);

		}
		if (cmd.equals("CALLVENDORSOFEXPIREDPERMIT")) {

			ret = callVendorsOfExpPermit(options);

		}
		
		if (cmd.equals("CALLFORECBVIOLATIONS")) {

			ret = callForEcbViolations(options);

		}
		
		if (cmd.equals("AutoCallEstoppel")) {

			ret = callForEstoppel(options);

		}
		
		if (cmd.equals("AutoCallTimeSheet")) {

			ret = callForTimeSheet(options);

		}
		
		if (cmd.equals("AutoCallNuisance")) {

			ret = callForTenNuisance(options);

		}
		
		if (cmd.equals("AutoCallWindowGuard")) {

			ret = callForWindowGuard(options);

		}
		
		return ret;
	}
	
	private String callForWindowGuard(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String Tenant = options[1];
		//String deadlinedate = options[2];
		//String FormPhone = options[3];
		String phone = trimPhone(options[2]);
		//String landlord = options[4].replace(",", " ");
		//String bldg_no = options[5].replace(",", " ");;
		//String managerCell = trimPhone(options[6]);
		//String nuiType = options[7];
		//String startDate = options[8];
		//String strDue = options[9];
		
		/*
		if (deadlinedate.equals("")) deadlinedate = "none";
		if (landlord.equals("")) landlord = "none";
		if (managerCell.equals("")) managerCell = "none";
		if (startDate.equals("")) startDate = "none";*/
		//String bldgAddr = options[4];
	
		//String issAgency = options[5];
		
		//String lmsg = options[6];
		//lmsg = lmsg.replace(",", ".");
		
		
		String call = "Action: originate\r\n" 
		          //  + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callWindowGuard/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: TENANT=" + Tenant + "\r\n" 
				 //   + "Variable: DEADLINEDATE=" + deadlinedate + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				  //  + "Variable: FORMPHONE=" + FormPhone + "\r\n" 
				   // + "Variable: ISSAGENCY=" + issAgency + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n"
				 //   + "Variable: LANDLORD="   + landlord + "\r\n"
				   
				 //   + "Variable: BLDGNO="   + bldg_no + "\r\n"
				//	+ "Variable: MANAGERCELL="   + managerCell + "\r\n"		
				//	+ "Variable: NUITYPE="   + nuiType + "\r\n"	
				//	+ "Variable: STARTDATE="   + startDate + "\r\n"	
				//	+ "Variable: DATEDUE="   + strDue + "\r\n"	
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callCustomerWindowGuard\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	
	private String callForTenNuisance(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String Tenant = options[1];
		String deadlinedate = options[2];
		String FormPhone = options[3];
		String phone = trimPhone(options[3]);
		String landlord = options[4].replace(",", " ");
		String bldg_no = options[5].replace(",", " ");;
		String managerCell = trimPhone(options[6]);
		String nuiType = options[7];
		String startDate = options[8];
		String strDue = options[9];
		
		
		if (deadlinedate.equals("")) deadlinedate = "none";
		if (landlord.equals("")) landlord = "none";
		if (managerCell.equals("")) managerCell = "none";
		if (startDate.equals("")) startDate = "none";
		//String bldgAddr = options[4];
	
		//String issAgency = options[5];
		
		//String lmsg = options[6];
		//lmsg = lmsg.replace(",", ".");
		
		
		String call = "Action: originate\r\n" 
		          //  + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callNuisance/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: TENANT=" + Tenant + "\r\n" 
				    + "Variable: DEADLINEDATE=" + deadlinedate + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: FORMPHONE=" + FormPhone + "\r\n" 
				   // + "Variable: ISSAGENCY=" + issAgency + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n"
				    + "Variable: LANDLORD="   + landlord + "\r\n"
				   
				    + "Variable: BLDGNO="   + bldg_no + "\r\n"
					+ "Variable: MANAGERCELL="   + managerCell + "\r\n"		
					+ "Variable: NUITYPE="   + nuiType + "\r\n"	
					+ "Variable: STARTDATE="   + startDate + "\r\n"	
					+ "Variable: DATEDUE="   + strDue + "\r\n"	
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callcustomersNuisance\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	private String callForTimeSheet(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String EmpName = options[1];
		String num = options[2];
		String strEnd = options[3];
		String phone = trimPhone(options[4]);
		
		
		
		//String bldgAddr = options[4];
	
		//String issAgency = options[5];
		
		//String lmsg = options[6];
		//lmsg = lmsg.replace(",", ".");
		
		
		String call = "Action: originate\r\n" 
		          //  + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callTimeSheet/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: EMPNAME=" + EmpName + "\r\n" 
				    + "Variable: NUM=" + num + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: STREND=" + strEnd + "\r\n" 
				 
				    + "Variable: DOCID="   + docid + "\r\n"
			
								    
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callsuperTimeSheet\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	
	
	private String callForEstoppel(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String company = options[1];
		String senddate = options[2];
		String FormPhone = options[3];
		String phone = trimPhone(options[3]);
		String landlord = options[4].replace(",", " ");
		String bldg_no = options[5].replace(",", " ");;
		String managerCell = trimPhone(options[6]);
		
		
		if (senddate.equals("")) senddate = "none";
		if (landlord.equals("")) landlord = "none";
		if (managerCell.equals("")) managerCell = "none";
		//String bldgAddr = options[4];
	
		//String issAgency = options[5];
		
		//String lmsg = options[6];
		//lmsg = lmsg.replace(",", ".");
		
		
		String call = "Action: originate\r\n" 
		          //  + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callEstoppel/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: COMPANY=" + company + "\r\n" 
				    + "Variable: SENDDATE=" + senddate + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: FORMPHONE=" + FormPhone + "\r\n" 
				   // + "Variable: ISSAGENCY=" + issAgency + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n"
				    + "Variable: LANDLORD="   + landlord + "\r\n"
				   
				    + "Variable: BLDGNO="   + bldg_no + "\r\n"
					+ "Variable: MANAGERCELL="   + managerCell + "\r\n"			    
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callvendorsEstoppel\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	private String strSplitByBlank(String s)
	{
		String res = "";
		
		for (int i=0; i<s.length(); i++)
		{
			res += s.substring(i, i+1) + " ";
		}
		
		
		
		return res;
		
	}
	
	private String callForEcbViolations(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String vendor = options[1];
		String contactName = options[2];
		String FormPhone = options[3];
		String phone = trimPhone(options[3]);
		
		
		String bldgAddr = options[4];
		
		int ib = bldgAddr.indexOf(' ');
		String code = bldgAddr.substring(0, ib);
		code = strSplitByBlank(code);
		
		bldgAddr = code + bldgAddr.substring(ib);
		
		
		
	
		String issAgency = options[5];
		
		String lmsg = options[6];
		lmsg = lmsg.replace(",", ".");
		
		
		String call = "Action: originate\r\n" 
		          //  + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callvdrECB/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: VENDOR=" + vendor + "\r\n" 
				    + "Variable: CONTACT=" + contactName + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: FORMPHONE=" + FormPhone + "\r\n" 
				    + "Variable: ISSAGENCY=" + issAgency + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n"
				    + "Variable: LMSG="   + lmsg + "\r\n"
				   
				    + "Variable: BLDGADDR="   + bldgAddr + "\r\n"
								    
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callvendorsECBViolations\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	
	
	
	private String trimPhone(String PhoneNum) {
		// PhoneNum = PhoneNum.replaceAll("[(,),\\-, ,]", "");
		PhoneNum = PhoneNum.replaceAll("[^\\d]", "");
		return PhoneNum;
	}
	
	private String callVendorsOfExpPermit(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String vendor = options[1];
		String contactName = options[2];
		String FormPhone = options[3];
		String phone = trimPhone(options[3]);
		String docids = options[4];
		String NUM = options[5];
		
		
		
		
		if (docids.endsWith("*")){
			docids = docids.substring(0, docids.length()-1);
			
			
		}
		
		/*
		String expDate = options[4];
		
		String expDate30 = options[5];
		String bldgAddr = options[6];
		String permitNo = options[7];
		String description = options[8];
		String issueDate = options[9];
		
		description = description.replace(",", ".");
		
		permitNo = permitNo.replace("", " ").trim();
		*/
		
		String call = "Action: originate\r\n" 
		          //  + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callvdrpermexp/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: VENDOR=" + vendor + "\r\n" 
				    + "Variable: CONTACT=" + contactName + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: FORMPHONE=" + FormPhone + "\r\n" 
				   // + "Variable: EXPDATE=" + expDate + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n"
				    + "Variable: DOCIDS="   + docids + "\r\n"
				    + "Variable: NUM="   + NUM + "\r\n"
				    
				/*    + "Variable: EXPDATE30="   + expDate30 + "\r\n"
				    + "Variable: BLDGADDR="   + bldgAddr + "\r\n"
				    + "Variable: PERMNO="   + permitNo + "\r\n"
				    + "Variable: DESCRIP="   + description + "\r\n"
				    + "Variable: ISSDATE="   + issueDate + "\r\n"
				   */ 
				    
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callvendorsExpPermit\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	private String callCommercialForCOI(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String mailAddr = options[1];
		String phones = options[2];
		String bldgAddress = options[3];
		//String expDate = options[3];
		//String phones = options[4];
	//	String TenDocID = options[5];
		String[] phoneArray = phones.split("&");
		String coirec = "coiCom" + System.currentTimeMillis();
		
		if (phoneArray.length <= 0) return res;
		
		String NamePhone = phoneArray[0];
		String[] NameAPhone = NamePhone.split("-");
		String tenantName = NameAPhone[0];
		String phone = NameAPhone[1];
		//res = lordName;
		
		String apdPhones = "";
		
		if (phoneArray.length > 1){
			
			apdPhones = "Variable: PHONES=";
			for (int i=1; i<phoneArray.length-1; i++)
			{
				
				apdPhones  += phoneArray[i] + "&";
				
			}
			apdPhones  += phoneArray[phoneArray.length-1];
			
			apdPhones  += "\r\n";
		}
		
		
		String call = "Action: originate\r\n" 
		           // + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callcomcellCOI/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: TENANT=" + tenantName + "\r\n" 
				    + "Variable: MAILADDR=" + mailAddr + "\r\n" 
				    + "Variable: ADDRESS=" + bldgAddress + "\r\n" 
				    
				//    + "Variable: LORD="	+ lordName + "\r\n" 
				//    + "Variable: EXPDATE=" + expDate + "\r\n" 
				    + "Variable: coirec=" + coirec + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: NAMEPHONE=" + NamePhone + "\r\n" 
				 //   + "Variable: OPT=-1" + "\r\n" 
				//    + "Variable: TENDOC=" + TenDocID + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n" + apdPhones
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callComforcoi\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	private String callCommercialForRenewal(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String suffix = options[1];
		String lordName = options[2];
		String expDate = options[3];
		String phones = options[4];
	//	String TenDocID = options[5];
		String[] phoneArray = phones.split("&");
		String leaserec = "leaseCom" + System.currentTimeMillis();
		
		if (phoneArray.length <= 0) return res;
		
		String NamePhone = phoneArray[0];
		String[] NameAPhone = NamePhone.split("-");
		String tenantName = NameAPhone[0];
		String phone = NameAPhone[1];
		res = lordName;
		
		String apdPhones = "";
		
		if (phoneArray.length > 1){
			
			apdPhones = "Variable: PHONES=";
			for (int i=1; i<phoneArray.length-1; i++)
			{
				
				apdPhones  += phoneArray[i] + "&";
				
			}
			apdPhones  += phoneArray[phoneArray.length-1];
			
			apdPhones  += "\r\n";
		}
		
		
		String call = "Action: originate\r\n" 
		           // + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callcomcelloutgoing/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: TENANT=" + tenantName + "\r\n" 
				    + "Variable: SUFFIX=" + suffix + "\r\n" 
				    + "Variable: LORD="	+ lordName + "\r\n" 
				    + "Variable: EXPDATE=" + expDate + "\r\n" 
				    + "Variable: leaserec=" + leaserec + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: NAMEPHONE=" + NamePhone + "\r\n" 
				 //   + "Variable: OPT=-1" + "\r\n" 
				//    + "Variable: TENDOC=" + TenDocID + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n" + apdPhones
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: callComforrenewal\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	
	private String makeConfCallByChann(String[] options) {
		String res = "OK";
		String extChannel = options[0];
		String phonenum = options[1];
		String ext = "";
		
		if (options.length > 2)
			ext = options[2];

		//String extChannel = "";

	//	CommandAction commandAction = new CommandAction("core show channels");
		AstConnection astConn = new AstConnection();
		CommandResponse response;
		try {
			/*response = (CommandResponse) astConn.sendAction(commandAction);

			// res += response;
			for (String line : response.getResult()) {
				//
				if (line.startsWith("SIP/" + exten)) {
					// res += line;
					extChannel = line.substring(0, 21).replace(" ", "");
					// res += extChannel;
					break;

				}
			}*/

			if (!extChannel.equals("")) {

				CommandAction commandAction = new CommandAction("core show channel "
						+ extChannel);
				response = (CommandResponse) astConn.sendAction(commandAction);
				String brgChannel = "";
				for (String line : response.getResult()) {
					if (line.startsWith("BRIDGEPEER")) {

						brgChannel = line.split("=")[1];
						// res += brgChannel;
						break;

					}

				}

				if (!brgChannel.equals("")) {
					RedirectAction ac = new RedirectAction();
					ac.setChannel(brgChannel);
					ac.setExtraChannel(extChannel);
					ac.setExten(phonenum);
					ac.setContext("conferenceFromAndroid");

					ac.setPriority(1);

					astConn.sendAction(ac);

					String chann = "Local/" + phonenum + "@from-internal/n";//"SIP/xo/" + phonenum;//"Local/" + phonenum + "@localcallcontext/n";// extension@context[/n]"SIP/xo/"
																			// +
																			// phonenum;

					/*
					 * if (phonenum.length() == 3) { chann = "SIP/" + phonenum;
					 * }
					 */
					OriginateAction oa = new OriginateAction();
					oa.setChannel(chann);
					oa.setCallerId("2123171423");
					
					if (!ext.equals(""))
						oa.setCallerId(ext);
					oa.setVariable("LOCALEXTEN", ext);
					oa.setTimeout(60000);
					oa.setExten(phonenum);
					oa.setContext("conferenceForCell");
					oa.setPriority(1);
					oa.setAsync(Boolean.TRUE);

					astConn.sendAction(oa);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		}

		return res;
	}

	
	
	private String callTenantForRenewal(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String suffix = options[1];
		String lordName = options[2];
		String expDate = options[3];
		String phones = options[4];
	//	String TenDocID = options[5];
		String[] phoneArray = phones.split("&");
		String leaserec = "lease" + System.currentTimeMillis();
		
		if (phoneArray.length <= 0) return res;
		
		String NamePhone = phoneArray[0];
		String[] NameAPhone = NamePhone.split("-");
		String tenantName = NameAPhone[0];
		String phone = NameAPhone[1];
		res = lordName;
		
		String apdPhones = "";
		
		if (phoneArray.length > 1){
			
			apdPhones = "Variable: PHONES=";
			for (int i=1; i<phoneArray.length-1; i++)
			{
				
				apdPhones  += phoneArray[i] + "&";
				
			}
			apdPhones  += phoneArray[phoneArray.length-1];
			
			apdPhones  += "\r\n";
		}
		
		
		String call = "Action: originate\r\n" 
		           // + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@callcelloutgoing/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: TENANT=" + tenantName + "\r\n" 
				    + "Variable: SUFFIX=" + suffix + "\r\n" 
				    + "Variable: LORD="	+ lordName + "\r\n" 
				    + "Variable: EXPDATE=" + expDate + "\r\n" 
				    + "Variable: leaserec=" + leaserec + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: NAMEPHONE=" + NamePhone + "\r\n" 
				 //   + "Variable: OPT=-1" + "\r\n" 
				//    + "Variable: TENDOC=" + TenDocID + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n" + apdPhones
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: calltenforrenewal\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
	
		
		
		
		return res;
	}
	

	private String mergeCalls(String[] options) {
		String res = "OK";
		String exten = options[0];

		String extChannel = "";
		ArrayList hschannel = new ArrayList();

		CommandAction commandAction = new CommandAction("core show channels");
		AstConnection astConn = new AstConnection();
		RedirectAction ac = new RedirectAction(); 
		CommandResponse response;
		try {
			response = (CommandResponse) astConn.sendAction(commandAction);

			// res += response;
			for (String line : response.getResult()) {
				//
				if (line.startsWith("SIP/" + exten)) {
					// res += line;
					extChannel = line.substring(0, 21).replace(" ", "");
					
				//	GetVarAction getVarAction = new GetVarAction(extChannel, "CDR(userfield)");
				//	 ManagerResponse mresponse = astConn.sendAction(getVarAction);
				//	 String userfield = mresponse.getAttribute("Value");
					// System.out.println("MY_VAR on " + channel + " is " + value);
					 
					// res += extChannel;

					if (!extChannel.equals("")) {

						commandAction = new CommandAction("core show channel "
								+ extChannel);
						response = (CommandResponse) astConn
								.sendAction(commandAction);
						String brgChannel = "";
						for (String linepeer : response.getResult()) {
							if (linepeer.startsWith("BRIDGEPEER")) {

								brgChannel = linepeer.split("=")[1];
							//	hschannel.add(brgChannel);
								if (!brgChannel.equals(""))
								{
									
									ac.setChannel(extChannel);
									ac.setExtraChannel(brgChannel); 
									ac.setExten(exten);
									ac.setContext("conferenceFromMicro");
									
									ac.setPriority(1);
									
									astConn.sendAction(ac);
									
									
									
								}

							}

						}

					}
				}
				
				
			/*	if (!extChannel.equals("") && hschannel.size()>1)
				{
					int count = hschannel.size();
					OriginateAction oa = new OriginateAction();
					for (int i=0; i<count-1; i++){
						
						String chann = (String)hschannel.get(i);
						oa.setChannel(chann); 
						oa.setCallerId("2123171423");
						oa.setTimeout(60000); 
						oa.setExten(exten);
						oa.setContext("conferenceFromMicro"); 
						oa.setPriority(1);
						oa.setAsync(Boolean.TRUE);
						
						astConn.sendAction(oa);
						
						
						
						
						
					}
					
					String brgChannel = (String)hschannel.get(count-1);
					RedirectAction ac = new RedirectAction(); 
					ac.setChannel(extChannel);
					ac.setExtraChannel(brgChannel); 
					ac.setExten(exten);
					ac.setContext("conferenceFromMicro");
					
					ac.setPriority(1);
					
					astConn.sendAction(ac);
					
					
					
					
				}*/

				/*
				 * if (!extChannel.equals("")) {
				 * 
				 * commandAction = new CommandAction("core show channel " +
				 * extChannel); response = (CommandResponse)
				 * astConn.sendAction(commandAction); String brgChannel = "";
				 * for (String line : response.getResult()) { if
				 * (line.startsWith("BRIDGEPEER")) {
				 * 
				 * brgChannel = line.split("=")[1]; // res += brgChannel; break;
				 * 
				 * }
				 * 
				 * }
				 * 
				 * if (!brgChannel.equals("")) { RedirectAction ac = new
				 * RedirectAction(); ac.setChannel(brgChannel);
				 * ac.setExtraChannel(extChannel); ac.setExten(exten);
				 * ac.setContext("conferenceFromMicro");
				 * 
				 * ac.setPriority(1);
				 * 
				 * astConn.sendAction(ac);
				 * 
				 * String chann = "Local/" + phonenum +
				 * "@from-internal/n";//extension@context[/n]"SIP/xo/" +
				 * phonenum;
				 * 
				 * if (phonenum.length() == 3) { chann = "SIP/" + phonenum; }
				 * 
				 * OriginateAction oa = new OriginateAction();
				 * oa.setChannel(chann); oa.setCallerId("2123171423");
				 * oa.setTimeout(60000); oa.setExten(exten);
				 * oa.setContext("conferenceForCell"); oa.setPriority(1);
				 * oa.setAsync(Boolean.TRUE);
				 * 
				 * astConn.sendAction(oa);
				 */

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		}

		return res;
	}

	private String makeConfToCell(String[] options) {
		String res = "OK";
		String exten = options[0];
		String phonenum = options[1];

		String extChannel = "";

		CommandAction commandAction = new CommandAction("core show channels");
		AstConnection astConn = new AstConnection();
		CommandResponse response;
		try {
			response = (CommandResponse) astConn.sendAction(commandAction);

			// res += response;
			for (String line : response.getResult()) {
				//
				if (line.startsWith("SIP/" + exten)) {
					// res += line;
					extChannel = line.substring(0, 21).replace(" ", "");
					// res += extChannel;
					break;

				}
			}

			if (!extChannel.equals("")) {

				commandAction = new CommandAction("core show channel "
						+ extChannel);
				response = (CommandResponse) astConn.sendAction(commandAction);
				String brgChannel = "";
				for (String line : response.getResult()) {
					if (line.startsWith("BRIDGEPEER")) {

						brgChannel = line.split("=")[1];
						// res += brgChannel;
						break;

					}

				}

				if (!brgChannel.equals("")) {
					RedirectAction ac = new RedirectAction();
					ac.setChannel(brgChannel);
					ac.setExtraChannel(extChannel);
					ac.setExten(exten);
					ac.setContext("conferenceFromMicro");

					ac.setPriority(1);

					astConn.sendAction(ac);

					String chann = "Local/" + phonenum + "@from-internal/n";// extension@context[/n]"SIP/xo/"
																			// +
																			// phonenum;

					/*
					 * if (phonenum.length() == 3) { chann = "SIP/" + phonenum;
					 * }
					 */
					OriginateAction oa = new OriginateAction();
					oa.setChannel(chann);
					oa.setCallerId("2123171423");
					oa.setTimeout(60000);
					oa.setExten(exten);
					oa.setContext("conferenceForCell");
					oa.setPriority(1);
					oa.setAsync(Boolean.TRUE);

					astConn.sendAction(oa);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		}

		return res;
	}

	private String getRecordingByFileName(String[] options) {
		String res = "none";
		String dir = "/var/spool/asterisk/monitor/";
		String fname = dir + options[0];

		File f = new File(fname);
		if (f.exists()) {
			long fsize = f.length();
			byte[] data = new byte[(int) fsize];

			FileInputStream fin;
			try {
				fin = new FileInputStream(f);

				fin.read(data);

				fin.close();

				res = org.apache.axis.encoding.Base64.encode(data);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return res;
	}

	private String transferFromExtToCell(String[] options) {
		String res = "START";
		String ext = options[0];
		String cell = options[1];

		String extChannel = "";

		CommandAction commandAction = new CommandAction("core show channels");
		AstConnection astConn = new AstConnection();
		CommandResponse response;
		try {
			response = (CommandResponse) astConn.sendAction(commandAction);

			// res += response;
			for (String line : response.getResult()) {
				//
				if (line.startsWith("SIP/" + ext)) {
					res += line;
					extChannel = line.substring(0, 21).replace(" ", "");
					res += extChannel;
					break;

				}
			}

			if (!extChannel.equals("")) {

				AtxferAction action = new AtxferAction();
				action.setChannel(extChannel);
				action.setExten(cell);
				action.setContext("from-internal-xfer");
				action.setPriority(1);

				astConn.sendAction(action);

				/*
				 * commandAction = new CommandAction("core show channel " +
				 * extChannel); response = (CommandResponse)
				 * astConn.sendAction(commandAction); String brgChannel = "";
				 * for (String line : response.getResult()) { if
				 * (line.startsWith("BRIDGEPEER")) {
				 * 
				 * brgChannel = line.split("=")[1]; res += brgChannel; break;
				 * 
				 * 
				 * 
				 * } }
				 * 
				 * if (!brgChannel.equals("")) { RedirectAction ac = new
				 * RedirectAction(); ac.setChannel(brgChannel);
				 * ac.setExten(cell); ac.setContext("xliteToCell");
				 * ac.setPriority(1);
				 * 
				 * AtxferAction action = new AtxferAction();
				 * action.setChannel(brgChannel); action.setExten(cell);
				 * action.setContext("xliteToCell"); action.setPriority(1);
				 * 
				 * astConn.sendAction(action);
				 * 
				 * 
				 * }
				 */

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res += e.getMessage();
		}

		return res;
	}

	private String getVoiceMailCount(String[] options) {
		String res = "";
		String ext = options[0];
		String mailbox = ext + "@default";
		AstConnection astConn = new AstConnection();
		MailboxCountAction action = new MailboxCountAction(mailbox);
		try {
			MailboxCountResponse resp = (MailboxCountResponse) astConn
					.sendAction(action);

			res = "" + resp.getNewMessages() + ":" + resp.getOldMessages();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			res = e.getMessage();
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			res = e.getMessage();
			e.printStackTrace();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			res = e.getMessage();
			e.printStackTrace();
		} finally {

			astConn = null;
		}

		return res;

	}

	private String broadCastMessageInText(String[] options) {
		String ret = "OK";

		String dbname = options[0];
		String docid = options[1];
		String fname = options[2];
		String name = options[3];
		String phone = options[4];
		String prompts = options[5];
		String msg = options[6];

		String tmpTxtFile = "/tmp/" + phone + ".txt"; // the text file of the
														// prompt

		String tmpWavFile = "/tmp/" + phone; // the wav file of the prompt

		WriteToFile(tmpTxtFile, prompts); // to text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // to wav file

		String tmpMsgFile = "/tmp/Msg" + phone + ".txt"; // the text file of the
															// prompt

		String tmpMsgWavFile = "/tmp/Msg" + phone; // the wav file of the prompt

		WriteToFile(tmpMsgFile, msg); // to text file

		ConverToWav(tmpMsgWavFile + ".wav", tmpMsgFile); // to wav file

		/*
		 * msg = msg.replace(".WAV", ""); msg = "/var/spool/asterisk/monitor/" +
		 * msg; msg = msg.replace("&", "&/var/spool/asterisk/monitor/");
		 */

		String call = "Action: originate\r\n" + "Channel: " + "SIP/xo/" + phone
				+ "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n"
				+ "Variable: DBNAME=" + dbname + "\r\n" +

				"Variable: FDNAME=" + fname + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: PROMPT=" + tmpWavFile + "\r\n" +

				"Variable: CALLNAME=" + name + "\r\n" +

				"Variable: MSG=" + tmpMsgWavFile + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: BroadCastMessage\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String createLumonVoxGram2ForKey(String[] options) {

		String pp = options[0];

		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length() - 1);

		File tf = new File(ppt);

		if (!tf.exists()) {
			tf.mkdirs();
			tf.setWritable(true, false);
		}
		/*
		 * String prompt = options[1];
		 * 
		 * String fpname = pp + "p.wav";
		 * 
		 * String ftext = pp + "p.txt";
		 * 
		 * 
		 * WriteToFile(ftext, prompt); ConverToWav(fpname, ftext);
		 */

		String fname = "rk.gram";

		FileWriter resultFile = null;
		try {
			int count = Integer.parseInt(options[2]);

			resultFile = new FileWriter(tf + "/" + fname);

			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.write("#ABNF 1.0 UTF-8;\r\n");

			myFile.write("mode dtmf;\r\n");

			myFile.write("language en-US;\r\n");

			myFile.write("tag-format <semantics/1.0.2006>;\r\n");

			myFile.write("root $directive;\r\n");

			// myFile.write("$directive = ");

			String content = "";
			String direct = "$directive = ";
			for (int i = 1; i <= count; i++) {
				String vlu = options[i + 2];

				// if (i<count)
				content = content + "$R" + i + " = " + vlu + ";\r\n";
				// else
				// content = content + "(" + vlu + ") {out = \"" + vlu + "\"};";
				direct = direct + "$R" + i + "|";

			}

			if (direct.endsWith("|"))
				direct = direct.substring(0, direct.length() - 1);

			direct = direct + ";";
			// content = content + "$R" + (count+1) +
			// " = (keypad) {out = \"keypad\"};\r\n";
			// direct = direct + "$R" + (count + 1) + ";";
			myFile.write(content + direct);

		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				resultFile.close();
			} catch (Exception e) {
				return e.toString();
			}
		}

		return "OK";

	}

	private String createLumonVoxGram2(String[] options) {

		String pp = options[0];

		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length() - 1);

		File tf = new File(ppt);

		if (!tf.exists()) {
			tf.mkdirs();
			tf.setWritable(true, false);
		}

		String prompt = options[1];

		String fpname = pp + "p.wav";

		String ftext = pp + "p.txt";

		WriteToFile(ftext, prompt);
		ConverToWav(fpname, ftext);

		String fname = "r.gram";

		FileWriter resultFile = null;
		try {
			int count = Integer.parseInt(options[2]);

			resultFile = new FileWriter(tf + "/" + fname);

			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.write("#ABNF 1.0 UTF-8;\r\n");

			myFile.write("mode voice;\r\n");

			myFile.write("language en-US;\r\n");

			myFile.write("tag-format <semantics/1.0.2006>;\r\n");

			myFile.write("root $directive;\r\n");

			// myFile.write("$directive = ");

			String content = "";
			String direct = "$directive = ";
			for (int i = 1; i <= count; i++) {
				String vlu = options[i + 2];

				// if (i<count)
				content = content + "$R" + i + " = " + vlu + ";\r\n";
				// else
				// content = content + "(" + vlu + ") {out = \"" + vlu + "\"};";
				direct = direct + "$R" + i + "|";

			}

			if (direct.endsWith("|"))
				direct = direct.substring(0, direct.length() - 1);

			direct = direct + ";";
			// content = content + "$R" + (count+1) +
			// " = (keypad) {out = \"keypad\"};\r\n";
			// direct = direct + "$R" + (count + 1) + ";";
			myFile.write(content + direct);

		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				resultFile.close();
			} catch (Exception e) {
				return e.toString();
			}
		}

		return "OK";

	}

	private String createLumonVoxGram(String[] options) {

		String pp = "/opt/grammer/" + options[0];

		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length() - 1);

		File tf = new File(ppt);

		if (!tf.exists()) {
			tf.mkdirs();
			tf.setWritable(true, false);
		}

		String fname = options[1];

		FileWriter resultFile = null;
		int count = Integer.parseInt(options[2]);

		try {
			resultFile = new FileWriter(tf + "/" + fname);
			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.write("#ABNF 1.0 UTF-8;\r\n");

			myFile.write("mode voice;\r\n");

			myFile.write("language en-US;\r\n");

			myFile.write("tag-format <semantics/1.0.2006>;\r\n");

			myFile.write("root $directive;\r\n");

			// myFile.write("$directive = ");

			String content = "";
			String direct = "$directive = ";
			for (int i = 1; i <= count; i++) {
				String vlu = options[i + 2];
				String outv = vlu.replace(" ", "");

				// if (i<count)
				content = content + "$R" + i + " = (" + vlu + ") {out = \""
						+ outv + "\"};\r\n";
				// else
				// content = content + "(" + vlu + ") {out = \"" + vlu + "\"};";
				direct = direct + "$R" + i + "|";

			}
			if (direct.endsWith("|"))
				direct = direct.substring(0, direct.length() - 1);
			// content = content + "$R" + (count+1) +
			// " = (keypad) {out = \"keypad\"};\r\n";
			// direct = direct + "$R" + (count + 1) + ";";
			myFile.write(content + direct + ";");

		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				resultFile.close();
			} catch (Exception e) {
				return e.toString();
			}
		}

		return "OK";

	}

	private String createLumonVoxGramForKey(String[] options) {

		String pp = "/opt/grammer/" + options[0];

		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length() - 1);

		File tf = new File(ppt);

		if (!tf.exists()) {
			tf.mkdirs();
			tf.setWritable(true, false);
		}

		String fname = options[1];

		FileWriter resultFile = null;
		int count = Integer.parseInt(options[2]);

		try {
			resultFile = new FileWriter(tf + "/" + fname);
			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.write("#ABNF 1.0 UTF-8;\r\n");

			myFile.write("mode dtmf;\r\n");

			myFile.write("language en-US;\r\n");

			myFile.write("tag-format <semantics/1.0.2006>;\r\n");

			myFile.write("root $directive;\r\n");

			// myFile.write("$directive = ");

			String content = "";
			String direct = "$directive = ";
			for (int i = 1; i <= count; i++) {
				String valuekeys = options[i + 2];
				String vlu = "";
				String vlk = "";
				// String vlk = "";

				if (valuekeys.contains(":")) {
					String[] vk = valuekeys.split(":");
					if (vk.length > 1) {
						vlu = vk[0];
						vlk = vk[1];
					}

				}

				// String outv = vlu.replace(" ", "");

				// if (!vlk.equals(""))
				// vlu = vlk;

				// if (i<count)
				content = content + "$R" + i + " = (" + vlk + ") {out = \""
						+ vlu + "\"};\r\n";
				// else
				// content = content + "(" + vlu + ") {out = \"" + vlu + "\"};";
				direct = direct + "$R" + i + "|";

			}
			if (direct.endsWith("|"))
				direct = direct.substring(0, direct.length() - 1);
			// content = content + "$R" + (count+1) +
			// " = (keypad) {out = \"keypad\"};\r\n";
			// direct = direct + "$R" + (count + 1) + ";";
			myFile.write(content + direct + ";");

		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				resultFile.close();
			} catch (Exception e) {
				return e.toString();
			}
		}

		return "OK";

	}

	/*
	 * private void createGramFile(String fname, String type, String[] options)
	 * {
	 * 
	 * FileWriter resultFile = null; int count = Integer.parseInt(options[2]);
	 * 
	 * try{ resultFile = new FileWriter(tf + "/" + fname); PrintWriter myFile =
	 * new PrintWriter(resultFile); myFile.write("#ABNF 1.0 UTF-8;\r\n");
	 * 
	 * myFile.write("mode voice;\r\n");
	 * 
	 * myFile.write("language en-US;\r\n");
	 * 
	 * myFile.write("tag-format <semantics/1.0.2006>;\r\n");
	 * 
	 * myFile.write("root $directive;\r\n");
	 * 
	 * // myFile.write("$directive = ");
	 * 
	 * String content = ""; String direct = "$directive = "; for (int i=1;
	 * i<=count;i++) { String vlu = options[i+2];
	 * 
	 * // if (i<count) content = content + "$R" + i + " = (" + vlu +
	 * ") {out = \"" + vlu + "\"};\r\n"; // else // content = content + "(" +
	 * vlu + ") {out = \"" + vlu + "\"};"; direct = direct + "$R" + i + " | ";
	 * 
	 * } content = content + "$R" + (count+1) +
	 * " = (keypad) {out = \"keypad\"};\r\n"; direct = direct + "$R" + (count +
	 * 1) + ";"; myFile.write(content+direct);
	 * 
	 * }catch(Exception e) { return e.toString(); }finally { try{
	 * resultFile.close(); }catch(Exception e){ return e.toString(); } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	private String newdailyCallInOne(String[] opts) {
		String ret = "OK";
		String supername = opts[0];
		String calls = opts[1];
		String cell = opts[2];
		String jobtitle = opts[3];
		String codes = opts[4];
		String empdocid = opts[5];

		String chann = "SIP/xo/" + cell;

		String[] allcall = calls.split("&");

		String strcall = "";

		for (int i = 1; i <= allcall.length; i++) {

			strcall = strcall + "Variable: C" + i + "=" + allcall[i - 1]
					+ "\r\n";

		}

		String[] allcodes = codes.split("&");

		String strcodes = "";
		int numCodes = allcodes.length;

		for (int i = 1; i <= numCodes; i++) {

			strcodes = strcodes + "Variable: code" + i + "=" + allcodes[i - 1]
					+ "\r\n";

		}

		String call = "Action: originate\r\n" + "Channel: "
				+ chann
				+ "\r\n"
				+ "WaitTime: 60\r\n"
				+ "CallerId: 2123171423"
				+ "\r\n"
				+

				// "Variable: ALLCALL=" + calls + "\r\n" +

				strcall + strcodes +

				"Variable: EMPNAME=" + supername + "\r\n"
				+ "Variable: JOBTITLE=" + jobtitle + "\r\n"
				+ "Variable: EMPDOCID=" + empdocid + "\r\n"
				+ "Variable: codenum=" + numCodes + "\r\n"
				+ "Variable: RSLT=Found" + "\r\n" + "Variable: SUPERCELL="
				+ cell + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: newdailycallinone\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String broadCastMessage(String[] options) {
		String ret = "OK";

		String dbname = options[0];
		String docid = options[1];
		String fname = options[2];
		String name = options[3];
		String phone = options[4];
		String prompts = options[5];
		String msg = options[6];

		String tmpTxtFile = "/tmp/" + phone + ".txt"; // the text file of the
														// prompt

		String tmpWavFile = "/tmp/" + phone; // the wav file of the prompt

		WriteToFile(tmpTxtFile, prompts); // to text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // to wav file

		msg = msg.replace(".WAV", "");
		msg = "/var/spool/asterisk/" + msg;
		msg = msg.replace("&", "&/var/spool/asterisk/");

		String call = "Action: originate\r\n" + "Channel: " + "SIP/xo/" + phone
				+ "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n"
				+ "Variable: DBNAME=" + dbname + "\r\n" +

				"Variable: FDNAME=" + fname + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: PROMPT=" + tmpWavFile + "\r\n" +

				"Variable: CALLNAME=" + name + "\r\n" +

				"Variable: MSG=" + msg + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: BroadCastMessage\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String makeCallAndCommentByExt(String[] options) {
		String ret = "OK";

		String dbname = options[0];
		String docid = options[1];
		String fname = options[2];
		String phone = options[3];
		String prompts = options[4];

		String tmpTxtFile = "/tmp/" + phone + ".txt"; // the text file of the
														// prompt

		String tmpWavFile = "/tmp/" + phone; // the wav file of the prompt

		WriteToFile(tmpTxtFile, prompts); // to text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // to wav file

		String call = "Action: originate\r\n" + "Channel: " + "SIP/" + phone
				+ "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n"
				+ "Variable: DBNAME=" + dbname + "\r\n" +

				"Variable: FDNAME=" + fname + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: PROMPT=" + tmpWavFile + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: LeaveComments\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String makeCallAndComment(String[] options) {
		String ret = "OK";

		String dbname = options[0];
		String docid = options[1];
		String fname = options[2];
		String phone = options[3];
		String prompts = options[4];

		String tmpTxtFile = "/tmp/" + phone + ".txt"; // the text file of the
														// prompt

		String tmpWavFile = "/tmp/" + phone; // the wav file of the prompt

		WriteToFile(tmpTxtFile, prompts); // to text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // to wav file

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ phone + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Variable: DBNAME=" + dbname + "\r\n" +

				"Variable: FDNAME=" + fname + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: PROMPT=" + tmpWavFile + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: LeaveComments\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String makeCallAndRecord(String[] opts) {
		String ret = "OK";
		String dbname = opts[0];
		String docid = opts[1];
		String fdname = opts[2];
		String caller = opts[3];
		String callee = opts[4];
		// String filewav = opts[3];

		String chann = "SIP/xo/" + caller;

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

				"Variable: DBNAME=" + dbname + "\r\n" + "Variable: FDNAME="
				+ fdname + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: CALLEE=" + callee + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: makeCallAndRecord\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String connectSuperTen(String[] opts) {
		String ret = "OK";
		String docid = opts[0];
		String supercell = opts[1];
		String tencell = opts[2];
		// String filewav = opts[3];

		String chann = "SIP/xo/" + supercell;

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: TENCELL=" + tencell + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: superCallTenComp\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String googleAsr(String[] opts) {
		String ret = "OK";
		String db = opts[0];
		String docid = opts[1];
		String fd = opts[2];
		String filewav = opts[3];

		String chann = "LOCAL/s@playBackRecord";

		String fname = "/var/spool/asterisk/monitor/" + filewav;

		File f = new File(fname + ".WAV");
		if (!f.exists())
			fname = "/var/spool/asterisk/lastmonth/" + filewav;

		String call = "Action: originate\r\n"
				+ "Channel: "
				+ chann
				+ "\r\n"
				+ "WaitTime: 60\r\n"
				+
				// "CallerId: 2123171423" + "\r\n" +

				// "Variable: ALLCALL=" + calls + "\r\n" +

				"Variable: DB=" + db + "\r\n" + "Variable: DOCID=" + docid
				+ "\r\n" + "Variable: FD=" + fd + "\r\n"
				+ "Variable: recording=" + fname + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: googleasr\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String dailyCallInOne(String[] opts) {
		String ret = "OK";
		String supername = opts[0];
		String calls = opts[1];
		String cell = opts[2];

		String chann = "SIP/xo/" + cell;

		String[] allcall = calls.split("&");

		String strcall = "";

		for (int i = 1; i <= allcall.length; i++) {

			strcall = strcall + "Variable: C" + i + "=" + allcall[i - 1]
					+ "\r\n";

		}

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n"
				+

				// "Variable: ALLCALL=" + calls + "\r\n" +

				strcall +

				"Variable: SUPER=" + supername + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: dailycallinone\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String callProspectByExt(String[] opts) {
		String ret = "OK";
		String prosphone = opts[0];
		String agphone = opts[1];
		String docid = opts[2];
		String bldg = opts[3];
		String aptNo = opts[4];
		String unType = opts[5];

		String moreInfo = "";

		if (!bldg.equals("")) {
			moreInfo = "Variable: BLDG=" + bldg + "\r\n" + "Variable: APTNO="
					+ aptNo + "\r\n" + "Variable: UITYPE=" + unType + "\r\n";

		}

		String chann = "SIP/" + agphone;
		if (agphone.equals(""))
			chann = "SIP/625";

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

				"Variable: PROSPHONE=" + prosphone + "\r\n"
				+ "Variable: agphone=" + agphone + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				moreInfo +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: callprospect\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String callProspect(String[] opts) {
		String ret = "OK";
		String prosphone = opts[0];
		String agphone = opts[1];
		String docid = opts[2];
		String bldg = opts[3];
		String aptNo = opts[4];
		String unType = opts[5];

		String moreInfo = "";

		if (!bldg.equals("")) {
			moreInfo = "Variable: BLDG=" + bldg + "\r\n" + "Variable: APTNO="
					+ aptNo + "\r\n" + "Variable: UITYPE=" + unType + "\r\n";

		}

		String chann = "SIP/xo/" + agphone;
		if (agphone.equals(""))
			chann = "SIP/625";

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

				"Variable: PROSPHONE=" + prosphone + "\r\n"
				+ "Variable: agphone=" + agphone + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				moreInfo +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: callprospect\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String callProspectByExtV2(String[] opts) {
		String ret = "OK";
		String prosphone = opts[0];
		String agphone = opts[1];
		String docid = opts[2];
		String bldg = opts[3];
		String aptNo = opts[4];
		String unType = opts[5];
		String obnames = opts[6];

		String moreInfo = "";// "Variable: BLDG=test" + bldg + "\r\n";

		if (!bldg.equals("")) {
			moreInfo = "Variable: BLDG=" + bldg + "\r\n" + "Variable: APTNO="
					+ aptNo + "\r\n" + "Variable: UITYPE=" + unType + "\r\n";

		}

		if (!obnames.equals("")) {

			obnames = "/var/spool/asterisk/monitor/" + obnames;
			obnames = obnames.replace("&", "&/var/spool/asterisk/monitor/");

			obnames = obnames.replace(".WAV", "");
			obnames = obnames.replace(".amr", "");
			obnames = obnames.replace(".wav", "");

			moreInfo = moreInfo + "Variable: MSG=" + obnames + "\r\n";

		}

		String chann = "SIP/" + agphone;
		if (agphone.equals(""))
			chann = "SIP/625";

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

				"Variable: PROSPHONE=" + prosphone + "\r\n"
				+ "Variable: agphone=" + agphone + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				moreInfo +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: callprospectv2\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String callProspectV2(String[] opts) {
		String ret = "OK";
		String prosphone = opts[0];
		String agphone = opts[1];
		String docid = opts[2];
		String bldg = opts[3];
		String aptNo = opts[4];
		String unType = opts[5];
		String obnames = opts[6];
		String moreInfo = "";

		// /String moreInfo = "Variable: BLDG=test" + bldg + "\r\n";
		if (!bldg.equals("")) {
			moreInfo = "Variable: BLDG=" + bldg + "\r\n" + "Variable: APTNO="
					+ aptNo + "\r\n" + "Variable: UITYPE=" + unType + "\r\n";

		}

		if (!obnames.equals("")) {

			obnames = "/var/spool/asterisk/monitor/" + obnames;
			obnames = obnames.replace("&", "&/var/spool/asterisk/monitor/");

			obnames = obnames.replace(".WAV", "");
			obnames = obnames.replace(".amr", "");
			obnames = obnames.replace(".wav", "");

			moreInfo = moreInfo + "Variable: MSG=" + obnames + "\r\n";

		}

		String chann = "SIP/xo/" + agphone;
		if (agphone.equals(""))
			chann = "SIP/625";

		String call = "Action: originate\r\n" + "Channel: " + chann + "\r\n"
				+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

				"Variable: PROSPHONE=" + prosphone + "\r\n"
				+ "Variable: agphone=" + agphone + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				moreInfo +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: callprospectv2\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private void makeDir(String path) {

		StringTokenizer st = new StringTokenizer(path, "/");
		String path1 = st.nextToken() + "/";
		String path2 = path1;
		while (st.hasMoreTokens()) {
			path1 = st.nextToken() + "/";
			path2 += path1;
			File inbox = new File(path2);
			if (!inbox.exists()) {
				inbox.mkdir();
				inbox.setWritable(true, false);
			}

		}

		// File f = new File(path);
		// f.setWritable(true, false);
	}
	
	//makeCallSpanishPrompts

	
	private String makeCallSpanishPrompts(String[] options) {

		String pp = "/var/lib/asterisk/sounds/" + options[0];

		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length() - 1);

		File tf = new File(ppt);

		if (!tf.exists()) {
			tf.mkdirs();
			tf.setWritable(true, false);
		}

		// makeDir(ppt);

		int count = Integer.parseInt(options[1]);

		String tmpTxtFile = "/tmp/astPrompt.txt";
		//Translator trans = new Translator();

		for (int i = 1; i <= count; i++) {
			String vlu = options[i + 1];

		/*	if (vlu.startsWith("{|SPANISH|}")) {
				
				try {
			//	vlu = trans.EnglishToSpanish(vlu.substring("{|SPANISH|}".length()));
				}catch(Exception e)
				{
					vlu = "";
				}
				
			} */
			WriteToFile(tmpTxtFile, vlu);
			ConverToWavSp(pp + "sp" + i + ".wav", tmpTxtFile); // convert it to wav file

		}

		return "OK";

	}
	
	
	
	private String makeCallPrompts(String[] options) {

		String pp = "/var/lib/asterisk/sounds/" + options[0];

		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length() - 1);

		File tf = new File(ppt);

		if (!tf.exists()) {
			tf.mkdirs();
			tf.setWritable(true, false);
		}

		// makeDir(ppt);

		int count = Integer.parseInt(options[1]);

		String tmpTxtFile = "/tmp/astPrompt.txt";

		for (int i = 1; i <= count; i++) {
			String vlu = options[i + 1];

			WriteToFile(tmpTxtFile, vlu);
			ConverToWav(pp + i + ".wav", tmpTxtFile); // convert it to wav file

		}

		return "OK";

	}

	private String callTenForEmail(String[] opts) {
		String ret = "OK";
		String tenName = opts[0];
		String bldg = opts[1];
		String aptno = opts[2];
		String ldate = opts[3];
		String docid = opts[4];
		String tencell = opts[5];

		String call = "Action: originate\r\n" + "Channel: SIP" + "/xo/"
				+ tencell + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" +

				"Variable: TENANT=" + tenName + "\r\n" + "Variable: ADDRESS="
				+ bldg + " unit " + aptno + "\r\n" +

				"Variable: LDATE=" + ldate + "\r\n" + "Variable: DOCID="
				+ docid + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: calltenforemail\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String callSuperAfterTenComp(String[] opts) {
		String ret = "OK";
		String superName = opts[0];
		String bldg = opts[1];
		String aptno = opts[2];
		String fname = opts[3];
		String docid = opts[4];
		String supercell = opts[5];

		fname = fname.replace(".WAV", "");
		fname = "/var/spool/asterisk/monitor/" + fname;

		String call = "Action: originate\r\n" + "Channel: SIP" + "/xo/"
				+ supercell + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" +

				"Variable: SUPER=" + superName + "\r\n" + "Variable: BLDG="
				+ bldg + "\r\n" + "Variable: APTNO=" + aptno + "\r\n"
				+ "Variable: BLDG=" + bldg + "\r\n" + "Variable: TENCOMP="
				+ fname + "\r\n" + "Variable: COMPDOC=" + docid + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: aftertenantcomp\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String saveCallScreening(String[] opts) {
		String ret = "OK";

		String db = opts[0];
		String doc = opts[1];
		String fd = opts[2];
		String phone = opts[3];

		String cmd = "/var/javalib/fileconvert.sh";

		String fp = "/var/lib/asterisk/sounds/priv-callerintros/";

		String fname1 = fp + phone + ".sln";
		String fname2 = fp + phone + ".WAV";

		File f1 = new File(fname1);

		if (f1.exists()) {

			String[] cmds = new String[3];
			cmds[0] = cmd;// the shell command to convert the text into wav file
			cmds[1] = fname1; // the src file
			cmds[2] = fname2; // the dst file

			runshellcmds(cmds);

			File f2 = new File(fname2);

			if (f2.exists()) {

				String[] options = new String[4];
				options[0] = db;
				options[1] = doc;
				options[2] = fd;
				options[3] = phone + ".WAV";

				byte[] data = null;

				// ******************************
				long fsize = f2.length();
				data = new byte[(int) fsize];

				try {
					FileInputStream fin = new FileInputStream(f2);

					fin.read(data);

					fin.close();

				} catch (Exception e) {

				}

				String cmd2 = "SAVECALLSCREEN";

				String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";

				NotesWSClient wsclient = new NotesWSClient(url);

				String[] res = wsclient.generalCommand(cmd2, options, data);

			}

		}

		return ret;

	}

	private String listenToDaily(String[] opts) {
		String ret = "OK";
		String empName = opts[0];
		String empExt = opts[1];
		String typeDaily = opts[2];

		String context = "lisnAllRepOfDaily";
		if (typeDaily.equals("ALL"))
			context = "lisnAllRepOfDaily";

		if (typeDaily.equals("DAILY"))
			context = "lisnRepOfDaily";

		if (typeDaily.equals("RFP"))
			context = "lisnRfpRepOfDaily";

		if (typeDaily.equals("VACANCY"))
			context = "lisnVacRepOfDaily";

		if (typeDaily.equals("SERVICE"))
			context = "lisnScalRepOfDaily";

		if (typeDaily.equals("HPD"))
			context = "lisnHpdRepOfDaily";

		String call = "Action: originate\r\n" + "Channel: LOCAL" + "/" + empExt
				+ "@ivr-2\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" +

				"Variable: EMPNAME=" + empName + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n" + "Context: " + context
				+ "\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;

	}

	private String deleIncomingForward(String[] opts) {
		String ret = "OK";
		String docid = opts[0];
		delExtFoward(docid, "/etc/asterisk/extensions_custom_forward.conf");

		String cmds[] = new String[1];
		cmds[0] = "/var/javalib/dialreload";

		runshellcmds(cmds);

		return ret;

	}

	private String procIncomingForward(String[] opts) {
		String ret = "OK";

		String ext = opts[1];
		String callerid = opts[2];
		String dstext = opts[3];
		String docid = opts[0];

		delExtFoward(docid, "/etc/asterisk/extensions_custom_forward.conf");
		appExtFoward(docid, ext, callerid, dstext);

		String cmds[] = new String[1];
		cmds[0] = "/var/javalib/dialreload";

		runshellcmds(cmds);

		return ret;
	}

	private void appExtFoward(String docid, String ext, String callerid,
			String dstext) {
		String fileName = "/etc/asterisk/extensions_custom_forward.conf";

		String s = ";start " + docid;
		String e = ";end " + docid;
		String sep = System.getProperty("line.separator");

		String content = s + sep + "exten => " + ext + "/" + callerid
				+ ",1,Goto(from-did-direct," + dstext + ",1)" + sep

				+ e + sep;

		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void delExtFoward(String docid, String filename) {
		deleteConf(docid, filename);
	}

	private String HPDDailyCallSuper(String[] opts) {
		String ret = "OK";

		String docid = opts[0];
		String bldg = opts[1];
		String OCount = opts[2];
		String WSCount = opts[3];
		String supername = opts[4];
		String supercell = opts[5];

		String call = "Action: originate\r\n" + "Channel: SIP/xo/" + supercell
				+ "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n"
				+

				"Variable: DOCID=" + docid + "\r\n" + "Variable: BLDG=" + bldg
				+ "\r\n" + "Variable: OCOUNT=" + OCount + "\r\n" +

				"Variable: WSCOUNT=" + WSCount + "\r\n"
				+ "Variable: SUPERNAME=" + supername + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: HPDDAILYCALLSUPER\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);
		return ret;
	}

	private String discBoardWebClickCall(String[] options) {
		String ret = "OK";

		String type = options[0];
		String local = options[1];
		String remote = options[2];

		// ******************************
		String localnum = local;
		String remotenum = remote;
		String localext = "";
		String remoteext = "";

		if (local.contains("-")) {
			String[] res = local.split("-");
			localnum = res[0];
			localext = res[1];

		}

		if (remote.contains("-")) {
			String[] res = remote.split("-");
			remotenum = res[0];
			remoteext = res[1];
		}

		String srcdtmf = "";
		String dstdtmf = "";

		String srcvardtmf = "";
		String dstvardtmf = "";

		if (!localext.equals("")) {

			for (int i = 0; i < localext.length(); i++) {

				srcdtmf = srcdtmf + "ww" + localext.charAt(i); // set the dtmf
																// keys

				// TSleep(1000);
			}
			srcvardtmf = ",__SRCDTMFKEYS=" + srcdtmf;

		}

		if (!remoteext.equals("")) {

			for (int i = 0; i < remoteext.length(); i++) {

				dstdtmf = dstdtmf + "ww" + remoteext.charAt(i); // set the dtmf
																// keys

				// TSleep(1000);
			}
			dstvardtmf = ",__DSTDTMFKEYS=" + dstdtmf;

		}

		// **********************************
		String localname = options[3];
		String remotename = options[4];

		String db = options[5];
		String doc = options[6];

		String dt = options[7];

		String call = "";

		if (type.equals("inout") || type.equals("outout")) {

			String localchann = "SIP/" + localnum;

			if (!type.contains("in"))
				localchann = "SIP/xo/" + localnum;

			call = "Action: originate\r\n" + "Channel: " + localchann + "\r\n"
					+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

					"Variable: callid=" + local + "\r\n" + "Variable: local="
					+ local + "\r\n" + "Variable: remote=" + remote + "\r\n" +

					"Variable: localname=" + localname + "\r\n"
					+ "Variable: remotename=" + remotename + "\r\n" +

					"Variable: db=" + db + "\r\n" + "Variable: doc=" + doc
					+ "\r\n" + "Variable: type=" + type + "\r\n" +

					"Variable: dt=" + dt + srcvardtmf + dstvardtmf + "\r\n" +

					"Exten: " + remotenum + "\r\n" + "Async: true\r\n"
					+ "Context: DiscBoardCallOut\r\n" + "Priority: 1\r\n\r\n";
		}

		if (type.equals("outin") || type.equals("inin")) {
			String localchann = "SIP/" + localnum;

			if (type.contains("out"))
				localchann = "SIP/xo/" + localnum;

			call = "Action: originate\r\n" + "Channel: " + localchann + "\r\n"
					+ "WaitTime: 60\r\n" + "CallerId: 2123171423" + "\r\n" +

					"Variable: callid=" + local + "\r\n" + "Variable: local="
					+ local + "\r\n" + "Variable: remote=" + remote + "\r\n" +

					"Variable: localname=" + localname + "\r\n"
					+ "Variable: remotename=" + remotename + "\r\n" +

					"Variable: db=" + db + "\r\n" + "Variable: doc=" + doc
					+ "\r\n" +

					"Variable: type=" + type + "\r\n" +

					"Variable: dt=" + dt + srcvardtmf + dstvardtmf + "\r\n" +

					"Exten: " + remotenum + "\r\n" + "Async: true\r\n"
					+ "Context: DiscBoardCallIn\r\n" + "Priority: 1\r\n\r\n";
		}

		/*
		 * if (type.equals("outout")) { String[] opts = new String[4]; opts[0] =
		 * local; opts[1] = remote; opts[2] = db; opts[3] = doc;
		 * 
		 * runWebCallExtExt(opts); /* call = "Action: originate\r\n" +
		 * "Channel: " + "SIP" + "/xo/" + local + "\r\n" + "WaitTime: 60\r\n" +
		 * "CallerId: 2123171423" + "\r\n" +
		 * 
		 * 
		 * "Variable: callid=" + local + "\r\n" + // "Variable: PUNDOCID=" +
		 * docid + "\r\n" + // "Variable: NAME=" + name + "\r\n" +
		 * 
		 * 
		 * "Exten: " + remote + "\r\n" + "Async: true\r\n" +
		 * "Context: from-internal\r\n" + "Priority: 1\r\n\r\n" ;
		 */
		// }
		// */

		makecalls(call);

		return ret;
	}

	private String webClickToCallByType(String[] options) {
		String ret = "OK";

		String type = options[0];
		String local = options[1];
		String remote = options[2];
		String db = options[3];
		String doc = options[4];
		String name = options[5];
		String phonetype = options[6];

		String call = "";

		if (type.equals("inin") || type.equals("inout")) {

			call = "Action: originate\r\n" + "Channel: " + /*"SIP/" + local */ "Local" + "/" + local + "@webclicktocalllocal/nj" 
					+ "\r\n" + "WaitTime: 60\r\n" + "CallerId: " + local
					+ "\r\n" +

					"Variable: callid=" + local
					+ "\r\n"
					+ "Variable: remote=" + remote
					+ "\r\n"
					+ "Variable: remotename=" + name
					+ "\r\n"
					
					+ "Variable: phonetype=" + phonetype
					+ "\r\n"
					
				//	+ "Variable: CALLERID(name)=" + "CLICKTO " + name
				//	+ "\r\n"
					+
					// "Variable: PUNDOCID=" + docid + "\r\n" +
					// "Variable: NAME=" + name + "\r\n" +

					"Exten: " + remote + "\r\n" + "Async: true\r\n"
					+ "Context: webclicktocallfromin\r\n" + "Priority: 1\r\n\r\n";
			
			makecalls(call);
		}

		if (type.equals("outin")) {

			String[] opts = new String[4];
			opts[0] = local;
			opts[1] = remote;
			opts[2] = db;
			opts[3] = doc;

			runWebCallExtInt(opts);

			/*
			 * call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/" +
			 * local + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423" +
			 * "\r\n" +
			 * 
			 * 
			 * "Variable: callid=" + local + "\r\n" + // "Variable: PUNDOCID=" +
			 * docid + "\r\n" + // "Variable: NAME=" + name + "\r\n" +
			 * 
			 * 
			 * "Exten: " + remote + "\r\n" + "Async: true\r\n" +
			 * "Context: webcalltrunk-incoming\r\n" + "Priority: 1\r\n\r\n" ;
			 */
		}

		if (type.equals("outout")) {
			String[] opts = new String[4];
			opts[0] = local;
			opts[1] = remote;
			opts[2] = db;
			opts[3] = doc;

			runWebCallExtExt(opts);
			/*
			 * call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/" +
			 * local + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423" +
			 * "\r\n" +
			 * 
			 * 
			 * "Variable: callid=" + local + "\r\n" + // "Variable: PUNDOCID=" +
			 * docid + "\r\n" + // "Variable: NAME=" + name + "\r\n" +
			 * 
			 * 
			 * "Exten: " + remote + "\r\n" + "Async: true\r\n" +
			 * "Context: from-internal\r\n" + "Priority: 1\r\n\r\n" ;
			 */
		}

		

		return ret;
	}

	private String phoneconfbycell(String[] options) {
		String ret = "OK";

		String ext = options[0];
		String conf = options[1];
		String pin = options[2];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ ext + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" +

				"Variable: callid=" + ext + "\r\n" + "Variable: PIN=" + pin
				+ "\r\n"
				+
				// "Variable: NAME=" + name + "\r\n" +

				"Exten: " + conf + "\r\n" + "Async: true\r\n"
				+ "Context: ext-meetme\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;
	}

	private String phoneconfbyxlite(String[] options) {
		String ret = "OK";

		String ext = options[0];
		String conf = options[1];
		String pin = options[2];

		String call = "Action: originate\r\n" + "Channel: " + "LOCAL" + "/"
				+ ext + "@ivr-2/n\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" +

				"Variable: callid=" + ext + "\r\n" + "Variable: PIN=" + pin
				+ "\r\n"
				+
				// "Variable: NAME=" + name + "\r\n" +

				"Exten: " + conf + "\r\n" + "Async: true\r\n"
				+ "Context: ext-meetme\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;
	}

	private String deleeteconference(String[] options) {
		String ret = "OK";

		String docid = options[0];

		deleteConf(docid, "/etc/asterisk/meetme_notes.conf");
		deleteConf(docid, "/etc/asterisk/extensions_notes_meetme.conf");

		String cmds[] = new String[1];
		cmds[0] = "/var/javalib/dialreload";

		runshellcmds(cmds);

		return ret;
	}

	private String phoneconference(String[] options) {
		String ret = "OK";

		String docid = options[0];
		String confnum = options[1];
		String confprompt = options[2];

		String confpass = options[3];

		// **************************prompt*************************
		String tmpTxtFile = "/tmp/conf" + confnum + ".txt";
		WriteToFile(tmpTxtFile, confprompt);
		ConverToWav("/var/lib/asterisk/sounds/custom/conf" + confnum
				+ "pro.wav", tmpTxtFile);

		// **************************************************

		writeinconffile(docid, confnum, confpass);

		String cmds[] = new String[1];
		cmds[0] = "/var/javalib/dialreload";

		runshellcmds(cmds);

		return ret;
	}

	private String writeinconffile(String docid, String confnum, String confpass) {
		String ret = "OK";

		deleteConf(docid, "/etc/asterisk/meetme_notes.conf");
		appendconf(docid, confnum, confpass);

		deleteConf(docid, "/etc/asterisk/extensions_notes_meetme.conf");
		appendconfext(docid, confnum, confpass);

		return ret;
	}

	private void appendconfext(String docid, String confnum, String confpass) {
		String fileName = "/etc/asterisk/extensions_notes_meetme.conf";

		String s = ";start " + docid;
		String e = ";end " + docid;
		String sep = System.getProperty("line.separator");

		String content = s
				+ sep
				+ "exten => "
				+ confnum
				+ ",1,Macro(user-callerid,)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,ExecIf($[\"${callid}\" != \"\"],Set,CALLERID(num)=${callid})"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Playback(custom/conf"
				+ confnum
				+ "pro)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Set(MEETME_ROOMNUM="
				+ confnum
				+ ")"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Set(MEETME_RECORDINGFORMAT=WAV)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Set(MEETME_RECORDINGFILE=${ASTSPOOLDIR}/monitor/conf${MEETME_ROOMNUM}-${UNIQUEID})"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Goto(USER)"
				+ sep
				// + "exten => " + confnum + ",n,GotoIf($[\"x${PIN}\" = \"x" +
				// confpass + "\"]?USER)" + sep
				+ "exten => "
				+ confnum
				+ ",n,GotoIf($[\"${DIALSTATUS}\" = \"ANSWER\"]?READPIN)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Answer"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Wait(1)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Set(PINCOUNT=0)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n(READPIN),Read(PIN,enter-conf-pin-number,,,,)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,GotoIf($[\"x${PIN}\" = \"x"
				+ confpass
				+ "\"]?USER)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Set(PINCOUNT=$[${PINCOUNT}+1])"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,GotoIf($[${PINCOUNT}>3]?h)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Playback(conf-invalidpin)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,Goto(READPIN)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n(USER),Set(MEETME_OPTS=Ir)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,MeetMeCount("
				+ confnum
				+ ",cnt)"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,ExecIf($[${cnt} = 0],UserEvent,NEWSESS,Conf: "
				+ confnum
				+ "@${CHANNEL})"
				+ sep
				+ "exten => "
				+ confnum
				+ ",n,ExecIf($[${cnt} = 0],MixMonitor,${MEETME_RECORDINGFILE}.WAV)"
				+ sep + "exten => " + confnum
				+ ",n,ExecIf($[${cnt} = 0],Set,MEETME_OPTS=${MEETME_OPTS})"
				+ sep + "exten => " + confnum + ",n,Goto(STARTMEETME,1)" + sep
				+ e + sep;

		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void appendconf(String docid, String confnum, String confpass) {
		String fileName = "/etc/asterisk/meetme_notes.conf";

		String s = ";start " + docid;
		String e = ";end " + docid;
		String sep = System.getProperty("line.separator");

		String content = s + System.getProperty("line.separator") + "conf => "
				+ confnum + "," + confpass + sep + e + sep;

		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void deleteConf(String docid, String filename) {
		String temp = "";

		String s = ";start " + docid;
		String e = ";end " + docid;

		try {
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该行前面的内容
			for (int j = 1; (temp = br.readLine()) != null && !temp.equals(s); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}
			// 忽略中间内容
			for (int j = 1; (temp = br.readLine()) != null && !temp.equals(e); j++) {

			}

			// 保存该行后面的内容
			while ((temp = br.readLine()) != null) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private String reportLeaveOffByCell(String[] options) {
		String ret = "OK";

		String cell = options[0];
		String docid = options[1];
		String name = options[2];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ cell + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" +

				"Variable: callid=" + cell + "\r\n" + "Variable: PUNDOCID="
				+ docid + "\r\n" + "Variable: NAME=" + name + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: reportforleaveoff\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;
	}

	private void rfpPrompt(String[] options) {

		String tmpTxtFile = "/tmp/rfp.txt";

		for (int i = 0; i < options.length; i++) {
			String fldValue = options[i];

			if (fldValue == null || fldValue.equals(""))
				continue;

			String[] keyvalue = fldValue.split("::");

			String fld = keyvalue[0];
			String vlu = "";

			if (keyvalue.length > 1) {
				vlu = keyvalue[1];
			}

			WriteToFile(tmpTxtFile, vlu);
			ConverToWav("/var/lib/asterisk/sounds/custom/rfp" + fld + ".wav",
					tmpTxtFile); // convert it to wav file

		}

	}

	private void vendorConfPrompt(String[] options) {

		String tmpTxtFile = "/tmp/vdcf.txt";

		for (int i = 0; i < options.length; i++) {
			String fldValue = options[i];

			if (fldValue == null || fldValue.equals(""))
				continue;

			String[] keyvalue = fldValue.split("::");

			String fld = keyvalue[0];
			String vlu = "";

			if (keyvalue.length > 1) {
				vlu = keyvalue[1];
			}

			WriteToFile(tmpTxtFile, vlu);
			ConverToWav("/var/lib/asterisk/sounds/custom/vdcf" + fld + ".wav",
					tmpTxtFile); // convert it to wav file

		}

	}

	private void superCallPrompt(String[] options) {

		String tmpTxtFile = "/tmp/sup.txt";

		for (int i = 0; i < options.length; i++) {
			String fldValue = options[i];

			if (fldValue == null || fldValue.equals(""))
				continue;

			String[] keyvalue = fldValue.split("::");

			String fld = keyvalue[0];
			String vlu = "";

			if (keyvalue.length > 1) {
				vlu = keyvalue[1];
			}

			WriteToFile(tmpTxtFile, vlu);
			ConverToWav("/var/lib/asterisk/sounds/custom/sup" + fld + ".wav",
					tmpTxtFile); // convert it to wav file

		}

	}

	private void serviceCallPrompt(String[] options) {

		String tmpTxtFile = "/tmp/scp.txt";

		for (int i = 0; i < options.length; i++) {
			String fldValue = options[i];
			String[] keyvalue = fldValue.split("::");

			String fld = keyvalue[0];
			String vlu = "";

			if (keyvalue.length > 1) {
				vlu = keyvalue[1];
			}

			WriteToFile(tmpTxtFile, vlu);
			ConverToWav("/var/lib/asterisk/sounds/custom/scp" + fld + ".wav",
					tmpTxtFile); // convert it to wav file

		}

	}

	private String cellPunchCallBack(String[] options) {
		String ret = "OK";

		String cell = options[0];
		String gpsDoc = options[1];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ cell + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" +

				"Variable: callid=" + cell + "\r\n" + "Variable: GPSDOCID="
				+ gpsDoc + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: cellpunchcallback\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;
	}

	private String leaveCommentsForPic(String[] options) {
		String ret = "OK";

		String bldg = options[0];
		String apt = options[1];
		String docid = options[2];
		String phone = options[3];
		String caller = options[4];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ phone + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Variable: BlgAddr=" + bldg + "\r\n" +

				"Variable: callid=" + phone + "\r\n" +

				"Variable: Apt=" + apt + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: REPNAME=" + caller + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: LeaveCommtsForPic\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;
	}

	private String getOilShipDate(String[] options) {
		String ret = "OK";

		String docid = options[0];
		String code = options[1];
		String bldg = options[2];
		String vendor = options[3];
		String amount = options[4];
		String oiltype = options[5];
		String supercell = options[6];
		String supername = options[7];
		String PONum = options[8];
		String oilcell = options[9];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ supercell + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Variable: BlgAddr=" + bldg
				+ "\r\n" + "Variable: REPNAME=" + supername + "\r\n" +

				"Variable: callid=" + supercell + "\r\n" +

				"Variable: BlgCode=" + code + "\r\n" +

				"Variable: OilType=" + oiltype + "\r\n" +

				"Variable: DOCID=" + docid + "\r\n" +

				"Variable: amount=" + amount + "\r\n" +

				"Variable: vendor=" + vendor + "\r\n" +

				"Variable: PONum=" + PONum + "\r\n" +

				"Variable: oilcomphone=" + oilcell + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: oilshippingdate\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call);

		return ret;
	}

	private String test(String[] options) {
		String ret = "OK";
		try {
			// String sqlstr =
			// "select UniID, CallingType, Phone from callingrecord where EmpExt='652' order by UniID";

			Connection c = DbConnection.getConnection();
			// Statement stat = c.createStatement(); // get the sql statement
			// ResultSet result = stat.executeQuery(sqlstr);
			// if (result.next())
			// {
			// ret = result.getString("UniID");
			// }
		} catch (Exception e) {
			ret = "Exception";
		}

		return ret;
	}

	private String getCallingRecord(String[] options) {
		String ret = "OK";

		String ext = options[0];

		Connection con = null;

		// sql for mysql database
		String sqlstr = "select UniID, CallingType, Phone from callingrecord where EmpExt=\'"
				+ ext + "\' order by UniID";

		try {
			con = DbConnection.getConnection(); // get the mysql connection from
												// the database connection spool
			Statement stat = con.createStatement(); // get the sql statement
			ResultSet result = stat.executeQuery(sqlstr); // execute the sql

			if (result.next()) {

				ret = result.getString("CallingType") + ":"
						+ result.getString("Phone"); // get the extension status
				String uid = result.getString("UniID");

				String sqldel = "delete from callingrecord where UniID=\'"
						+ uid + "\'";

				DbConnection.sqlExecute(sqldel);

			}

		} catch (Exception e) {
			ret = e.toString();
			// System.out.println(e);
		} finally {
			try {
				DbConnection.FreeCon(con); // free the mysql connection from the
											// spool
			} catch (Exception e) {
				ret = e.toString();
			}

		}

		return ret;
	}

	private String superVACDailyCall(String[] options) {
		String ret = "OK";

		String code = options[0];
		String blgaddr = options[1];
		String supername = options[2];
		String supercell = options[3];
		String unitnum = options[4];
		String units = options[5];
		String sgrents = options[6];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ supercell + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Variable: BlgAddr="
				+ blgaddr + "\r\n" + "Variable: REPNAME=" + supername + "\r\n" +

				"Variable: callid=" + supercell + "\r\n" +

				"Variable: BlgCode=" + code + "\r\n" +

				"Variable: vacnum=" + unitnum + "\r\n" +

				"Variable: units=" + units + "\r\n" +

				"Variable: sgrents=" + sgrents + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: callforvacancy\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return ret;
	}

	private String superRFPDailyCall(String[] options) {
		String ret = "OK";

		String docid = options[0];
		String supername = options[1];
		String supercell = options[2];
		String jtitle = options[3];
		String blgaddr = options[4];
		String vendor = options[5];
		String ponum = options[6];
		String code = options[7];
		String sext = options[8];
		String ifnodate = options[9];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ supercell + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Variable: BlgAddr="
				+ blgaddr + "\r\n" + "Variable: DOCID=" + docid + "\r\n"
				+ "Variable: SUPER=" + supername + "\r\n" + "Variable: PONUM="
				+ ponum + "\r\n" + "Variable: JTITLE=" + jtitle + "\r\n"
				+ "Variable: sext=" + sext + "\r\n" + "Variable: callid="
				+ supercell + "\r\n" +

				"Variable: nodate=" + ifnodate + "\r\n" +

				"Exten: s" + "\r\n" + "Async: true\r\n"
				+ "Context: superRFPDailyCall\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return ret;
	}

	private String voicemailbycell(String[] options) {
		String ret = "OK";

		String ext = options[0];
		String cell = options[1];

		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ cell + "\r\n" + "WaitTime: 60\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: " + ext + "\r\n" + "Async: true\r\n"
				+ "Context: empvoicemail\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return ret;
	}

	private void reminderToPunchIn(String[] options) {
		String empDoc = options[0];
		String empName = options[1];
		String empCell = options[2];
		String prompt = options[3];

		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds

		String tmpTxtFile = "/tmp/" + empCell + "-" + tm + ".txt"; // the text
																	// file of
																	// the
																	// prompt

		String tmpWavFile = "/tmp/" + empCell + "-" + tm; // the wav file of the
															// prompt

		WriteToFile(tmpTxtFile, prompt); // to text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // to wav file

		// this is the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ empCell + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Exten: s" + "\r\n"
				+ "Async: true\r\n" + "Variable: DOCID=" + empDoc + ",EMPNAME="
				+ empName + ",EMPCELL=" + empCell + ",PRWAVE=" + tmpWavFile
				+ "\r\n" + // + ",LOCKBOXADDR=" + lockboxaddr + ",PRWAVE=" +
							// tmpWavFile +",REMOTEEXTEN="+ phone
				// + oldextenStr + otherphoneVar + tenantNameVar + "\r\n" +
				"Context: prompttopunchin\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

	}

	private void employeeDailyCall(String[] options) {
		if (options.length < 3)
			return;

		String cell = options[0];// the super cell number
		String docid = options[1]; // the document id

		String fprompt = options[2]; // the prompt played to the super

		// ***************************************
		long tm = Calendar.getInstance().getTimeInMillis(); // get the
															// millseconds

		String tmpTxtFile = "/tmp/" + cell + "-" + tm + ".txt"; // temporay text
																// file for the
																// prompts

		String tmpWavFile = "/tmp/" + cell + "-" + tm; // the wav file

		WriteToFile(tmpTxtFile, fprompt); // write the prompt to file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // convert it to wav file

		// ************************************

		// this is the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "LOCAL" + "/"
				+ cell + "@empldailout\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Exten: s" + "\r\n"
				+ "Async: true\r\n" + "Variable: DOCID=" + docid + ",FPROMPT="
				+ tmpWavFile + "\r\n" + // + ",LOCKBOXADDR=" + lockboxaddr +
										// ",PRWAVE=" + tmpWavFile
										// +",REMOTEEXTEN="+ phone
				// + oldextenStr + otherphoneVar + tenantNameVar + "\r\n" +
				"Context: empldialreport\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

	}

	private boolean ifautocallback(String uid) {
		boolean ret = false;
		Connection con = null;

		// sql for mysql database
		String sqlstr = "select duration, billsec, dst, lastapp from cdr where uniqueid=\'"
				+ uid + "\' ";

		try {
			con = DbConnection.getConnection(); // get the mysql connection from
												// the database connection spool
			Statement stat = con.createStatement(); // get the sql statement
			ResultSet result = stat.executeQuery(sqlstr); // execute the sql

			if (result.next()) {

				String lastapp = result.getString("lastapp");
				String duration = result.getString("duration");
				String billsec = result.getString("billsec");
				String dst = result.getString("dst");

				int iBillSec = Integer.parseInt(billsec);
				int iDuration = Integer.parseInt(duration);

				if (iBillSec == iDuration) {
					if (!lastapp.equals("VoiceMail"))
						ret = true;
					else {
						if (dst.equals("601")) {
							if (iBillSec < 65)
								ret = true;

						} else {
							if (iBillSec < 50)
								ret = true;
						}

					}

				}

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DbConnection.FreeCon(con); // free the mysql connection from the
										// spool

		}

		return ret;
	}

	private String hangupAutoCallback(String[] options) {

		String phone = options[0];
		String dbname = options[1];
		String docid = options[2];
		String fPrompt = options[3];
		String uniqueid = options[4];

		String emaddr = options[5];

		String ext = options[6];

		// if (!ifautocallback(uniqueid)) return "ok";

		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds for
															// temporary wav
															// file name

		String tmpTxtFile = "/tmp/" + phone + "-" + tm + ".txt"; // the prompt
																	// text file

		String tmpWavFile = "/tmp/" + phone + "-" + tm; // the prompt wav file

		WriteToFile(tmpTxtFile, fPrompt); // write the prompt into the text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // convert the text to wav
														// file

		File f = new File("/var/lib/asterisk/sounds/custom/cbpn" + ext + ".wav");

		if (f.exists()) {
			tmpWavFile = "custom/cbpn" + ext;
		}

		// start the call back here

		String dbdoc = ",__dbname=" + dbname + ",__docid=" + docid
				+ ",__APROMPT=" + tmpWavFile + ",__EMAILADDR=" + emaddr; // some
																			// channel
																			// variable

		// this the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ phone + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: s\r\n" + "Async: true\r\n"
				+ "Variable: __CALLID=" + phone + dbdoc + "\r\n"
				+ "Context: HangupAutoCallBack" + "\r\n"
				+ "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return "ok";
	}

	private String webcallByCell(String[] options) {
		if (options.length == 0)
			return "";

		String phone = options[0]; // the phone number external
		String local = options[1]; // the local cell number
		String dbname = options[2]; // the database name
		String docid = options[3]; // the notes document id

		String context = "WEBCALLBACK_BYCELL"; // the web call context

		long tm = Calendar.getInstance().getTimeInMillis();// get the mill
															// seconds for web
															// call id

		String webcallid = ",__WBCBID=" + tm;
		String forexten = ",__LOCALEXTEN=" + local + ",__REMOTEEXTEN=" + phone;// some
																				// channel
																				// variable

		String oldexten = "";

		if (phone.contains("-"))// if the remote number has extension
		{
			int p = phone.indexOf('-');

			oldexten = phone.substring(p + 1);
			phone = phone.substring(0, p);

		}

		String oldextenStr = "";

		if (!oldexten.equals("")) {
			StringBuffer strbNum = new StringBuffer(oldexten);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i); // set the dtmf
																// keys

				// TSleep(1000);
			}
			oldextenStr = ",__DTMFKEYS=" + dtmfkeys;

		}

		/*
		 * String local_ext = ""; if (local.contains("-")) { int p =
		 * local.indexOf('-');
		 * 
		 * local_ext = local.substring(p+1); local = local.substring(0, p);
		 * 
		 * }
		 * 
		 * 
		 * String localDtmf = "";
		 * 
		 * if (!local_ext.equals("")) {
		 * 
		 * StringBuffer strbNum = new StringBuffer(local_ext); String dtmfkeys =
		 * ""; for (int i=0; i<strbNum.length(); i++){
		 * 
		 * dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);
		 * 
		 * // TSleep(1000); } localDtmf = ",__DTMFKEYSLOCAL=" + dtmfkeys;
		 * 
		 * }
		 */

		String dbdoc = ",__DBNAME=" + dbname + ",__DOCID=" + docid; // some
																	// channel
																	// variable

		// this the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ local + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: " + phone + "\r\n" + "Async: true\r\n"
				+ "Variable: __CALLID=" + phone + oldextenStr + dbdoc
				+ webcallid + forexten + "\r\n" + "Context: " + context
				+ "\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return "" + tm; // return the web call id

	}

	private String webcallByCellForVm(String[] options) {
		if (options.length == 0)
			return "";

		String phone = options[0]; // the phone number external
		String local = options[1]; // the local cell number
		String dbname = options[2]; // the database name
		String docid = options[3]; // the notes document id
		String vm = options[4];

		String context = "WEBCALLBACK_BYCELLFORVM"; // the web call context

		long tm = Calendar.getInstance().getTimeInMillis();// get the mill
															// seconds for web
															// call id

		String webcallid = ",__WBCBID=" + tm;
		String forexten = ",__LOCALEXTEN=" + local + ",__REMOTEEXTEN=" + phone;// some
																				// channel
																				// variable

		String oldexten = "";

		if (phone.contains("-"))// if the remote number has extension
		{
			int p = phone.indexOf('-');

			oldexten = phone.substring(p + 1);
			phone = phone.substring(0, p);

		}

		String oldextenStr = "";

		if (!oldexten.equals("")) {
			StringBuffer strbNum = new StringBuffer(oldexten);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i); // set the dtmf
																// keys

				// TSleep(1000);
			}
			oldextenStr = ",__DTMFKEYS=" + dtmfkeys;

		}

		/*
		 * String local_ext = ""; if (local.contains("-")) { int p =
		 * local.indexOf('-');
		 * 
		 * local_ext = local.substring(p+1); local = local.substring(0, p);
		 * 
		 * }
		 * 
		 * 
		 * String localDtmf = "";
		 * 
		 * if (!local_ext.equals("")) {
		 * 
		 * StringBuffer strbNum = new StringBuffer(local_ext); String dtmfkeys =
		 * ""; for (int i=0; i<strbNum.length(); i++){
		 * 
		 * dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);
		 * 
		 * // TSleep(1000); } localDtmf = ",__DTMFKEYSLOCAL=" + dtmfkeys;
		 * 
		 * }
		 */

		String dbdoc = ",__DBNAME=" + dbname + ",__DOCID=" + docid; // some
																	// channel
																	// variable

		// this the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ local + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: s\r\n" + "Async: true\r\n"
				+ "Variable: __CALLID=" + phone + oldextenStr + dbdoc
				+ webcallid + forexten + "\r\n" + "Variable: vmrec=" + vm
				+ "\r\n" + "Context: " + context + "\r\n"
				+ "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return "" + tm; // return the web call id

	}

	// make a conference with SUPER/MANAGER
	private String confWithSuper(String[] options) {

		String exten = options[0]; // the extension number
		String supercell = options[1]; // the super/manager cell

		String confno = exten + "conf"; // the conference name

		String extenStatus = getExtenStatus(exten);// get the status of the
													// extension

		if (!(extenStatus.equals("INTALKING") // if the extension not in talking
												// or in conference, then don't
												// make the conference
		|| extenStatus.equals("INCONF"))) {
			return "NOT IN TALKING";
		}

		// the originate command to make the super/manager cell join in the
		// conference
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ supercell + "\r\n" + "WaitTime: 30\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Exten: " + confno + "\r\n"
				+ "Async: true\r\n" + "Context: conferrence\r\n"
				+ "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		if (extenStatus.equals("INTALKING")) // the extension not in conference,
												// just in talking, then let it
												// join the meeting too
		{
			String[] bChannels = getBothChannels(exten); // get the both
															// channels of
															// caller and called

			// this command is to redirect the two channels to the conferrence
			call = "Action: redirect\r\n" + "Channel: " + bChannels[0] + "\r\n"
					+ "ExtraChannel: " + bChannels[1] + "\r\n" + "Exten: "
					+ confno + "\r\n" + "Context: conferrence\r\n"
					+ "Priority: 1\r\n\r\n";

			makecalls(call);// send the command to asterisk

		}

		return "OK";
	}

	// get the both channels for the internal extension in talking
	private String[] getBothChannels(String exten) {
		String[] ret = new String[2]; // the return string array

		ret[0] = "ok1";
		ret[1] = "ok2";

		Connection con = null;

		// search in mysql to get both channels
		String sqlstr = "select localchannel, remotechannel from extenstatus where exten=\'"
				+ exten + "\' ";

		try {
			con = DbConnection.getConnection(); // get a mysql connection from
												// mysql spool
			Statement stat = con.createStatement(); // sql statement
			ResultSet result = stat.executeQuery(sqlstr); // execute the sql to
															// get the result

			if (result.next()) {

				ret[0] = result.getString("localchannel"); // get the local
															// extension channel
				ret[1] = result.getString("remotechannel"); // get the remote
															// party channel

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DbConnection.FreeCon(con); // free the connection of mysql

		}

		return ret; // return the result.

	}

	// this method is for getting extension status
	private String getExtenStatus(String exten) {

		String ret = "NONE";

		Connection con = null;

		// sql for mysql database
		String sqlstr = "select exstatus from extenstatus where exten=\'"
				+ exten + "\' ";

		try {
			con = DbConnection.getConnection(); // get the mysql connection from
												// the database connection spool
			Statement stat = con.createStatement(); // get the sql statement
			ResultSet result = stat.executeQuery(sqlstr); // execute the sql

			if (result.next()) {

				ret = result.getString("exstatus"); // get the extension status

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DbConnection.FreeCon(con); // free the mysql connection from the
										// spool

		}

		return ret;
	}

	private String rentqueueadd(String[] options) {

		String exten = options[0];

		String call = "Action: QueueAdd\r\n" + "Queue: 009\r\n"
				+ "Interface: SIP/" + exten + "\r\n\r\n";

		makecalls(call);

		return "OK";
	}

	private String rentqueueremove(String[] options) {

		String exten = options[0];

		String call = "Action: QueueRemove\r\n" + "Queue: 009\r\n"
				+ "Interface: SIP/" + exten + "\r\n\r\n";

		makecalls(call);

		return "OK";
	}

	// the method is for web call from external to internal
	private String runWebCallExtInt(String[] options) {
		if (options.length == 0)
			return "";

		String local = options[0]; // the phone number external
		String phone = options[1]; // the local extension number
		String dbname = options[2]; // the database name
		String docid = options[3]; // the notes document id

		String context = "WEBCALLBACK_EXTINT"; // the web call context

		long tm = Calendar.getInstance().getTimeInMillis();// get the mill
															// seconds for web
															// call id

		String webcallid = ",__WBCBID=" + tm;
		String forexten = ",__WBCBEXTEN=" + local + ",__REMOTEEXTEN=" + phone;// some
																				// channel
																				// variable

		String oldexten = "";

		if (phone.contains("-"))// if the remote number has extension
		{
			int p = phone.indexOf('-');

			oldexten = phone.substring(p + 1);
			phone = phone.substring(0, p);

		}

		String oldextenStr = "";

		if (!oldexten.equals("")) {
			StringBuffer strbNum = new StringBuffer(oldexten);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i); // set the dtmf
																// keys

				// TSleep(1000);
			}
			oldextenStr = ",__DTMFKEYS=" + dtmfkeys;

		}

		/*
		 * String local_ext = ""; if (local.contains("-")) { int p =
		 * local.indexOf('-');
		 * 
		 * local_ext = local.substring(p+1); local = local.substring(0, p);
		 * 
		 * }
		 * 
		 * 
		 * String localDtmf = "";
		 * 
		 * if (!local_ext.equals("")) {
		 * 
		 * StringBuffer strbNum = new StringBuffer(local_ext); String dtmfkeys =
		 * ""; for (int i=0; i<strbNum.length(); i++){
		 * 
		 * dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);
		 * 
		 * // TSleep(1000); } localDtmf = ",__DTMFKEYSLOCAL=" + dtmfkeys;
		 * 
		 * }
		 */

		String dbdoc = ",__dbname=" + dbname + ",__docid=" + docid; // some
																	// channel
																	// variable

		// this the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ phone + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: " + local + "\r\n" + "Async: true\r\n"
				+ "Variable: __CALLID=" + phone + oldextenStr + dbdoc
				+ webcallid + forexten + "\r\n" + "Context: " + context
				+ "\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return "" + tm; // return the web call id

	}

	// web call form external to external
	private String runWebCallExtExt(String[] options) {
		if (options.length == 0)
			return "";

		String local = options[0]; // the called external number
		String phone = options[1]; // the calling external number
		String dbname = options[2]; // the database name
		String docid = options[3]; // the document id

		String context = "WEBCALLBACK_EXTEXT"; // the context

		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds for web
															// call id

		// some channel variables
		String webcallid = ",__WBCBID=" + tm;
		String forexten = ",__WBCBEXTEN=" + local + ",__REMOTEEXTEN=" + phone;

		String oldexten = "";

		if (phone.contains("-")) // if it has an extention
		{
			int p = phone.indexOf('-');

			oldexten = phone.substring(p + 1);
			phone = phone.substring(0, p);

		}

		String oldextenStr = "";

		// set the dtmf keys
		if (!oldexten.equals("")) {
			StringBuffer strbNum = new StringBuffer(oldexten);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);

				// TSleep(1000);
			}
			oldextenStr = ",__DTMFKEYS=" + dtmfkeys;

		}

		// if the calling number has an extension
		String local_ext = "";
		if (local.contains("-")) {
			int p = local.indexOf('-');

			local_ext = local.substring(p + 1);
			local = local.substring(0, p);

		}

		// set the callling number dtmf keys
		String localDtmf = "";

		if (!local_ext.equals("")) {

			StringBuffer strbNum = new StringBuffer(local_ext);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);

				// TSleep(1000);
			}
			localDtmf = ",__DTMFKEYSLOCAL=" + dtmfkeys;

		}

		// channel variables for database name and document id
		String dbdoc = ",__dbname=" + dbname + ",__docid=" + docid;

		// the asterisk command to make a call
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
				+ local + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: " + phone + "\r\n" + "Async: true\r\n"
				+ "Variable: __CALLID=" + local + oldextenStr + dbdoc
				+ webcallid + forexten + localDtmf + "\r\n" + "Context: "
				+ context + "\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		return "" + tm; // return the webcall id

	}

	// this method is for rfp daily call
	private void rfpdailycall(String[] options) {

		String cell = options[0]; // the vendor cell phone number
		String Prompt = options[2]; // the prompt to play
		String docnum = options[3]; // the document number

		String docponums = options[1]; // the po numbers

		String addponums = options[4]; // the building addresses
		String jtiponums = options[5]; // the job titles

		String jnmponums = options[6]; // the job numbers

		String ifnodate = options[7];

		// ****************************

		String docs = ",MULTIDOCS="; // set the document id as the channel
										// variable
		String NumVar = ",DOCNUM=" + docnum; // set the document number
		String POVar = ",PONUMS=";

		String DOCArray = "RFPDOCS=";
		String ARArray = "RFPADDRS=";
		String JTArray = "RFPJTITLES=";
		String JNArray = "RFPJNUMS=";
		String POArray = "RFPPOS=";

		String dailoutContext = "rfpdialout"; // the dial plan context

		if (isBeta) // if beta version
		{
			dailoutContext = "rfpdialoutForTest";
		}

		if (docponums.endsWith(":"))
			docponums = docponums.substring(0, docponums.length() - 1);

		String[] ponums = docponums.split(":"); // get the po numbers array

		for (int i = 0; i < ponums.length; i++) {
			String podoc = ponums[i];
			int p1 = podoc.indexOf('<');
			String po = ponums[i].substring(0, p1);
			POVar = POVar + po + "\\,";

			POArray = POArray + po + ":"; // get the po numbers array
			// POArray = POArray + ",PN" + (i+1) + "=" + po;

			int p2 = podoc.indexOf('>');

			String doc = ponums[i].substring(p1 + 1, p2);

			DOCArray = DOCArray + doc + ":"; // get the documents array

			docs = docs + doc + "@";

		}

		if (docs.endsWith("@")) {
			docs = docs.substring(0, docs.length() - 1);
		}
		// **************************************************************

		if (addponums.endsWith(":"))
			addponums = addponums.substring(0, addponums.length() - 1);

		String[] ARponums = addponums.split(":");

		for (int i = 0; i < ARponums.length; i++) {
			String poAR = ARponums[i];
			int p1 = poAR.indexOf('<');
			String po = poAR.substring(0, p1);

			int p2 = poAR.indexOf('>');

			String ar = poAR.substring(p1 + 1, p2);

			ARArray = ARArray + ar + ":";// get the address array

		}

		// *******************************************************************
		if (jtiponums.endsWith(":"))
			jtiponums = jtiponums.substring(0, jtiponums.length() - 1);

		String[] JTponums = jtiponums.split(">:");

		for (int i = 0; i < JTponums.length; i++) {
			String poJT = JTponums[i];
			int p1 = poJT.indexOf('<');
			String po = poJT.substring(0, p1);

			String jt = poJT.substring(p1 + 1);

			int p2 = poJT.indexOf('>');

			if (p2 > -1)
				jt = poJT.substring(p1 + 1, p2);

			JTArray = JTArray + jt + ":"; // get the job title array

		}
		// **************************************************************

		if (jnmponums.endsWith(":"))
			jnmponums = jnmponums.substring(0, jnmponums.length() - 1);

		String[] JNponums = jnmponums.split(":");

		for (int i = 0; i < JNponums.length; i++) {
			String poJN = JNponums[i];
			int p1 = poJN.indexOf('<');
			String po = poJN.substring(0, p1);

			int p2 = poJN.indexOf('>');

			String jn = poJN.substring(p1 + 1, p2);

			JNArray = JNArray + jn + ":"; // get the job number array

		}

		// ***********************************************************
		/*
		 * if (POVar.endsWith("\\,")) { POVar = POVar.substring(0,
		 * POVar.length()-2); }
		 */

		// *********************************
		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds for
															// temporary wav
															// file name

		String tmpTxtFile = "/tmp/" + cell + "-" + tm + ".txt"; // the prompt
																// text file

		String tmpWavFile = "/tmp/" + cell + "-" + tm; // the prompt wav file

		WriteToFile(tmpTxtFile, Prompt); // write the prompt into the text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // convert the text to wav
														// file

		// this the asterisk command to start the rfp call
		String call = "Action: originate\r\n" + "Channel: " + "LOCAL" + "/"
				+ cell + "@" + dailoutContext + "\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 6465726400" + "\r\n" + "Exten: s" + "\r\n"
				+ "Async: true\r\n" + "Variable: RFPPrompt=" + tmpWavFile

				+ docs
				+ NumVar
				+ POVar
				+ "\r\n"
				+ // + ",LOCKBOXADDR=" + lockboxaddr + ",PRWAVE=" + tmpWavFile
					// +",REMOTEEXTEN="+ phone

				"Variable: " + DOCArray + "\r\n" + "Variable: " + ARArray
				+ "\r\n" + "Variable: " + JTArray + "\r\n" + "Variable: "
				+ POArray + "\r\n" + "Variable: " + JNArray + "\r\n" +

				"Variable: nodate=" + ifnodate + "\r\n" +
				// + oldextenStr + otherphoneVar + tenantNameVar + "\r\n" +
				"Context: rfpdailycall\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

	}

	// this method is for convert the txt to wav when asterisk prompt document
	// saved in notes side
	private void convertTextToAudio(String[] options) {
		String tip1 = options[0]; // 0-4 are five strings of text.
		String tip2 = options[1];

		String wrongnum = options[2];

		String reschacc = options[3];

		String wcwarn = options[4];

		String yohomsg = options[5];

		String tmpTxtFile = "/tmp/tip.txt"; // temporay file

		WriteToFile(tmpTxtFile, tip1); // write the tip1 to the tmp file.

		ConverToWav("/var/lib/asterisk/sounds/custom/moniprompt" + ".wav",
				tmpTxtFile); // convert it to wav file

		WriteToFile(tmpTxtFile, tip2); // write the tip2 to the tmp file

		ConverToWav("/var/lib/asterisk/sounds/custom/legalfirst2" + ".wav",
				tmpTxtFile); // convert it to wav file
		// .........same things....
		WriteToFile(tmpTxtFile, wrongnum);

		ConverToWav("/var/lib/asterisk/sounds/custom/wrongperson" + ".wav",
				tmpTxtFile);

		WriteToFile(tmpTxtFile, reschacc);

		ConverToWav("/var/lib/asterisk/sounds/custom/reschacc" + ".wav",
				tmpTxtFile);

		WriteToFile(tmpTxtFile, wcwarn);

		ConverToWav("/var/lib/asterisk/sounds/custom/wcwarn" + ".wav",
				tmpTxtFile);

		WriteToFile(tmpTxtFile, yohomsg);

		ConverToWav("/var/lib/asterisk/sounds/custom/yohomsg" + ".wav",
				tmpTxtFile);

	}

	// this mehtod is for making the super dialy call
	private void makeSuperDailyCall(String[] options) {
		if (options.length < 3)
			return;

		String cell = options[0];// the super cell number
		String docid = options[1]; // the document id

		String fprompt = options[2]; // the prompt played to the super

		// ***************************************
		long tm = Calendar.getInstance().getTimeInMillis(); // get the
															// millseconds

		String tmpTxtFile = "/tmp/" + cell + "-" + tm + ".txt"; // temporay text
																// file for the
																// prompts

		String tmpWavFile = "/tmp/" + cell + "-" + tm; // the wav file

		WriteToFile(tmpTxtFile, fprompt); // write the prompt to file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // convert it to wav file

		// ************************************

		// this is the command sent to asterisk
		String call = "Action: originate\r\n" + "Channel: " + "LOCAL" + "/"
				+ cell + "@superdailout\r\n" + "WaitTime: 60\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Exten: s" + "\r\n"
				+ "Async: true\r\n" + "Variable: DOCID=" + docid + ",FPROMPT="
				+ tmpWavFile + "\r\n" + // + ",LOCKBOXADDR=" + lockboxaddr +
										// ",PRWAVE=" + tmpWavFile
										// +",REMOTEEXTEN="+ phone
				// + oldextenStr + otherphoneVar + tenantNameVar + "\r\n" +
				"Context: superdialreport\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

	}

	// this web service method is for makeing web call
	private String makewebcall(String[] options) {

		if (options.length == 0)
			return "";

		String phone = options[0]; // the remote phone number
		String local = options[1]; // the local phone number
		String dbname = options[2]; // the database
		String docid = options[3]; // the documen

		String context = "WEBCALLBACK"; // the web call context

		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds

		String webcallid = ",__WBCBID=" + tm; // set the channle variable of
												// wbcbid
		String forexten = ",__WBCBEXTEN=" + local + ",__REMOTEEXTEN=" + phone; // set
																				// the
																				// local
																				// and
																				// remote
																				// channel
																				// variable

		String oldexten = "";

		// get the extenion of the phone
		if (phone.contains("-")) {
			int p = phone.indexOf('-');

			oldexten = phone.substring(p + 1);
			phone = phone.substring(0, p);

		}

		String oldextenStr = "";

		// get the dtmf keys for the phone
		if (!oldexten.equals("")) {
			StringBuffer strbNum = new StringBuffer(oldexten);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);

				// TSleep(1000);
			}
			oldextenStr = ",__DTMFKEYS=" + dtmfkeys;

		}

		// set the channel variable for database and documet id
		String dbdoc = ",__dbname=" + dbname + ",__docid=" + docid;

		/*
		 * String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/xo/"
		 * + phone + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423" +
		 * "\r\n" + "Exten: " + local + "\r\n" + "Async: true\r\n" +
		 * "Variable: CALLID=" + phone + oldextenStr + dbdoc + webcallid +
		 * forexten + "\r\n" + "Context: " + context + "\r\n" +
		 * "Priority: 1\r\n\r\n" ;
		 */

		// the asterisk command to make the call
		String call = "Action: originate\r\n" + "Channel: " + "SIP" + "/"
				+ local + "\r\n" + "WaitTime: 30\r\n" + "CallerId: 2123171423"
				+ "\r\n" + "Exten: " + phone + "\r\n" + "Async: true\r\n"
				+ "Variable: __CALLID=" + phone + oldextenStr + dbdoc
				+ webcallid + forexten + "\r\n" + "Context: " + context
				+ "\r\n" + "Priority: 1\r\n\r\n";

		makecalls(call); // send the asterisk command to make the call

		return "" + tm; // return the mill seconds as the web call id
		// trysleep(1000);

	}

	private void trysleep(long milsecs) {
		try {
			Thread.sleep(milsecs);
		} catch (Exception e) {

		}

	}

	// this web service method is for payment reminder and 3 DN notice
	private void makepayremainder(String[] options) {
		if (options.length == 0)
			return;

		// String call = "";
		// for (int i=0; i<response.length;i++)
		// {
		// String call = response[i];
		// String res[] = call.split("\\,");

		String docid = options[0];// get the document id

		String phone = options[1]; // the phone number to call

		String finalprompt = options[2]; // the prompt

		String lockboxaddr = options[3]; // the lockbox address

		// ********************************************************

		String otherphone = "";
		String otherphoneVar = "";
		String tenantName = "";
		String tenantNameVar = "";

		int ip = phone.indexOf(':');

		// get the first phone number and the other left as other phones
		if (ip > -1) {
			if (ip < phone.length() - 1)
				otherphone = phone.substring(ip + 1);

			phone = phone.substring(0, ip);

		}

		ip = phone.indexOf('<');

		tenantName = phone.substring(0, ip); // get the tenant name

		int ipi = phone.indexOf('>');

		phone = phone.substring(ip + 1, ipi); // get the phone number

		if (!tenantName.equals(""))
			tenantNameVar = ",NAMECALLED=" + tenantName;

		if (!otherphone.equals(""))
			otherphoneVar = ",OTHERPHONE=" + otherphone; // set the other phone
															// number channel
															// variable

		if (finalprompt.contains("[Tenant]"))// set the tenant name in the
												// prompt
			finalprompt = finalprompt.replace("[Tenant]", tenantName);

		// *****************************************************

		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds

		String tmpTxtFile = "/tmp/" + phone + "-" + tm + ".txt";

		String tmpWavFile = "/tmp/" + phone + "-" + tm;

		WriteToFile(tmpTxtFile, finalprompt);

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // transfer the text to
														// wav file

		// *****************************************************************

		String oldexten = "";

		// get the extension of the phone number
		if (phone.contains("-")) {
			int p = phone.indexOf('-');

			oldexten = phone.substring(p + 1);
			phone = phone.substring(0, p);

		}

		String oldextenStr = "";

		if (!oldexten.equals("")) {
			oldextenStr = ",__OLDEXTEN=" + oldexten;

		}

		String paydialout = "paydialout"; // the dial context

		if (isBeta) {
			paydialout = "paydialouttest";
		}

		// the asterisk command to make the call
		String call = "Action: originate\r\n" + "Channel: " + "LOCAL" + "/"
				+ phone + "@" + paydialout + "\r\n" + "WaitTime: 30\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Exten: s" + "\r\n"
				+ "Async: true\r\n" + "Variable: DOCID=" + docid
				+ ",LOCKBOXADDR=" + lockboxaddr + ",PRWAVE=" + tmpWavFile
				+ ",REMOTEEXTEN=" + phone + oldextenStr + otherphoneVar
				+ tenantNameVar + "\r\n" + "Context: payreminder\r\n"
				+ "Priority: 1\r\n\r\n";

		makecalls(call); // send the asterisk command

		// }

		/*
		 * 
		 * 
		 * try{ String hostname = "127.0.0.1"; SocketFactory factory = null;
		 * 
		 * if (false) factory= (SSLSocketFactory)SSLSocketFactory.getDefault();
		 * else factory = SocketFactory.getDefault();
		 * 
		 * Socket clientRequest = factory.createSocket(hostname, 5038);
		 * 
		 * BufferedReader input; //输入流 PrintWriter output; //输出流
		 * 
		 * InputStreamReader reader; OutputStreamWriter writer;
		 * 
		 * 
		 * reader = new InputStreamReader(clientRequest.getInputStream());
		 * 
		 * input = new BufferedReader(reader);
		 * 
		 * writer = new OutputStreamWriter(clientRequest.getOutputStream());
		 * 
		 * output = new PrintWriter(writer,true);
		 * 
		 * 
		 * String actions = "Action: login\r\n" + "Events: off\r\n" +
		 * "Username: admin\r\n" + "Secret: amp111\r\n\r\n" + call +
		 */
		/*
		 * "Action: originate\r\n" + "Channel: SIP/" + local + "\r\n" +
		 * "WaitTime: 30\r\n" + "CallerId: " + CallerID + "\r\n" + "Exten: " +
		 * remote + "\r\n" + "Async: true\r\n" + "Variable: LOCALEXTEN=" + local
		 * + ",DOCID=" + DocID + ",DBNAME=" + dbname + "\r\n" +
		 * "Context: clicktodial\r\n" + "Priority: 1\r\n\r\n" +
		 */
		/*
		 * "Action: Logoff\r\n\r\n";
		 * 
		 * 
		 * output.println(actions);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * input.close(); output.close(); clientRequest.close();
		 * 
		 * }catch(Exception e) {
		 * 
		 * }
		 */

	}

	// private String[] sendAction(String action)
	// {
	// try{
	// String hostname = "127.0.0.1"; // localhost ip
	// SocketFactory factory = null;
	//
	// if (false)
	// factory= (SSLSocketFactory)SSLSocketFactory.getDefault();
	// else
	// factory = SocketFactory.getDefault(); // get the socket factory
	//
	// Socket clientRequest = factory.createSocket(hostname, 5038); // connect
	// to asterisk 5038 port of manager api
	//
	// BufferedReader input; //the input stream
	// PrintWriter output; //the outpur stream
	//
	// InputStreamReader reader; // the input reader
	// OutputStreamWriter writer; // the outpur writer
	//
	//
	// reader = new InputStreamReader(clientRequest.getInputStream());
	//
	// input = new BufferedReader(reader); // get the input reader
	//
	// writer = new OutputStreamWriter(clientRequest.getOutputStream());
	//
	// output = new PrintWriter(writer,true); // get the outpur writer
	//
	//
	// String actions = "Action: login\r\n" +
	// "Events: off\r\n" +
	// "Username: admin\r\n" +
	// "Secret: amp111\r\n\r\n" + // first action is logging the asterisk
	// manager
	// action + // then send the action of command
	// /*
	// "Action: originate\r\n" +
	// "Channel: SIP/" + local + "\r\n" +
	// "WaitTime: 30\r\n" +
	// "CallerId: " + CallerID + "\r\n" +
	// "Exten: " + remote + "\r\n" +
	// "Async: true\r\n" +
	// "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID + ",DBNAME=" + dbname
	// + "\r\n" +
	// "Context: clicktodial\r\n" +
	// "Priority: 1\r\n\r\n" +
	// */
	//
	// "Action: Logoff\r\n\r\n"; // then log off the asterisk manager
	//
	//
	// output.println(actions); // write to the socket
	//
	// String line = input.readLine();
	//
	// while (line != null)
	// {
	//
	//
	// line = input.readLine();
	// }
	//
	//
	//
	// // close all streams
	// input.close();
	// output.close();
	// clientRequest.close();
	//
	// }catch(Exception e)
	// {
	// }
	//
	//
	//
	// }
	//

	// this is the fuction for sending command to asterisk
	private void makecalls(String calls) {
		try {
			String hostname = "127.0.0.1"; // localhost ip
			SocketFactory factory = null;

			if (false)
				factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			else
				factory = SocketFactory.getDefault(); // get the socket factory

			Socket clientRequest = factory.createSocket(hostname, 5038); // connect
																			// to
																			// asterisk
																			// 5038
																			// port
																			// of
																			// manager
																			// api

			BufferedReader input; // the input stream
			PrintWriter output; // the outpur stream

			InputStreamReader reader; // the input reader
			OutputStreamWriter writer; // the outpur writer

			reader = new InputStreamReader(clientRequest.getInputStream());

			input = new BufferedReader(reader); // get the input reader

			writer = new OutputStreamWriter(clientRequest.getOutputStream());

			output = new PrintWriter(writer, true); // get the outpur writer

			String actions = "Action: login\r\n" + "Events: off\r\n"
					+ "Username: admin\r\n" + "Secret: amp111\r\n\r\n" + // first
																			// action
																			// is
																			// logging
																			// the
																			// asterisk
																			// manager
					calls + // then send the action of command
					/*
					 * "Action: originate\r\n" + "Channel: SIP/" + local +
					 * "\r\n" + "WaitTime: 30\r\n" + "CallerId: " + CallerID +
					 * "\r\n" + "Exten: " + remote + "\r\n" + "Async: true\r\n"
					 * + "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID +
					 * ",DBNAME=" + dbname + "\r\n" + "Context: clicktodial\r\n"
					 * + "Priority: 1\r\n\r\n" +
					 */

					"Action: Logoff\r\n\r\n"; // then log off the asterisk
												// manager

			output.println(actions); // write to the socket

			String line = input.readLine();

			while (line != null) {

				line = input.readLine();
			}

			// close all streams
			input.close();
			output.close();
			clientRequest.close();

		} catch (Exception e) {
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
	private void ConverToWavSp(String w, String t) {

		String[] cmds = new String[3];
		cmds[0] = "/var/javalib/swiftSp.sh";// the shell command to convert the
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

	// this method is for access date auto call
	private String makeAccessDateCalls(String[] options) {
		String ret = "OK"; // the default value is "ok"

		if (options.length == 0)
			return ret;

		// String call = "";
		// for (int i=0; i<response.length;i++)
		// {
		// String call = response[i];
		// String res[] = call.split("\\,");

		// get the access date document id
		String acdocid = options[0];

		// the the phone numbers
		String acphone = options[1];
		// String acdate = getacdate(options[3]);

		String finalprompt = options[2];// the prompts

		String acdate = options[3]; // the access date

		// ********************************************************

		// *****************************************************************
		String otherphone = "";
		String otherphoneVar = "";
		String tenantName = "";
		String tenantNameVar = "";

		int ip = acphone.indexOf(':');

		if (ip > -1) {
			if (ip < acphone.length() - 1)
				otherphone = acphone.substring(ip + 1); // the other phones left

			acphone = acphone.substring(0, ip);// get the current phone number

		}

		ip = acphone.indexOf('<');

		tenantName = acphone.substring(0, ip); // get the tenant name

		int ipi = acphone.indexOf('>');

		acphone = acphone.substring(ip + 1, ipi); // the phone number to call

		if (!tenantName.equals(""))
			tenantNameVar = ",NAMECALLED=" + tenantName; // set the channel
															// variable of
															// tenant name

		if (!otherphone.equals(""))
			otherphoneVar = ",OTHERPHONE=" + otherphone; // set the other phone
															// variable

		// *********************************************
		if (finalprompt.contains("[Tenant]"))
			finalprompt = finalprompt.replace("[Tenant]", tenantName);// replace
																		// the
																		// tenant
																		// variable
																		// as
																		// the
																		// tenant
																		// name

		// the prompt
		String ForOptions = "Press 1 to confirm your appointment for " + acdate
				+ ",Press 2 if you cannot make your appointment,"
				+ "Press 3 if you are Not " + tenantName;

		// ***************************************
		long tm = Calendar.getInstance().getTimeInMillis(); // get the mill
															// seconds

		String tmpTxtFile = "/tmp/" + acphone + "-" + tm + ".txt"; // the text
																	// file of
																	// the
																	// prompt

		String tmpWavFile = "/tmp/" + acphone + "-" + tm; // the wav file of the
															// prompt

		WriteToFile(tmpTxtFile, finalprompt); // to text file

		ConverToWav(tmpWavFile + ".wav", tmpTxtFile); // to wav file

		// ****************************************
		String tmpOptTxtFile = "/tmp/" + acphone + "opt-" + tm + ".txt"; // the
																			// text
																			// for
																			// options

		String tmpOptWavFile = "/tmp/" + acphone + "opt-" + tm; // the wav file
																// for options

		WriteToFile(tmpOptTxtFile, ForOptions); // write the options to text
												// file

		ConverToWav(tmpOptWavFile + ".wav", tmpOptTxtFile); // transfer the text
															// file to wav file

		// *******************************************************

		String oldexten = "";

		// get the phone extension
		if (acphone.contains("-")) {
			int p = acphone.indexOf('-');

			oldexten = acphone.substring(p + 1);
			acphone = acphone.substring(0, p);

		}

		String oldextenStr = "";
		/*
		 * if (!oldexten.equals("")) { oldextenStr = ",__OLDEXTEN=" + oldexten;
		 * 
		 * 
		 * }
		 */

		// set the dtmf keys for extension
		if (!oldexten.equals("")) {
			StringBuffer strbNum = new StringBuffer(oldexten);
			String dtmfkeys = "";
			for (int i = 0; i < strbNum.length(); i++) {

				dtmfkeys = dtmfkeys + "ww" + strbNum.charAt(i);

				// TSleep(1000);
			}
			oldextenStr = ",__DTMFKEYS=" + dtmfkeys;

		}

		// the context
		String paydialout = "paydialout";
		if (isBeta)
			paydialout = "paydialouttest";

		// the call command sent to asterisk

		String call = "Action: originate\r\n" + "Channel: " + "LOCAL" + "/"
				+ acphone + "@" + paydialout + "\r\n" + "WaitTime: 30\r\n"
				+ "CallerId: 2123171423" + "\r\n" + "Exten: s" + "\r\n"
				+ "Async: true\r\n" + "Variable: DOCID=" + acdocid
				+ ",ACOPTWAVE=" + tmpOptWavFile + ",ACWAVE=" + tmpWavFile
				+ oldextenStr + otherphoneVar + tenantNameVar + ",REMOTEEXTEN="
				+ acphone + "\r\n" + "Context: legaloff\r\n"
				+ "Priority: 1\r\n\r\n";

		makecalls(call); // send the command to asterisk

		// }

		/*
		 * 
		 * try{ String hostname = "127.0.0.1"; SocketFactory factory = null;
		 * 
		 * if (false) factory= (SSLSocketFactory)SSLSocketFactory.getDefault();
		 * else factory = SocketFactory.getDefault();
		 * 
		 * Socket clientRequest = factory.createSocket(hostname, 5038);
		 * 
		 * BufferedReader input; //输入流 PrintWriter output; //输出流
		 * 
		 * InputStreamReader reader; OutputStreamWriter writer;
		 * 
		 * 
		 * reader = new InputStreamReader(clientRequest.getInputStream());
		 * 
		 * input = new BufferedReader(reader);
		 * 
		 * writer = new OutputStreamWriter(clientRequest.getOutputStream());
		 * 
		 * output = new PrintWriter(writer,true);
		 * 
		 * 
		 * String actions = "Action: login\r\n" + "Events: off\r\n" +
		 * "Username: admin\r\n" + "Secret: amp111\r\n\r\n" + call + /*
		 * "Action: originate\r\n" + "Channel: SIP/" + local + "\r\n" +
		 * "WaitTime: 30\r\n" + "CallerId: " + CallerID + "\r\n" + "Exten: " +
		 * remote + "\r\n" + "Async: true\r\n" + "Variable: LOCALEXTEN=" + local
		 * + ",DOCID=" + DocID + ",DBNAME=" + dbname + "\r\n" +
		 * "Context: clicktodial\r\n" + "Priority: 1\r\n\r\n" +
		 */

		/*
		 * "Action: Logoff\r\n\r\n";
		 * 
		 * 
		 * output.println(actions);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * input.close(); output.close(); clientRequest.close();
		 * 
		 * }catch(Exception e) {
		 * 
		 * }
		 */

		return ret;
	}

	private static String getacdate(String s) {

		// if (s.contains("-"))

		String[] res = s.split("\\/");

		int month = Integer.parseInt(res[0]);
		int day = Integer.parseInt(res[1]);
		int year = Integer.parseInt(res[2]);

		System.out.println(year + "-" + month + "-" + day);

		// Date d = new Date(year, month, day);

		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, 0, 0, 0);

		System.out.println(c.getTime().toLocaleString());

		return Long.toString(c.getTime().getTime() / 1000);

	}

}
