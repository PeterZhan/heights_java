package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendSpExtenRec extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String fname = getVariable("INCOMINGREC") + ".WAV";
		
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		 
		
			 
		try{
		// AstPortalProxy service = new AstPortalProxy();  
	 	// service.setEndpoint(url);
	 		 
	 	 String cmd = "SPECIALEXTENRECORDING";
	 	 
	 	 String[] opt = new String[2];
	 	 opt[0] = docid;
 		
 		 opt[1] = fname;
 		
 		
		
 		File f = new File("/var/spool/asterisk/monitor/" + fname);
 		
 		long fsize = f.length();
    	byte[] data = new byte[(int)fsize];
    	FileInputStream fin = new FileInputStream(f);
    	
    	fin.read(data);
    	
    	fin.close();
    	
    	NotesWSClient wsclient = new NotesWSClient(url);

		String[] res = wsclient.generalCommand(cmd, opt, data);
    	
    //	String[] res = service.generalCommand(cmd, opt, data);
    	
		}
    	catch(Exception e)
		 {
   		
   		    setVariable("vartest", e.toString());
		    System.out.println(e);	 
		 } 
		
		

	}

}
