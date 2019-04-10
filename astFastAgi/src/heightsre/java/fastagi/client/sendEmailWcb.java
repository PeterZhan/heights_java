package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendEmailWcb extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String local = getVariable("WBCBEXTEN");
		String remote = getVariable("REMOTEEXTEN");
		String docid = getVariable("docid");
		String uniqueID = getVariable("UNIQUEID");
		String dbname = getVariable("dbname");
		String dialedtime = getVariable("DIALEDTIME");
		
		if ((dialedtime == null)||(dialedtime.equals(""))) dialedtime = "0";
		
		
		
		int dialedsecs = Integer.parseInt(dialedtime);
		
		if (dialedsecs >= 2) dialedsecs = dialedsecs -2;
		
		dialedtime = "" + dialedsecs;
		
	/*	String mailaddress = "hzhang@HeightsRE.com";
		
		String subject = "Asterisk Recording Message:" + docid + ","
		                
		                + local + ","
		                + remote;*/
		
		String cmd = "/var/javalib/sendmailwebcall.sh";
		
		String[] cmds = new String[7];
		
		cmds[0] = cmd;
		cmds[1] = uniqueID + "peer.WAV";
		cmds[2] = remote;
		cmds[3] = local;
		cmds[4] = dialedtime;
		cmds[5] = "*Web Click ID*" + docid + "*Database*" + dbname;
		cmds[6] = "Web Click To Call For Extension";
		
			
			     /*  "uuencode /var/spool/asterisk/monitor/" 
			        + uniqueID + ".WAV " + uniqueID + ".WAV " + ", mail  " 
			        +  "  -s \'" 
			        + subject + "\' " 
			        + mailaddress ;*/
		
        try{
	       
	        Process proc =  Runtime.getRuntime().exec(cmds);

	        
            if (proc != null)
            {
            	proc.waitFor();
            }


	        
	        
        }catch(Exception e)
        {
        	System.out.println(e);
        }
        
        System.out.println(cmd);
	}

}
