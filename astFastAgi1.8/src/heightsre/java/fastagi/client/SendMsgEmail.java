package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SendMsgEmail extends BaseAgiScript {
	
	
	// this agi is for 
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String remote = getVariable("REMOTEEXTEN");
		String docid = getVariable("DOCID");
		String uniqueid = getVariable("UNIQUEID");

		
		
		
		
	/*	String mailaddress = "hzhang@HeightsRE.com";
		
		String subject = "Asterisk Recording Message:" + docid + ","
		                
		                + local + ","
		                + remote;*/
		
		String cmd = "/var/javalib/sendmsgmail.sh";
		
		String[] cmds = new String[4];
		
		cmds[0] = cmd;
		cmds[1] = "msg" + uniqueid + ".WAV";
		cmds[2] = remote;
		cmds[3] = docid;
		
			
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
