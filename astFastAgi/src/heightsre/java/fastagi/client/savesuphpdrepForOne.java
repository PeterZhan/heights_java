package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesuphpdrepForOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String sgnnum = getVariable("sgnnum");
		String resemp = getVariable("RSLT");
		String hpddocid = getVariable("HPDDOCID");
	
		
        String fname = getVariable("suprecording") + ".WAV";
		
		String[] opts = new String[3];
		opts[0] = hpddocid;
		opts[1] = fname;
		opts[2] = sgnnum;
	
		
		
		
		
		
		byte[] data = null;
		
		
		
		File f = new File("/var/spool/asterisk/monitor/" + fname);
		//******************************
		   long fsize = f.length();
    	   data = new byte[(int)fsize];
    	
    	   try{
    	     FileInputStream fin = new FileInputStream(f);
    	
    	     fin.read(data);
    	
    	     fin.close();	
		
    	   }catch (Exception e)
    	   {
    		
    	   }
    	   
    	   
    	   String cmd = "SAVENEWSUPERHPDREP";
   		
   		
   		
   		  
   		String url = "http://www.heightsre.com/Examples/Test/HPDTrack.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts, data);

	}

}
