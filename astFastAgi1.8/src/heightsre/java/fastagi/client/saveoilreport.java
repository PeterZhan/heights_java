package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveoilreport extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String oilDoc = getVariable("ORDDOC");
		String invDoc = getVariable("INVDOC");
	
		
		String fname = getVariable("oilreport") + ".WAV";;
		
				
		
	    String[] opts = new String[3];
		opts[0] = oilDoc;
		
		opts[1] = fname;
		
		opts[2] = invDoc;
		
	
		
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
		
		String cmd = "SAVEOILREPORT";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/Utility.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
      //  setVariable("sext", res[0]);
		
		
		
		
		
		
		
		
		
		

	}

}
