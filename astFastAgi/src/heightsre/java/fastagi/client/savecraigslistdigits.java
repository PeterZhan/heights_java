package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savecraigslistdigits extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
	
		
		String recogResult = getVariable("DIGITS");
		String fname = getVariable("REC") + ".WAV";	
		
		
		String did = getVariable("DID");
		String callername = getVariable("CALLERID(name)");
		String callernum = getVariable("CALLERID(num)");
		
		
		 String[] opts = new String[5];
			opts[0] = recogResult;
			
			
			opts[1] = fname;
			
			opts[2] = did;
			
			opts[3] = callername;
			
			opts[4] = callernum;
		
			
		
			
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
			
			
			
			
			
			//*******************************
			
			String cmd = "SAVECRAIGSLISTDIGITS";
			
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, opts, data);
			
		
		
		
		

	}

}
