package heightsre.java.prog;

import heightsre.java.fastagi.client.DbConnectionApache;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.asteriskjava.manager.action.GetVarAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.asteriskjava.manager.event.*;

import java.sql.*;
import java.util.Date;

public class InEmailThread extends Thread {

	String uid = "";
	channelInfo info = null;
	long dialedtime = 0;

	HangupEvent he = null;

	String chann = "";

	private AstPortalProxy service = new AstPortalProxy();
	String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";

	public InEmailThread(HangupEvent e) {
		uid = e.getUniqueId();

		he = e;

		chann = e.getChannel();

		service.setEndpoint(url);

	}

	public String getNameFromClid(String clid) {
		String res = "";

		int p1 = clid.indexOf('"');

		if (p1 != -1)
			clid = clid.substring(p1 + 1);

		int p2 = clid.indexOf('"');

		if (p2 != -1)
			clid = clid.substring(0, p2);

		res = clid;

		return res;
	}

	private void sendWcbChannHangup(HangupEvent he) {

		String chanUniqueid = he.getUniqueId();
		// String chanName = nse.getChannel();
		String chanStatus = "Hangup";
		// String chanExten = AstConnection.getVariable(nse.getChannel(),
		// "WBCBEXTEN");
		// String chanwcbid = wcbid;

		String cmd = "SETWCBSTATUS";
		String[] opts = new String[5];
		opts[0] = chanUniqueid;
		opts[1] = "";
		opts[2] = chanStatus;
		opts[3] = "";
		opts[4] = "";

		try {

			// AstPortalProxy service = new AstPortalProxy();
			// service.setEndpoint(url);

			String[] response = service.generalCommand(cmd, opts);

			// System.out.println(response[0]);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {

		}

	}

