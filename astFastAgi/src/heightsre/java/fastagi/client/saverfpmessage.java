package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saverfpmessage extends BaseAgiScript {

	@Override
	
	// this agi is for rfp left message and save the mesage into notes
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		// this is the web service url
		 String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
		   
	     try{
	    	 NotesWSClient wsclient = new NotesWSClient(url);
	 		// String[] res = wsclient.generalCommand(cmd, options, data);
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url); // set the url
		     String curdoc = getVariable("CURDOC");  // get current document id
		     String wktoday = getVariable("WKTODAY"); // if work today
		     String wknum = getVariable("WKNUMBER"); // the worker number
		     String wktime = getVariable("WKTIME"); // start working time
		     
		     String recstarttime = getVariable("recstarttime"); // re-scheduleed time
		     String recendtime = getVariable("EPOCH"); // get the time now
		     
		     int reclength = Integer.parseInt(recendtime) 
		                       - Integer.parseInt(recstarttime);  // calculate the recording length
		    
		     
		     if (reclength < 15)
		     {
		    	 streamString("shormsg", "The messages you just left is too short, less than 15 seconds, please try again");
		    	 setExtension("s");
				 setPriority("begin");
				 return;
		     
		     }
		    	 
		     
		     
		     
		     setVariable("vartest", wknum);
		     if (wknum == null) wknum = "";  // if null, set it to ""
		     
		     setVariable("vartest", wktime);
		     if (wktime == null) wktime = "";
		 
		     String unid = getVariable("UNIQUEID");  // get the channel unique id
		 
		 
		     String fname = "rfprep" + unid + ".WAV"; // the recording file name
		
		     String[] opt = new String[6];
	     
		     // these are the options for the service command
	        opt[0] = curdoc;
	        opt[1] = wktoday;
	        opt[2] = wknum;
	        opt[3] = wktime;
	        opt[4] = fname;
	        opt[5] = Integer.toString(reclength);
	       
	   
	     
	        String cmd = "RFPLEFTMESSAGE";  // this is the command
	     
	       String[] res = null;
	     
	     // the recording file
	       File f = new File("/var/spool/asterisk/monitor/" + fname);
   	  //     boolean fe = f.exists();
       
 //  	if (fe)
  // 	{
	      // read the file data into data byte array 
   	      long fsize = f.length();
   	      byte[] data = new byte[(int)fsize];
   	
   	      FileInputStream fin = new FileInputStream(f);
   	
   	      fin.read(data);
   	
   	      fin.close();
   	      
   	      // send the web service command
   	      res = wsclient.generalCommand(cmd, opt, data);
   	     
   	      
   	      // set the channel variable
   	      setVariable("typeofdetect", "SAVEDMESSAGE");
   	      
   	   // some jobs   
   	  /*    String curpo = getVariable("CURPO");
   	      
   	      setVariable("PO" + curpo, "");
   	      
   	      String docnum = getVariable("DOCNUM");
   	      
   	      int dn = Integer.parseInt(docnum);
   	      
   	      dn = dn - 1;
   	      
   	      docnum = Integer.toString(dn);
   	      
   	      setVariable("DOCNUM", docnum);
   	      
   	      String ponums = getVariable("PONUMS");
   	      
   	      ponums = ponums.replace(curpo + ",", "");
   	      
   	      setVariable("PONUMS", ponums);
   	      
   	      */
   	      
   	      // play the prompt
   	      streamString("saveMsg", "The messages you just left have been saved");
   	      
   	      
	 
	    } 
	 	catch(Exception e)
		{
	 		setVariable("vartest", e.toString());
		    System.out.println(e);	 
		}finally
		{
		  	// hangup();
		}
   	 
   	 

	}
	
	// this private method is for playing a string 
	private void streamString(String pre, String prompt)throws AgiException
	{
		
       String unid = getVariable("UNIQUEID");  // get the channel unique id
       
       
       String tmpwav = "/tmp/" + pre + unid;  // the temporay wav file
		
		String[] cmds = new String[3];  // linux commamd to convert the string to wav file
		cmds[0] = "/var/javalib/swiftStr.sh";
		cmds[1] = tmpwav + ".wav";
		cmds[2] = prompt;
		
		
		
		 try{
		       // run the linux commad
		        Process proc =  Runtime.getRuntime().exec(cmds);

		        
	         if (proc != null)
	         {
	         	proc.waitFor(); // wait until the command finished
	         }


		        
		        
	     }catch(Exception e)
	     {
	     	System.out.println(e);
	     }
		
		
		// play the wav file
		 streamFile(tmpwav);
		 
		 File f = new File(tmpwav + ".wav");
		 
		 f.delete();  // delete the temporay wav file.
		
		
		
		
		
	}
	

}
