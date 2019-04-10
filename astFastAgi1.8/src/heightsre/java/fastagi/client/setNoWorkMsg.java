package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setNoWorkMsg extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String ReasonOffWork = getVariable("ReasonOffWork");
		String DaysOfNoWork = getVariable("DaysOfNoWork");
		
		if (DaysOfNoWork == null) DaysOfNoWork = "";
		
		String tmstart = getVariable("TIMESTART");
		if (tmstart == null) tmstart = "";
		
		String tmend = getVariable("TIMEEND");
		if (tmend == null) tmend = "";
		
		String uid = getVariable("UNIQUEID");
		
		String docid = getVariable("EMPDOCID");
			
	    String fname = "nowork" + uid + ".WAV";    //getVariable("INCOMINGREC") + ".WAV";
		
		
	    String[] opts = new String[6];
		opts[0] = docid;
		opts[1] = fname;
		opts[2] = ReasonOffWork;
		opts[3] = DaysOfNoWork;
		opts[4] = tmstart;
		opts[5] = tmend;
		String cmd = "SETNOWORKMSG";
		
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
		
		
		String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
	    
	    setVariable("MANEXT", res[0]); 
	    setVariable("ABSDOC", res[1]);
	    setVariable("DOCID", res[1]);
	    
		
		

	}

}
