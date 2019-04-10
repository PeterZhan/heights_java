package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurSpDailyOfOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String i = getVariable("I");
		String docid = getVariable("DOCID" + i);
		
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = docid;
   
        setVariable("DOCID", docid);
   
       String cmd = "GETSPDAILYBYID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String prompts = res[0];
	   
	   
	   long tm = Calendar.getInstance().getTimeInMillis();  // get the millseconds 
 	  
  	 String tmpTxtFile = "/tmp/" + i + "-" + tm + ".txt"; // temporay text file for the prompts 
  	
  	 String tmpWavFile = "/tmp/" + i + "-" + tm;  // the wav file
  	 
  	 
  	 WriteToFile(tmpTxtFile, prompts); // write the prompt to file
  	 
  	 ConverToWav(tmpWavFile +  ".wav", tmpTxtFile); 
	   
	 setVariable("FPROMPT", tmpWavFile);  
	 setVariable("PrompText", prompts);
	   
		
		

	}
	
	
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
