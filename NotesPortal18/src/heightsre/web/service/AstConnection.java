package heightsre.web.service;

import java.io.IOException;
import java.util.*;

import org.asteriskjava.manager.event.*;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.action.*;
import org.asteriskjava.manager.response.ManagerResponse;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.response.CommandResponse;

// this class is for connection to asterisk by asterisk-java
public class AstConnection implements ManagerEventListener {

	// asterisk manager connection
	private ManagerConnection managerConnection;

	// public static SyncEntry SynData = new SyncEntry();

	// // run asterisk cli command
	// public static List<String> exeCommand(String cmd)
	// {
	// try{
	// CommandAction commandAction = new CommandAction(cmd); // command class
	// CommandResponse response = (CommandResponse)
	// managerConnection.sendAction(commandAction); // send the cli command and
	// wait for the response
	// /* for (String line : response.getResult())
	// {
	// System.out.println(line);
	// }
	// */
	// return response.getResult();
	// }catch(Exception e)
	// {
	// return null;
	// }
	//
	// }
	//
	// // this method is for getting channel variable from asterisk
	// public static String getVariable(String chann, String var)
	// {
	// String value = "";
	// try{
	// GetVarAction getVarAction = new GetVarAction(chann, var); // getting
	// variable action
	// ManagerResponse response = managerConnection.sendAction(getVarAction); //
	// send the action to asterisk
	// value = response.getAttribute("Value"); // get the variable value
	// }catch(Exception e)
	// {
	//
	// }
	//
	// return value; // return the value
	//
	// }
	//
	//
	// public static String getGlVariable(String var)
	// {
	// String value = "";
	// try{
	// GetVarAction getVarAction = new GetVarAction(var); // getting variable
	// action
	// ManagerResponse response = managerConnection.sendAction(getVarAction); //
	// send the action to asterisk
	// value = response.getAttribute("Value"); // get the variable value
	// }catch(Exception e)
	// {
	//
	// }
	//
	// return value; // return the value
	//
	// }
	//
	// // this method is for setting the channel variable value
	// public static void setVariable(String chann, String var, String varvalue)
	// {
	//
	// try{
	// SetVarAction setVarAction = new SetVarAction(chann, var, varvalue); //
	// set variable action
	// ManagerResponse response = managerConnection.sendAction(setVarAction); //
	// send the action to asterisk
	//
	// }catch(Exception e)
	// {
	//
	// }
	//
	//
	//
	// }
	//
	//
	// public static void setGlVariable(String var, String varvalue)
	// {
	//
	// try{
	// SetVarAction setVarAction = new SetVarAction(var, varvalue); // set
	// variable action
	// ManagerResponse response = managerConnection.sendAction(setVarAction); //
	// send the action to asterisk
	//
	// }catch(Exception e)
	// {
	//
	// }
	//
	//
	//
	// }

//	public AstConnection() {
//		try {
//			SetConnection();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AuthenticationFailedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	public ManagerResponse sendAction(ManagerAction action) throws IOException, TimeoutException, AuthenticationFailedException
	{
		this.SetConnection();
		ManagerResponse resp = this.managerConnection.sendAction(action);
		this.unSetConnection();
		
		return resp;
		
		
	}

	// set the connection to asterisk
	private void SetConnection() throws IOException, TimeoutException,
			AuthenticationFailedException {

	//	Config cfg = new Config();

		// connection factory
		ManagerConnectionFactory factory = new ManagerConnectionFactory("localhost", "admin", "amp111");
		//		cfg.asthost, cfg.username, cfg.password);

		// get a connection
		this.managerConnection = factory.createManagerConnection();

		// this.managerConnection.registerUserEventClass(NEWSESSEvent.class);

		// and listener for receiving events from asterisk
	//	this.managerConnection.addEventListener(this);

		// login
		this.managerConnection.login();

	}

	private void unSetConnection() {
		this.managerConnection.logoff();

	}

