package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class newSendEmail extends BaseAgiScript {

	@Override
	// this agi is for sending email to notes database after click to call
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String local = getVariable("LOCALEXTEN"); // get local phone number
		String remote = getVariable("REMOTEEXTEN");  // get remote phone number
		String docid = getVariable("DOCID");  // get the document id
		String uniqueID = getVariable("UNIQUEID");  // get the channel unique id
		String dbname = getVariable("DBNAME");  // get the database name
		String dialedtime = getVariable("DIALEDTIME");  // get the dialing time
		
		String isMain = getVariable("ISMAIN");
		String callreport = getVariable("CALLREPORT");
		String field = getVariable("FIELD");
		String company = getVariable("COMPANY");
		String contact = getVariable("CONTACT");
		
		
		if (dialedtime == null || dialedtime.equals(""))
			dialedtime = "0";
		
		
		
		
		// if not get the dialed time, then set it to zero.
		if ((dialedtime == null)||(dialedtime.equals(""))) dialedtime = "0";
		
		
		
		int dialedsecs = Integer.parseInt(dialedtime);// to integer
		
		if (dialedsecs >= 2) dialedsecs = dialedsecs -2;  // remove the before dial time
		
		dialedtime = "" + dialedsecs;  // to string
		
	
		// invoke the linux shell command to send email to notes database
		String cmd = "/var/javalib/newsendmail.sh";
		
		
		
		
		String[] cmds = null;
		
		
		cmds = new String[12];
		
		cmds[0] = cmd;
		cmds[1] = uniqueID + "peer.WAV";  // the wav recording flle
		cmds[2] = local;
		cmds[3] = remote;
		cmds[4] = docid;
		cmds[5] = dbname;
		cmds[6]= dialedtime;
		
		cmds[7] = isMain;
		cmds[8] = callreport;
		cmds[9] = field;
		cmds[10] = company;
		cmds[11]= contact;
		
	
		
		  
		
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
