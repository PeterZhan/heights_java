package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendResponseID extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");
		String namecalled = getVariable("NAMECALLED");
		String numcalled = getVariable("REMOTEEXTEN");
		String respdocid = getVariable("RESPDOCID");
		
		
		String fname = getVariable("LEGALRECALL") + ".WAV";
		
		String detectType = getVariable("typeofdetect");
		
		
	//	String exten = getVariable("EXTEN");
		
	//	if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);
		
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 NotesWSClient wsclient = new NotesWSClient(url);
	 	//	 String[] res = wsclient.generalCommand(cmd, options, data);
	    //	 AstPortalProxy service = new AstPortalProxy();  
	 	//	 service.setEndpoint(url);
	 		 
	 		 String[] opt = new String[6];
	 		 
	 		 opt[0] = docid;
	 		 opt[1] = namecalled;
	 		 opt[2] = numcalled;
	 		 opt[3] = respdocid;
	 		 opt[4] = fname;
	 		 opt[5] = detectType;
	 		 
	 		 
	 		String[] res = null;
	 		 System.out.println("Starting get");
	 		 
	 		 
	 	//	File f = new File("/var/spool/asterisk/monitor/" + fname);
	    //	boolean fe = f.exists();
            
	    	byte[] data = new byte[1];
	    	
	   /* 	if (fe)
	    	{
	    	long fsize = f.length();
	    	data = new byte[(int)fsize];
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);
	    	
	    	fin.close();	
	    	
	    	} else*/
	    		fname = "none";
	 		 
	 		 
	 		 
	 		 
	 		 
	    	res = wsclient.generalCommand("SENDRESPDOCID", opt, data); 
	 		 
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
