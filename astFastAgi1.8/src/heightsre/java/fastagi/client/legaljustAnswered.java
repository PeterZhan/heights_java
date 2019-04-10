package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

//This is for legal users answered the daily call but not response
public class legaljustAnswered extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");  // get the document id
		String namecalled = getVariable("NAMECALLED");  // the name of the called
		String numcalled = getVariable("REMOTEEXTEN");  // the phone number called
	//	String respdocid = getVariable("RESPDOCID");
		
		
	//	String fname = getVariable("LEGALRECALL") + ".WAV";
		
	//	String detectType = getVariable("typeofdetect");
		
		
	//	String exten = getVariable("EXTEN");
		
	//	if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);
		
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 	//	 service.setEndpoint(url); // set the web service url
	 		NotesWSClient wsclient = new NotesWSClient(url);
	 	//	 String[] res = wsclient.generalCommand(cmd, options, data);
	 		 
	 		 String[] opt = new String[3];
	 		 
	 		 opt[0] = docid;  // document id
	 		 opt[1] = namecalled; // name called
	 		 opt[2] = numcalled; // number called
	 		// opt[3] = respdocid;
	 		// opt[4] = fname;
	 		// opt[5] = detectType;
	 		 
	 		 
	 		String[] res = null;
	 		 System.out.println("Starting get");
	 		 
	 		 
	 	//	File f = new File("/var/spool/asterisk/monitor/" + fname);
	  //  	boolean fe = f.exists();
            
	    //	byte[] data = new byte[1];
	    	
	   /* 	if (fe)
	    	{
	    	long fsize = f.length();
	    	data = new byte[(int)fsize];
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);
	    	
	    	fin.close();	
	    	
	    	} else*/
	    	//	fname = "none";
	 		 
	 		 
	 		 
	 		 
	 		 // send the web service command
	    	res = wsclient.generalCommand("LEGALJUSTANSWERED", opt); 
	 		 
	 		// String[] res = service.generalCommand("SENDRESPDOCID", opt);
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 
	 		
	 		 System.out.println(res[0]);
	 		 
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	 
		 }
		
		
		
		
		

	}

}
