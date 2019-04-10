package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.Calendar;

public class checkIfPunched extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String empName = getVariable("CALLERID(name)");
		
		String[] opts = new String[1];
		opts[0] = empName;
		
		String cmd = "CHECKIFPUNCHED";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String punchact = res[0];
		String punchTime = res[1];
		String docid = res[2];
		String ifcam = res[3];
		String ifallow = res[4];
		
		setVariable("ACTPUNCH", punchact);
		
		setVariable("PINTIME", punchTime);
		
		setVariable("DOCID", docid);
		
		setVariable("IFCAM", res[3]);
		
		if (ifcam.equals("1"))
		{
			setVariable("STCAM", "please stand in front of camera and");
		}else
		{
			setVariable("STCAM", "");
		}
		
		if (ifallow.equals("0"))
		{
			
			streamString("Forb", "You are not allowed to punch in and out from phone. Thank you.");
			
			setContext("NOPUNCHBUTOFF");
			setExtension("s");
			setPriority("1");
			
			//hangup();
		}
		
		Calendar cl = Calendar.getInstance();
		String nowtime = "" + cl.get(cl.HOUR_OF_DAY) + ":" + cl.get(cl.MINUTE);
		
		setVariable("NOWTIME", nowtime);
		
		

	}
	
	private void streamString(String pre, String prompt)throws AgiException
	{
		
       String unid = getVariable("UNIQUEID");
       
       
       String tmpwav = "/tmp/" + pre + unid;
		
		String[] cmds = new String[3];
		cmds[0] = "/var/javalib/swiftStr.sh";
		cmds[1] = tmpwav + ".wav";
		cmds[2] = prompt;
		
		
		
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
		
		
		
		 streamFile(tmpwav);
		 
		 File f = new File(tmpwav + ".wav");
		 
		 f.delete();
		
		
		
		
		
	}

}
