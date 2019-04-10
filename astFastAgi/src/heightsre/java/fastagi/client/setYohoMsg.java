package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setYohoMsg extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		
        String uid = getVariable("UNIQUEID");
		
        String fname = "yoho" + uid + ".WAV";
        
        String callername = getVariable("CALLERID(name)");
        String callernum = getVariable("CALLERID(num)");
        
      		
		String[] opts = new String[3];
		opts[0] = callername;
		opts[1] = callernum;
		opts[2] = fname;
		
		String cmd = "YOHOMSGLEFT";
		
		File f = new File("/var/spool/asterisk/monitor/" + fname);
 		
 		long fsize = f.length();
    	byte[] data = new byte[(int)fsize];
    	
    	try{
    	FileInputStream fin = new FileInputStream(f);
    	
    	fin.read(data);
    	
    	fin.close();	
		
    	}catch (Exception e)
    	{
    		
    	}
		
		
		String url = "http://www.heightsre.com/Examples/Test/YohoPage.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		setVariable("msgstatus", "SAVED");

	}

}
