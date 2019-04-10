package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SendEmail extends BaseAgiScript {

	@Override
	// this agi is for sending email to notes database after click to call
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String local = getVariable("LOCALEXTEN"); // get local phone number
		String remote = getVariable("REMOTEEXTEN");  // get remote phone number
		String docid = getVariable("DOCID");  // get the document id
		String uniqueID = getVariable("UNIQUEID");  // get the channel unique id
		String dbname = getVariable("DBNAME");  // get the database name
		
		String callstart = getVariable("CALLSTART");
		
		String nowtime = getVariable("EPOCH");
		
		int secs = Integer.parseInt(nowtime) - Integer.parseInt(callstart);
		
		
		String dialedtime = "" + secs;//getVariable("CDR(duration)");  // get the dialing time
		
		String reqnum = getVariable("REQNUM");
		
		if (reqnum == null || reqnum.equals(""))
			reqnum = "0";
		
		if (dialedtime == null || dialedtime.equals(""))
			dialedtime = "0";
		
		
		
		
		// if not get the dialed time, then set it to zero.
		if ((dialedtime == null)||(dialedtime.equals(""))) dialedtime = "0";
		
		
		
		int dialedsecs = Integer.parseInt(dialedtime);// to integer
		
		if (dialedsecs >= 2) dialedsecs = dialedsecs -2;  // remove the before dial time
		
		dialedtime = "" + dialedsecs;  // to string
		
	/*	String mailaddress = "hzhang@HeightsRE.com";
		
		String subject = "Asterisk Recording Message:" + docid + ","
		                
		                + local + ","
		                + remote;*/
		
		
		// invoke the linux shell command to send email to notes database
		String cmd = "/var/javalib/sendmail.sh";
		
		boolean isMain = false;
		if (docid.startsWith("@Main@"))
		{
			cmd = "/var/javalib/sendmailMainDoc.sh";
			docid = docid.replace("@Main@", "");
			isMain = true;
		}
		
		
		String[] cmds = null;
		
		if (!dbname.equals("NODATABASE"))
		{
		cmds = new String[8];
		
		cmds[0] = cmd;
		cmds[1] = uniqueID + "peer.WAV";  // the wav recording flle
		cmds[2] = local;
		cmds[3] = remote;
		cmds[4] = docid;
		cmds[5] = dbname;
		cmds[6]= dialedtime;
		cmds[7] = reqnum;
		}
		else
		{
			 String cmdNoDatabase = "/var/javalib/sendmailoutboundcall.sh";
			 cmds = new String[5];
		     cmds[0] = cmdNoDatabase;
			 cmds[1] = uniqueID + "peer.WAV";
			 cmds[2] = local;
			 cmds[3] = remote;
			 cmds[4] = dialedtime;
		}
			     /*  "uuencode /var/spool/asterisk/monitor/" 
			        + uniqueID + ".WAV " + uniqueID + ".WAV " + ", mail  " 
			        +  "  -s \'" 
			        + subject + "\' " 
			        + mailaddress ;*/
		
        try{
	       // run the linux shell command
	        Process proc =  Runtime.getRuntime().exec(cmds);

	        
            if (proc != null)
            {
            	proc.waitFor();  // wait until it finishes
            }


	        
	        
        }catch(Exception e)
        {
        	System.out.println(e);
        }
        
        System.out.println(cmd);
	}

}
