package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesupcomprep extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
        String fname = getVariable("suprecording") + ".WAV";
		
		String[] opts = new String[2];
		opts[0] = getVariable("COMPDOC");
		opts[1] = fname;
	
		
		
		
		
		
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
    	   
    	   
    	   String cmd = "SAVECOMPSUPERREPORT";
   		
   		
   		
   		  
   		String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		
		
		
		
		
		
		
		
		

	}

}