	public void sendHangupCauseToLog(HangupEvent he) {
		String uniqueid = he.getUniqueId();
		String cause = he.getCauseTxt();
		String cmd = "SENDHANGUPCAUSE";
		String[] opts = new String[2];
		opts[0] = uniqueid;
		opts[1] = cause;

		try {
			service.generalCommand(cmd, opts);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String log = cause;

		String sql = "update tblcalleropts set HangupCause= '" + log + "', LastModified=NOW() where AstUniID='"
				+ uniqueid + "'";

		DbConnectionApache.sqlExecute(sql);

	}

	public void run() {

		/*
		 * String local = ""; String remote = ""; String dialtime = ""; String
		 * recordfile = ""; String dialedtime = "";
		 */

		sendHangupCauseToLog(he);

		sendWcbChannHangup(he);

		trySleep(10000);

		Connection con = null;

		String cmd = "/var/javalib/sendmailincomingcall.sh";

		String[] cmds = new String[7];
		String sqlstr = "select userfield,clid, src, dst,dcontext, dstchannel, duration,billsec,amaflags,dstchannel from cdr where accountcode=\'IN\' "
				+ " and uniqueid=\'" + uid + "\' ";

		try {
			con = DbConnection.getConnection();
			Statement stat = con.createStatement();
			ResultSet result = stat.executeQuery(sqlstr);

			if (result.next()) {

				// boolean wcb = false;

				cmds[0] = cmd;

				boolean forsp = false;
				String spexten = "";
				String userfield = result.getString("userfield");
				String fname = userfield;

				System.out.println(userfield);
				if (userfield.contains("*sp*")) {
					forsp = true;
					String[] spres = userfield.split("\\*sp\\*");
					fname = spres[0];
					spexten = spres[1];

					System.out.println(fname);
					System.out.println(spexten);

				}

				cmds[1] = fname + ".WAV";
				cmds[2] = result.getString("src");

				cmds[3] = result.getString("dst");

				if (cmds[3].startsWith("vmu") || cmds[3].startsWith("vmb")) {
					cmds[3] = cmds[3].substring(3);

				}

				String dcontext = result.getString("dcontext");
				if (cmds[3].equals("600") || cmds[3].equals("musiconhold") || dcontext.equals("ext-group")) {

					String dstchannel = result.getString("dstchannel");

					int p1 = dstchannel.indexOf('/');
					int p2 = dstchannel.indexOf('-');

					if (p1 != -1 && p2 != -1 && p2 > p1) {
						cmds[3] = dstchannel.substring(p1 + 1, p2);
					}

				}

				String clid = result.getString("clid");

				String callerName = getNameFromClid(clid);

				/*
				 * info = AstConnection.SynData.getout(uid);
				 * 
				 * // System.out.println(he);
				 * 
				 * System.out.println(info.startDial); //
				 * System.out.println(e.getDateReceived()); try{ dialedtime =
				 * (he.getDateReceived().getTime() - info.startDial.getTime())/1000;
				 * }catch(Exception e) { System.out.println(e); }
				 */

				// int dur = result.getInt("billsec");

				// if (dur >= 4) dur = dur - 4;

				int amaflags = result.getInt("amaflags");

				int calllength = Integer.parseInt(result.getString("duration"));

				cmds[4] = result.getString("duration");// "" + dialedtime;

				cmds[5] = callerName;

				cmds[6] = "Incoming Call For Extension";

				if (forsp) {
					cmds[6] = "Special " + spexten + " Incoming Call For Extension";
				}

				if (amaflags != 3) {
					if (amaflags == 1) {
						cmds[6] = "Ext-1*Incoming Call For Extension";
					}
				}

				System.out.println("...........incoming call hangup, and test if it's transfered.........\r\n");
				System.out.println(chann);
				System.out.println(".............");

				// if (chann.startsWith("Transfered/"))

				String uf = result.getString("userfield");
				String dstchannel = result.getString("dstchannel");

				if (uf.contains("CB")) {
					// wcb = true;
					int p = uf.indexOf("CB");

					cmds[2] = uf.substring(0, p);

					String[] ret = getDbDocid(con);

					cmds[0] = "/var/javalib/sendmailwebcall.sh";

					cmds[5] = "*Web Click ID*" + ret[1] + "*Database*" + ret[0];

					cmds[6] = "Web Click To Call For Extension";

				}

				if (uf.contains("in9")) {

					// cmds[6] = "Incoming 999 Call back For Extension";

					int p = uf.indexOf("IB");

					cmds[2] = uf.substring(3, p);

					cmds[5] = cmds[2];

				}

				/*
				 * String sql = "select dst,duration from cdr where accountcode=\'IN\' " +
				 * " and userfield=\'" + uf + "\'" +
				 * " and dcontext=\'from-internal-xfer\' order by calldate";
				 */

				String sql = "select dst,duration from cdr where accountcode=\'IN\' " + " and userfield=\'" + uf + "\'"
						+ " and  dstchannel <>\'" + dstchannel + "\' "
						// + " and duration>0'"
						+ " order by calldate";

				System.out.println(sql);

				Statement stat1 = con.createStatement();
				ResultSet result1 = stat1.executeQuery(sql);

				String toString = "";
				while (result1.next()) {

					calllength = calllength + Integer.parseInt(result1.getString("duration"));
					cmds[4] = Integer.toString(calllength);
					System.out.println(".....get the transfered record....." + cmds[3]);

					toString = "*To*" + result1.getString("dst");
					// cmds[3] = cmds[3] + "*To*" + result1.getString("dst");

					cmds[6] = "Transferred Call From";

					System.out.println(cmds[3]);

				}

				cmds[3] = cmds[3] + toString;

				System.out.println(cmds);

				// ***********************************************************
				if (cmds[0].equals("/var/javalib/sendmailincomingcall.sh")) {
					String[] opts = new String[8];

					for (int i = 0; i < 7; i++) {
						opts[i] = cmds[i];

					}
					opts[7] = uid;

					String NoteCMD = "SENDINCOMINGWITHLOGID";

					byte[] data = null;

					File f = new File("/var/spool/asterisk/monitor/" + fname + ".WAV");
					// ******************************
					long fsize = f.length();
					data = new byte[(int) fsize];

					try {
						FileInputStream fin = new FileInputStream(f);

						fin.read(data);

						fin.close();

					} catch (Exception e) {

					}
					String[] res = null;
					try {
						 res = service.generalCommand(NoteCMD, opts, data);
					} catch (Exception e) {

					}

					String logid = "none";

					if (res!= null && res.length > 0)
						logid = res[0];
					cmds[5] = cmds[5] + "*LogID*" + logid;
					cmds[0] = "/var/javalib/sendmailincomingcallLogId.sh";

					try {

						Process proc = Runtime.getRuntime().exec(cmds);

						if (proc != null) {
							proc.waitFor();
						}

					} catch (Exception e) {
						System.out.println(e);
					}

				} else {

					try {

						Process proc = Runtime.getRuntime().exec(cmds);

						if (proc != null) {
							proc.waitFor();
						}

					} catch (Exception e) {
						System.out.println(e);
					}

				}

				// *****************************************************************

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DbConnection.FreeCon(con);

		}

	}

	public String GetChannelVar(String chann, String varname) {

		String value = "";
		GetVarAction action = new GetVarAction(chann, varname);

		try {
			ManagerResponse response = AstConnection.managerConnection.sendAction(action);
			value = response.getAttribute("Value");

		} catch (Exception e) {
		}
		;

		action = null;

		System.out.println(
				"**************************************##########################################***************************get channel variable: chann "
						+ chann + "  variable " + varname + "  value " + value);

		if (value == null)
			value = "";
		if (value.equals("(null)"))
			value = "";

		return value;

	}

	private void trySleep(long millsecs) {
		try {
			Thread.sleep(millsecs);

		} catch (Exception e) {

		}

	}

	private String[] getDbDocid(Connection con) {
		String[] res = new String[2];
		res[0] = "";
		res[1] = "";

		String sqlstr = "select dbname,docid from webcallback where " + " uniqueid=\'" + uid + "\'";

		try {

			Statement stat = con.createStatement();
			ResultSet result = stat.executeQuery(sqlstr);

			if (result.next()) {

				res[0] = result.getString("dbname");
				res[1] = result.getString("docid");

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return res;
	}

}
