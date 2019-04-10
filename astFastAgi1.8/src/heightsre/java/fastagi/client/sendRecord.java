package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendRecord extends BaseAgiScript {

	@Override
	
	// this agi is for sending legal recording to notes database
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		
		String acdocid = getVariable("DOCID");  // the document id
		String namecalled = getVariable("NAMECALLED");// the tenant name
		String numcalled = getVariable("REMOTEEXTEN"); // the tenant number
		String persontype = getVariable("typeofdetect");  // if answered, left message or not
		
		String fname = getVariable("LEGALRECALL") + ".WAV";  // the recording filename
		
		// the web service url
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		 String[] res = null;
		 
		 String cmd = "SENDLEGALRECORD"; // the web service command
	     try{
	    	
	    	 NotesWSClient wsclient = new NotesWSClient(url);
	 		// String[] res = wsclient.generalCommand(cmd, options, data);
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);  // set the web service url
	 		 
	 		 String[] opt = new String[5];
	 		 
	 		 opt[0] = acdocid;  // document id
	 		 opt[1] = namecalled;  // tenant name
	 		 opt[2] = numcalled;  // tanant phone number
	 		 
	 		 opt[3] = persontype;  // the type of response
	 		 opt[4] = fname;  // the recording file name
	 		 System.out.println("Starting get");
	 		 
	 		 
	 		//read the recording data into a byte array 
	 		 File f = new File("/var/spool/asterisk/monitor/" + fname);
		    	boolean fe = f.exists();
	            
		    //	if (fe)
		   // 	{
		    	long fsize = f.length();
		    	byte[] data = new byte[(int)fsize];
		    	
		    	FileInputStream fin = new FileInputStream(f);
		    	
		    	fin.read(data);  // get the byte array
		    	
		    	fin.close();	
		    	 res = wsclient.generalCommand(cmd, opt, data);// send the web service command
		  //  	} else
		   // 		 res = service.generalCommand(cmd, opt);
	 		 
	 		 
	 		 
	 		 
	 		
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 setVariable("testval", res[0]);
	 		 
	 	//	 streamFile("thank-you-cooperation");
	 	//	 System.out.println(res[0]);
	 		 
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	// hangup();
		 }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub

	}

}
