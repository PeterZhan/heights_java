package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class endrfpcall extends BaseAgiScript {

	@Override
	
	// this agi is for rfp call, when the call done, it will be run
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String docs = getVariable("MULTIDOCS");  // get the documents
		
		String typeofdetect = getVariable("typeofdetect");  // get the result of response
		// 1,NOBODY  no keys pressed
		// 2,LEFTMESSAGE  message left
		// 3,ANSWEREDCALL pressed some key but no message left
		
		// the url of web service at notes
		 String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
		   
	     try{
	    	 
	    	 // set the web service url
	    	 NotesWSClient wsclient = new NotesWSClient(url);
	 		// String[] res = wsclient.generalCommand(cmd, options, data);
	 		 
	 		 if (typeofdetect.equals("NOBODY"))// if no key pressed
	 		 {
	 		     String[] opt = new String[2];
	 		 
	 		     opt[0] = docs;  // documents needed to be reported by super
	 		     opt[1] = getVariable("MISSEDREP");  // get the document which not be reported
	 		     
	 		     if (opt[1] == null || opt[1].equals(""))
	 		    	 opt[1] = "none";
	 		     
	 		     
	 		
	 		     System.out.println("Starting get");
	 		 
	 		     // send the web service command
	 		     String[] res = wsclient.generalCommand("RFPNOANSWER", opt);
	 		 
	 		     System.out.println("Ending get");
	 		     System.out.println(res[0]);
	 		 
	 		 
	 		// streamFile("thank-you-cooperation");
	 		     //System.out.println(res[0]);
	 		 }
	 		 
	 		if (typeofdetect.equals("LEFTMESSAGE"))  // if super left message
	 		 {
	 		     
	 			 String curdoc = getVariable("CURDOC");  // current document needed to be reprot
	 			 String wktoday = getVariable("WKTODAY");  // if work today
	 			 String wknum = getVariable("WKNUMBER");  // how many workers
	 			 String wktime = getVariable("WKTIME");  // working start time
	 			 
	 			 String unid = getVariable("UNIQUEID");
	 			
	 			 String recstarttime = getVariable("recstarttime"); // recording start time
			     String recendtime = getVariable("EPOCH");  // current time 
			     
			     int reclength = Integer.parseInt(recendtime) 
			                       - Integer.parseInt(recstarttime);  // get the recording length
	 			 
	 			 
	 			 String fname = "rfprep" + unid + ".WAV";  // recording file name
	 			 
	 			
	 			 // if not get the variable, then set it to ""
	 			//setVariable("vartest", wknum);
			     if (wknum == null) wknum = "";
			     
			    // setVariable("vartest", wktime);
			     if (wktime == null) wktime = "";
	 			
	 			 String[] opt = new String[6];
	 		     	 		 
	 		     opt[0] = curdoc;
	 		     opt[1] = wktoday;
	 		     opt[2] = wknum;
	 		     opt[3] = wktime;
	 		     opt[4] = fname;
	 		     opt[5] = Integer.toString(reclength);
	 		   
	 		     
	 		     String cmd = "RFPLEFTMESSAGE";
	 		     
	 		     String[] res = null;
	 		     
	 		   // read the file data from the recording file  
	 		    File f = new File("/var/spool/asterisk/monitor/" + fname);
		    	boolean fe = f.exists();
	            
		  //  	if (fe)
		   // 	{
		    	long fsize = f.length();
		    	byte[] data = new byte[(int)fsize];
		    	
		    	FileInputStream fin = new FileInputStream(f);
		    	
		    	fin.read(data);
		    	
		    	fin.close();	
		    	
		    	
		    	// send the command to notes web service
		    	 res = wsclient.generalCommand(cmd, opt, data);
		 //   	} else
		 //   		 res = service.generalCommand(cmd, opt);
		    	 
		    	// after send, set the variable to "SAVEDMESSAGE"
		    	 setVariable("typeofdetect", "SAVEDMESSAGE");
	 		     
	 		   setVariable("retVar", res[0]);
	 		     
	 		     
	 		     
	 		     
	 		     
	 		
	 		     
	 		 }
	 		
	 		// if answered but hangup
	 		if (typeofdetect.equals("ANSWEREDCALL"))
	 		 {
	 		  
	 				
	 			 String[] opt = new String[2];
	 		 
	 		     opt[0] = docs;
	 		     opt[1] = getVariable("MISSEDREP");  // get the document which not be reported
	 		
	 		  //   System.out.println("Starting get");
	 		 
	 		     // send the service command to notes service
	 		     String[] res = wsclient.generalCommand("RFPANSWERED", opt);
	 		 
	 		  //   setVariable("wsResult", res[0]);
	 		 //    System.out.println("Ending get");
	 		     System.out.println(res[0]);
	 		 
	 		 
	 		// streamFile("thank-you-cooperation");
	 		  //   System.out.println(res[0]);
	 		 }
	 		 
	 		 
	 		 
	 		 
	 		 
	 		 
	     } 
	 	 catch(Exception e)
		 {
	 		//setVariable("vartest", e.toString());
		    System.out.println(e);	 
		 }finally
		 {
		  	// hangup();
		 }
		
		
		
		

	}

}
