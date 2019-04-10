package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class editpunchtimeconfed extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String tpunch = getVariable("timeforpunch");
		
		String docid = getVariable("DOCID");
		String punchact = getVariable("ACTPUNCH");
		
		
		
		String unid = getVariable("UNIQUEID");
		String fname = "edit" + unid + ".WAV";
		File f = new File("/var/spool/asterisk/monitor/" + fname);
    	
    	long fsize = f.length();
    	byte[] data = new byte[(int)fsize];
    	
    	try{
    	   FileInputStream fin = new FileInputStream(f);
    	
    	   fin.read(data);
    	
    	   fin.close();
    	}catch(Exception e)
    	{
    		
    	}
		
		
		
		
		
		
		String[] opts = new String[4];
		opts[0] = docid;
		opts[1] = punchact;
		opts[2] = tpunch;
		opts[3] = fname;
		
		String cmd = "EDITPUNCHINTIME";
		
		
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		data = null;
		

	}

}
