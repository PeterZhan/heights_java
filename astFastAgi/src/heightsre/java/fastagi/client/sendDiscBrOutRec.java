package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.util.Date;

public class sendDiscBrOutRec extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String db = getVariable("db");
		String doc = getVariable("doc");
		String localname = getVariable("localname");
		String local = getVariable("local");
		String remote = getVariable("remote");
		String remotename = getVariable("remotename");
		String dt = getVariable("DIALEDTIME");
		
		if (dt==null || dt.equals(""))
			dt = "0";
		
	/*	Date d = new Date();
		d.setTime(Long.parseLong(dt) * 1000);
		
		dt = d.toString();
		*/
		String rec = getVariable("DISCBRDREC");
		
		String type = getVariable("type");

		
		
		
	
		
		String cmd = "/var/javalib/sendDiscOutRec.sh";
		
		String[] cmds = new String[10];
		
		cmds[0] = cmd;
		cmds[1] = rec + ".WAV";
		cmds[2] = local;
		
		cmds[3] = remote;
		
		cmds[4] = doc;
		cmds[5] = db;
		cmds[6] = dt;
		
		
		cmds[7] = localname;
		cmds[8] = remotename;
		cmds[9] = type;
		
		
			
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
        
		
		
		
		
		

	}

}
