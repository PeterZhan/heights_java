package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendCallByCell extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String local = getVariable("LOCALEXTEN"); // get local phone number
		String remote = getVariable("REMOTEEXTEN");  // get remote phone number
		String docid = getVariable("DOCID");  // get the document id
		String uniqueID = getVariable("UNIQUEID");  // get the channel unique id
		String dbname = getVariable("DBNAME");  // get the database name
		String dialedtime = getVariable("CDR(duration)");  // get the dialing time
		
		
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
		String cmdWithDatase = "/var/javalib/sendmailForCallByCell.sh";
		String cmdNoDatabase = "/var/javalib/sendmailoutboundcall.sh";
		String[] cmds = null;
		
				
		if (!dbname.equals("NODATABASE"))
		{
		  cmds = new String[7];
		
		 cmds[0] = cmdWithDatase;
		 cmds[1] = "cell" + uniqueID + ".WAV";  // the wav recording flle
		 cmds[2] = local;
		 cmds[3] = remote;
		 cmds[4] = docid;
		 cmds[5] = dbname;
		 cmds[6]= dialedtime;
		
		}else
		{
        
		
		 cmds = new String[5];
	     cmds[0] = cmdNoDatabase;
		 cmds[1] = "cell" + uniqueID + ".WAV";
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
        
   //     System.out.println(cmd);
		
		

	}

}
