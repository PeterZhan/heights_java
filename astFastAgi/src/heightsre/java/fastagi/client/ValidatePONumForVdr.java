package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidatePONumForVdr extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
        String PONum = getVariable("EXTEN");
		
		if (PONum.endsWith("#"))
			PONum = PONum.substring(0,PONum.length()-1);
		
		
		String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
		   
	     try{
	    	 
	    	 NotesWSClient wsclient = new NotesWSClient(url); // set the web service url
	 		 
	 		 String[] opt = new String[1];
	 		 
	 		
	 		 opt[0] = PONum;
	 		
	 	
	 		 String[] res = wsclient.generalCommand("VALIDATEPONUMFORVDR", opt);
	 		 
	 		 if (res[0].equals("0"))
	 		 {
	 			 setExtension("notfound");
	 			 setPriority("1");
	 			 return;
	 		 }
	 		
	 	//*******************************************	 
	 		String cell = res[1];  // the vendor cell phone number
			String Prompt = res[2];  // the prompt to play
			String docnum = res[3];  // the document number
			
			String docponums = res[8]; // the po numbers
			
			String addponums = res[4];  // the building addresses
			String jtiponums = res[5];  // the job titles
			
			String jnmponums = res[6];  // the job numbers
			
			
			String ifnodate = res[7];
		    
			//****************************
			
			String docs = "";  // set the document id as the channel variable
			String NumVar = "";  // set the document number
			String POVar = "";
			
			
			String DOCArray = "";
			String ARArray = "";
			String JTArray = "";
			String JNArray = "";
			String POArray = "";
			
			
			
			
			
			if (docponums.endsWith(":"))
				docponums = docponums.substring(0, docponums.length()-1);
						
			String[] ponums = docponums.split(":");  // get the po numbers array
			
			for (int i=0; i<ponums.length; i++)
			{
				String podoc = ponums[i];
				int p1 = podoc.indexOf('<');
				String po = ponums[i].substring(0, p1);
				POVar = POVar + po + "\\,";
				
				POArray = POArray + po + ":";  // get the po numbers array
			//	POArray = POArray + ",PN" + (i+1) + "=" + po;
				
				int p2 = podoc.indexOf('>');
				
				String doc = ponums[i].substring(p1+1, p2);
				
				DOCArray = DOCArray + doc + ":";  // get the documents array
				
				docs = docs + doc + "@";
				
			}
			
			
			if (docs.endsWith("@"))
			{
				docs = docs.substring(0, docs.length()-1);
			}
	//**************************************************************		
			
			if (addponums.endsWith(":"))
				addponums = addponums.substring(0, addponums.length()-1);
						
			String[] ARponums = addponums.split(":");
			
			for (int i=0; i<ARponums.length; i++)
			{
				String poAR = ARponums[i];
				int p1 = poAR.indexOf('<');
				String po = poAR.substring(0, p1);
				
				
				int p2 = poAR.indexOf('>');
				
				String ar = poAR.substring(p1+1, p2);
				
				ARArray = ARArray + ar + ":";// get the address array
				
				
				
			}
			
//*******************************************************************			
			if (jtiponums.endsWith(":"))
				jtiponums = jtiponums.substring(0, jtiponums.length()-1);
						
			String[] JTponums = jtiponums.split(">:");
			
			for (int i=0; i<JTponums.length; i++)
			{
				String poJT = JTponums[i];
				int p1 = poJT.indexOf('<');
				String po = poJT.substring(0, p1);
				
				String jt = poJT.substring(p1+1);
				
				int p2 = poJT.indexOf('>');
				
				if (p2 > -1)
				  jt = poJT.substring(p1+1,p2);
				
				JTArray = JTArray + jt + ":";  // get the job title array
				
				
				
			}
//**************************************************************
			
			if (jnmponums.endsWith(":"))
				jnmponums = jnmponums.substring(0, jnmponums.length()-1);
						
			String[] JNponums = jnmponums.split(":");
			
			for (int i=0; i<JNponums.length; i++)
			{
				String poJN = JNponums[i];
				int p1 = poJN.indexOf('<');
				String po = poJN.substring(0, p1);
				
				
				int p2 = poJN.indexOf('>');
				
				String jn = poJN.substring(p1+1, p2);
				
				JNArray= JNArray  + jn + ":";  // get the job number array
				
				
				
			}
			
			
			
			
	
			
			
	//***********************************************************		
	/*		if (POVar.endsWith("\\,"))
			{
				POVar = POVar.substring(0, POVar.length()-2);
			}
		*/	
			
			//*********************************
			long tm = Calendar.getInstance().getTimeInMillis();  // get the mill seconds for temporary wav file name
	    	  
	    	 String tmpTxtFile = "/tmp/" + cell + "-" + tm + ".txt";  // the prompt text file 
	    	
	    	 String tmpWavFile = "/tmp/" + cell + "-" + tm;  // the prompt wav file
	    	 
	    	 
	    	 WriteToFile(tmpTxtFile, Prompt); // write the prompt into the text file
	    	 
	    	 ConverToWav(tmpWavFile +  ".wav", tmpTxtFile); // convert the text to wav file
			
	 		 
	 	//***********************************************	
	    	 setVariable("RFPPrompt", tmpWavFile);
	    	 setVariable("MULTIDOCS", docs);
	    	 setVariable("DOCNUM", docnum);
	    	 setVariable("PONUMS", POVar);
	    	 
             
	    	 setVariable("RFPDOCS", DOCArray);
	    	 setVariable("RFPADDRS", ARArray);
	    	 setVariable("RFPJTITLES", JTArray);
	    	 setVariable("RFPJNUMS", JNArray);
	    	 setVariable("RFPPOS", POArray);
	    	 
    	 
            setVariable("nodate", ifnodate);    	 
	    	 
            setVariable("PONUM", PONum);
	    	 
	 		 /*
	 		 setVariable("DOCID", res[1]);
	 		 
	 		 setVariable("SUPER", res[2]);
	 		 
	 		 setVariable("sext", res[3]);
	 		 
	 		 setVariable("PONUM", PONum);*/
	 		 setExtension("codefound");
	 		 setPriority("1");
	 		 
	 	
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	
		 }
		
		
		
		

	}
	
	// write the sting c into file f
	private void WriteToFile(String f, String c)
	{
		try{
		  FileWriter resultFile = new FileWriter(f);  // get the FileWriter of the file
		  PrintWriter myFile = new PrintWriter(resultFile);  // get the PrintWriter of the file
		
		  myFile.println(c);  // wirte the c into file f
		  resultFile.close(); // close the file stream
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	// convert the text file t to wav file
	private void ConverToWav(String w, String t)
   {
	   
	   String[] cmds = new String[3];
	   cmds[0] = "/var/javalib/swift.sh";// the shell command to convert the text into wav file
	   cmds[1] = w;  // the wav file
	   cmds[2] = t;  // the text file
	   
	   runshellcmds(cmds);  // run the shell command
      
	   
	  
	   String[] rmcmds = new String[2];  // run the shell command to delete the text file
	   
	   rmcmds[0] = "rm";  // the "rm" command
	   rmcmds[1] = t;  // the text file
	   
	   runshellcmds(rmcmds);  // run the command to delete the text file
	   // /var/javalib/swift.sh w t
	   
	       
	   
   }
   
  // run the linux shell command 
	private void runshellcmds(String[] cmds)
   {
       Process pid = null;  // the process id
       
       try{
       
          pid = Runtime.getRuntime().exec(cmds);  // run the shell command
          
          if (pid != null)
          {
        	  pid.waitFor();  // wait for the command is finished
          }
          
          
          
       }catch(Exception e)
       {
    	   
    	   System.out.println(e);
    	   
       } 
	   
	   
	   
	   
   }

}
