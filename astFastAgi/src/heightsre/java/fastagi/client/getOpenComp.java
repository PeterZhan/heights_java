package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getOpenComp extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String tendoc = getVariable("TENDOC");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = tendoc;
    
    
    
        String cmd = "GETOPENCOMPLAIN"; 
	   	
        String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String compNum = res[0];
	   
	   setVariable("compnum", compNum);
	   
	   String compPrompt = "You have " + compNum + " open complaints in system";
	   
	   int iCompNum = Integer.parseInt(compNum);
	   
	   for (int i=1; i<=iCompNum; i++)
	   {
		   setVariable("comp" + i, res[i]);
		   compPrompt = compPrompt + ",Press " + i + " for complaint number " + res[i];
	   }
	   
	   
	    String unid = getVariable("UNIQUEID");
		String fname = "/tmp/allcomp" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, compPrompt);   // write the tip1 to the tmp file.
   	 
   	    ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("allopencomp", fname);
	   
	   
	   
	   
		
		
		
		
		

	}
	
	private void ConverToWav(String w, String t)
	   {
		   
		   String[] cmds = new String[3];
		   cmds[0] = "/var/javalib/swift.sh";
		   cmds[1] = w;
		   cmds[2] = t;
		   
		   runshellcmds(cmds);
 
		   
		  
		   String[] rmcmds = new String[2];
		   
		   rmcmds[0] = "rm";
		   rmcmds[1] = t;
		   
		   runshellcmds(rmcmds);
		   // /var/javalib/swift.sh w t
		   
		       
		   
	   }
	   
	
	private void runshellcmds(String[] cmds)
	   {
  Process pid = null;
  
  try{
  
     pid = Runtime.getRuntime().exec(cmds);
     
     if (pid != null)
     {
   	  pid.waitFor();
     }
     
     
     
  }catch(Exception e)
  {
	   
	   System.out.println(e);
	   
  } 
		   
		   
		   
		   
	   }
	
	private void WriteToFile(String f, String c)
	{
		try{
		  FileWriter resultFile = new FileWriter(f);
		  PrintWriter myFile = new PrintWriter(resultFile);
		
		  myFile.println(c);
		  resultFile.close(); 
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

}
