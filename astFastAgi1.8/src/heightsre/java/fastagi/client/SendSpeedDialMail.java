package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SendSpeedDialMail extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
	/*
		String docid = getVariable("DOCID");
		String spdial = getVariable("SPDIALNUM");
		String sphone = getVariable("spphone");
		
		String fname = getVariable("INCOMINGREC") + ".WAV";
		
	try{
		String[] opts = new String[4];
		opts[0] = docid;
		opts[1] = spdial;
		opts[2] = sphone;
		opts[3] = fname;
		
		String cmd = "SENDSPEEDDIAL";
		
		File f = new File("/var/spool/asterisk/monitor/" + fname);
	 
	   	long fsize = f.length();
	   	byte[] data = new byte[(int)fsize];
	   	
	   	FileInputStream fin = new FileInputStream(f);
	   	
	    fin.read(data);
	   	
	   	fin.close();
		
	   	
	    String url = "http://www.heightsre.com/Examples/Test/TrackVdr.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
	   	
		setVariable("VARTEST1", res[0]);
	   	
	   	
		
		
	   	
	}catch(Exception e)
	{
		setVariable("VARTEST0", e.toString());
	}
		
		
		*/
		
		String ext = getVariable("EMPEXT");
		String phone = getVariable("spphone");
		String uniqueID = getVariable("UNIQUEID");
		String dialedtime = getVariable("DIALEDTIME"); 
		
		
		

		 String cmdNoDatabase = "/var/javalib/sendmailoutboundcall.sh";
		 String[] cmds = new String[5];
	     cmds[0] = cmdNoDatabase;
		 cmds[1] = "incoming" + uniqueID + ".WAV";
		 cmds[2] = ext;
		 cmds[3] = phone;
		 cmds[4] = dialedtime;
		 
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
