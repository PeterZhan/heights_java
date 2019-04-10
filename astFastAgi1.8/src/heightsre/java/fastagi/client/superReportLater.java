package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class superReportLater extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
        String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
       
        setVariable("RECSAVED", "LATER");
        
        
        String docid = getVariable("DOCID");
        String recFile = getVariable("SUPERRECALL");
        
        String fname = recFile + ".WAV";
		
		String cmd = "SUPERLATERREP";
		String[] options = new String[2];
		
		options[0] = docid;
		options[1] = fname;
		
		try{
            File f = new File("/var/spool/asterisk/monitor/" + fname);
	    	
	    	long fsize = f.length();
	    	byte[] data = new byte[(int)fsize];
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);
	    	
	    	fin.close();	
			
			
			
			
			
			
			
	    	NotesWSClient wsclient = new NotesWSClient(url);
 		  setVariable("RETVAL1", "STARTING");
 		 
 		 String[] res = wsclient.generalCommand(cmd, options, data);
 		 
 		// setVariable("RETVAL2", res[0]);
 		 
		}
		catch(Exception e)
		{
			
			setVariable("RETERROR", e.toString());
		//	System.out.println(e);
		}
 		

	}

}
