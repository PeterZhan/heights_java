package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendrfprecord extends BaseAgiScript {

	@Override
	
	// this agi is for sending rfp dailly call recording
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
         String docs = getVariable("MULTIDOCS");  // get the multi documents
         
         String unid = getVariable("UNIQUEID");  // get the channel unique id
		
		 String typeofdetect = getVariable("typeofdetect");  // get the response type
		 
		 String rfpcallname = getVariable("RFPCALL");  // get the recording file name
		
		// web service url
		 String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
		  
		 
		 // if not answer or ansered but hangup
		 if (typeofdetect.equals("NOBODY") || typeofdetect.equals("ANSWEREDCALL"))
		 {
			 
			 try{
				 NotesWSClient wsclient = new NotesWSClient(url);
		 	//	 String[] res = wsclient.generalCommand(cmd, options, data);
		//	 AstPortalProxy service = new AstPortalProxy();  
	 	//	 service.setEndpoint(url);  // set the web sercie url
	 		 
	 		 String cmd = "RFPMISSREPORT";  // web service command
	 		 
	 		 String[] opt = new String[3];// the command options
	 		 opt[0] = docs;
	 		 
	 		 
	 		String fname = rfpcallname + ".WAV";
	 		
	 		opt[1] = fname;
	 		
	 		opt[2] = typeofdetect;
			
	 		
	 		// read the recording data into bytes array
	 		File f = new File("/var/spool/asterisk/monitor/" + fname);
	 		
	 		long fsize = f.length();
	    	byte[] data = new byte[(int)fsize];
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);// read into bytes array
	    	
	    	fin.close();	
	    	
	    	
	    	// send the web service command
	    	String[] res = wsclient.generalCommand(cmd, opt, data);
	    	
	    	setVariable("MISSEDREP", res[0]); // set the channel variable as missed report docuemnt id
	    	
			 }
			 
	    	catch(Exception e)
			 {
	    		
	    		setVariable("vartest", e.toString());
			    System.out.println(e);	 
			 } 
			 
			 
			 
		 }
		 
		 
		
		

	}

}
