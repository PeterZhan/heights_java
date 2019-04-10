package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendAndCallRec extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String ext = getVariable("EMPEXT");
		String phone = getVariable("phonenum");
		String uniqueID = getVariable("UNIQUEID");
		String dialedtime = getVariable("DIALEDTIME"); 
		String docid = getVariable("ANDRDOC");
		
		String phoneext = getVariable("EXT");
		
		String dbname = getVariable("DBNAME");
		String pdocid = getVariable("PDOCID");
		
		
		String startTime = getVariable("STARTTIME");
		String endTime = getVariable("EPOCH");
		
		int iDialerTime = Integer.parseInt(endTime) - Integer.parseInt(startTime);
		
		dialedtime = "" + iDialerTime;
		
		if (phoneext!=null && !phoneext.equals(""))
			phone = phone + "-" + phoneext;
		
		
		if (dialedtime == null || dialedtime.equals(""))
			dialedtime = "0";
		

		 String cmdNoDatabase = "/var/javalib/sendmailandroutcall.sh";
		 String[] cmds = new String[6];
	     cmds[0] = cmdNoDatabase;
		 cmds[1] = "incoming" + uniqueID + ".WAV";
		 cmds[2] = ext;
		 cmds[3] = phone;
		 cmds[4] = dialedtime;
		 cmds[5] = docid;
		 
		 if (!dbname.equals("none") && !pdocid.equals("none"))
		 {
			 cmdNoDatabase = "/var/javalib/sendmailandroutcall2.sh";
			 cmds = new String[7];
		     cmds[0] = cmdNoDatabase;
			 cmds[1] = "incoming" + uniqueID + ".WAV";
			 cmds[2] = ext;
			 cmds[3] = phone;
			 cmds[4] = dialedtime;
			 cmds[5] = pdocid; 
			 cmds[6] = dbname;
			 
			 
			 
			 
			 
			 
		 }
		 
		 
		 
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
		
		
		
		

	}

}
