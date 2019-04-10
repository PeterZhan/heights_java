package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

//this a fast agi service to process access date message
public class AcMsgDone extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		// set the url of notes web service for access date auto call
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		 
		String unid = getVariable("UNIQUEID");  //get the channel uniqueid
		String docid = getVariable("DOCID");   // get the document id
		
		String namecalled = getVariable("NAMECALLED");  // get the callee fullname
		String numcalled = getVariable("REMOTEEXTEN");  // get the callee phone number
		
		String reschDate = getVariable("RESCHDATE");  // get the rescheduled date callee input
		
		String recFile = "resch" + unid + ".WAV";  // get the recording filename
		
		//verbose("agi out put :" + recFile,1);
		
		
		
		String cmd = "ACMSGLEFT";   // the command to legaloff web service
		String[] options = new String[5];
		
		options[0] = docid;  // document id
		options[1] = reschDate;  // rescheduled date
		options[2] = recFile;   //recording file name
		options[3] = namecalled; // the callee full name
		options[4] = numcalled;  // the callee phone
		
		
		
		
		
	    try{
	    	
	    	
	    	// get the real recording file from disk
	    	File f = new File("/var/spool/asterisk/monitor/" + recFile);
	    	
	    	long fsize = f.length();
	    	byte[] data = new byte[(int)fsize];  // allocat enough size for the recording data
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);  //get the data into byte array
	    	
	    	fin.close();
	    	
	    	
	    	NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, options, data);
	    	
	    	 // get service, and set the url
	    /*	 AstPortalProxy service = new AstPortalProxy();  
	 		 service.setEndpoint(url);
	 		 
	 		 // run the command for this function
	 		 String[] res = service.generalCommand(cmd, options, data);
	 		 data = null;*/
	 		 
	 	
	 		
	 		 
	 		 
	     }catch(Exception e)
	     {
	    	 System.out.println(e);
	    	
	     }
		
		
		
		
		

	}

}
