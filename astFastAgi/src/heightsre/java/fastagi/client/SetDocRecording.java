package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.io.*;


public class SetDocRecording extends BaseAgiScript {

	@Override
	
	// this web service is for send the response message
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		// the web service url
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		 
		String unid = getVariable("UNIQUEID");  // get the channel unique id
		String docid = getVariable("RESPDOCID");  // get reponse document id
		setVariable("TESTVAR", unid);
		
		
		String recFile = "msg" + unid + ".WAV";
		
		//verbose("agi out put :" + recFile,1);
		
		
		
		String cmd = "PAYMENTMSG";  // the web service command
		String[] options = new String[2];
		
		options[0] = docid;  // the response document id
		options[1] = recFile; // the recording file name
		setVariable("TESTVAR", cmd);
		
		
		
		
	    try{
	    	
	    	
	    	// read the recording data into bytes array
	    	File f = new File("/var/spool/asterisk/monitor/" + recFile);
	    	
	    	long fsize = f.length();
	    	byte[] data = new byte[(int)fsize];
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);  // get the data
	    	
	    	fin.close();
	    	
	    	
	    	
	    	// the  java  class for sending web service command
	    	
	    	NotesWSClient wsclient = new NotesWSClient(url);

			String[] res = wsclient.generalCommand(cmd, options, data);
	    	
	    //	 AstPortalProxy service = new AstPortalProxy();  
	 	//	 service.setEndpoint(url);  // set the web service url
	 	//	 String[] res = service.generalCommand(cmd, options, data);  // send the web service command
	 		 data = null;
	 		 
	 		setVariable("TESTVAR", res[0]);   // test the returan value
	 	//	verbose("agi out put :" + res[0], 1);
	 		
	 		
	 		//exec("NoOp", res[0]);
	 		// System.out.println(res);
	 		
	 		 
	 		 
	     }catch(Exception e)
	     {
	    	 System.out.println(e);
	    	// exec("NoOp", e.toString());
	    	 setVariable("TESTVAR", e.toString());  // show the exception string
	    	// verbose("agi out put :" + e.toString(),1);
	     }
		
		
		
		

	}

}
