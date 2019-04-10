package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getSuperInfo extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String cmd = "GETSUPERINFO";
		
		String blgCode = getVariable("BlgCode");
		String unid = getVariable("UNIQUEID");
		
		String fname = "/tmp/super" + unid;
		
		String[] opts = new String[1];
		opts[0] = blgCode;
		
		String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
		
		
        NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		setVariable("DOCID", res[0]);
		
		String SuperPromptOut = res[1];
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, SuperPromptOut);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("SuperPromptOut", fname);
		
		

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