	public void onManagerEvent(ManagerEvent arg0) {

		// if (arg0 instanceof BridgeEvent)
		// {
		// BridgeEvent be = (BridgeEvent) arg0;
		// new BridgeEventThread(be).start();
		//
		// }
		// if (arg0 instanceof NEWSESSEvent)
		// {
		//
		// NEWSESSEvent newsess = (NEWSESSEvent) arg0;
		// new newSessEventThread(newsess).start();
		// System.out.print(newsess);
		//
		// }
		//
		//
		//
		// if (arg0 instanceof MeetMeLeaveEvent)
		// {
		// MeetMeLeaveEvent mle = (MeetMeLeaveEvent) arg0;
		// new MeetMeLeaveEventThread(mle).start();
		//
		// }
		//
		// if (arg0 instanceof MeetMeJoinEvent)
		// {
		// MeetMeJoinEvent mje = (MeetMeJoinEvent) arg0;
		// new MeetMeJoinEventThread(mje).start();
		//
		// }
		//
		//
		//
		// if (arg0 instanceof ExtensionStatusEvent)
		// {
		//
		// ExtensionStatusEvent ese = (ExtensionStatusEvent) arg0;
		// new ExtenStatusThread(ese).start();
		//
		//
		// }
		//
		//
		//
		// if (arg0 instanceof NewStateEvent)
		// {
		// try{
		// String chann = ((NewStateEvent) arg0).getChannel();
		// if (chann.startsWith(heightsReMain.cfg.astChann))
		// {
		// new DtmfThread(chann).start();
		//
		//
		// // new ChannStateEventThread((NewStateEvent) arg0).start();
		// }
		//
		// }catch(Exception e)
		// {
		//
		// }
		//
		//
		//
		// }
		//
		//
		//
		// if (arg0 instanceof NewChannelEvent)
		// {
		// try{
		// String chann = ((NewChannelEvent) arg0).getChannel();
		//
		// if (chann == null) return;
		//
		//
		//
		//
		//
		//
		//
		// if (chann.startsWith(heightsReMain.cfg.astChann))
		// {
		//
		//
		// // new NewChannelThread((NewChannelEvent) arg0).start();
		// /* channelInfo info = new channelInfo();
		// info.chann = chann;
		// info.unid = ((NewChannelEvent) arg0).getUniqueId();
		// info.startdate = ((NewChannelEvent) arg0).getDateReceived();
		// info.startDial = info.startdate;
		//
		// SynData.putin(info.unid, info);
		//
		// */
		//
		// }else
		// {
		// new NewLocalChannelThread((NewChannelEvent) arg0).start();
		// }
		//
		// }catch(Exception e)
		// {
		//
		// }
		//
		//
		// }
		//
		//
		// if (arg0 instanceof DialEvent)
		// {
		// try{
		// DialEvent de = (DialEvent) arg0;
		// String dstchann = de.getDestination();
		// String srcchann = de.getChannel();
		//
		//
		//
		//
		//
		// System.out.println(de);
		//
		// System.out.println(de.getUniqueId() + ":" + de.getSrcUniqueId());
		//
		// if (dstchann.startsWith(heightsReMain.cfg.astChann))
		// {
		//
		//
		// if (!srcchann.startsWith(heightsReMain.cfg.astChann))
		// (new ThrSetVar(srcchann, "__DYNAMIC_FEATURES",
		// "legalfeature")).start();
		// // SynData.updateStartDialvalue(de.getUniqueId(),
		// de.getDateReceived());
		//
		//
		// if (srcchann.startsWith(heightsReMain.cfg.astChann))
		// {
		// // (new ThrSetVar(srcchann, "CDR(accountcode)", "IN")).start();
		// // (new ThrSetVar(srcchann, "__DYNAMIC_FEATURES",
		// "legalfeature")).start();
		//
		// }
		//
		//
		// }
		//
		// /// for process dial event
		// String callType = "";
		// String phonenumber = "";
		// String empExt = "";
		// if ((srcchann.startsWith(heightsReMain.cfg.astChann)) &&
		// !dstchann.startsWith(heightsReMain.cfg.astChann))
		// {
		// callType = "In";
		// phonenumber = de.getCallerIdNum();
		//
		// empExt = getExtenFromChann(dstchann);
		//
		// }
		//
		// if ((!srcchann.startsWith(heightsReMain.cfg.astChann)) &&
		// dstchann.startsWith(heightsReMain.cfg.astChann))
		// {
		// callType = "Out";
		//
		// empExt = getExtenFromChann(srcchann);
		//
		// //phonenumber = getVariable(dstchann, "CALLERID(num)");
		//
		// }
		//
		// System.out.println(callType + ":" + empExt + ":" + phonenumber);
		//
		//
		//
		// if (callType.equals("") || empExt.equals(""))// ,,
		// phonenumber.equals(""))
		// return;
		//
		// if (empExt.length() != 3) return;
		//
		// // if (phonenumber.length() < 10) return;
		//
		// if (!MyJMathLib.isNumeric(empExt)) return;
		//
		// // if (!MyJMathLib.isNumeric(phonenumber)) return;
		//
		// (new ProcessDial(de, srcchann, dstchann)).start();
		//
		// //**********************************************************
		//
		//
		// }catch(Exception e)
		// {
		//
		// }
		//
		// }
		//
		//
		//
		//
		// if (arg0 instanceof HangupEvent)
		// {
		//
		//
		// try{
		// HangupEvent he = (HangupEvent) arg0;
		//
		//
		//
		// String chann = he.getChannel();
		//
		// if
		// (chann.startsWith(heightsReMain.cfg.astChann)||chann.startsWith("Transfered/"
		// + heightsReMain.cfg.astChann))
		// {
		// System.out.println(he);
		// new InEmailThread(he).start();
		//
		//
		//
		// }else
		// {
		// new OutEmailThread(he).start();
		//
		// }
		//
		//
		//
		//
		//
		//
		//
		// }catch(Exception e)
		// {
		//
		// }
		//
		//
		//
		// }
		//
		// if (arg0 instanceof MessageWaitingEvent)
		// {
		// System.out.println("**********************************************Get Message Waiting Event*********************");
		//
		// try{
		// MessageWaitingEvent mwe = (MessageWaitingEvent) arg0;
		// System.out.println(mwe);
		// // String mailbox = mwe.getMailbox();
		// // int newCount = mwe.getNew();
		// System.out.println("**********************************************Start the Message Waiting Event Thread*********************");
		// new vmNewMessage(mwe).start();
		// //new vmASRThread(mailbox).start();
		//
		//
		//
		//
		// }catch(Exception e)
		// {
		// System.out.println(e);
		// }
		//
		//
		//
		// }
		//

	}

	private String getExtenFromChann(String chann) {
		String res = "";

		int p1 = chann.indexOf('/');
		int p2 = chann.indexOf('-');

		if (p2 > p1 + 1)
			res = chann.substring(p1 + 1, p2);

		return res;

	}

}
