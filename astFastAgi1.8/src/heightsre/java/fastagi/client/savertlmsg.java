package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savertlmsg extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
       String docid = getVariable("INQDOC");
	
		
		
		String fname = getVariable("rtlmsg") + ".WAV";	
		
		
		 String[] opts = new String[2];
			opts[0] = docid;
			
			opts[1] = fname;
			
			
		
			
		
			
			byte[] data = null;
			
			
			try{
				Thread.sleep(1000);
			  File f = new File("/var/spool/asterisk/monitor/" + fname);
			//******************************
			   long fsize = f.length();
	    	   data = new byte[(int)fsize];
	    	
	    	   
	    	     FileInputStream fin = new FileInputStream(f);
	    	
	    	     fin.read(data);
	    	
	    	     fin.close();	
			
	    	   }catch (Exception e)
	    	   {
	    		 /// return;
	    	   }
			
			
			
			
			
			//*******************************
			
			String cmd = "SAVEPBRTLCOMM";
			
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/nrentalt.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, opts, data);
		
		    setVariable("TESTVAR", res[0]);
		
		
		
		

	}

}
