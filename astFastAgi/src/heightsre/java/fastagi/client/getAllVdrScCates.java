package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllVdrScCates extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String cmd = "GETALLVDRJOBCATES";
		String[] opts = new String[1];
		opts[0] = getVariable("BlgCode");
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String catNum = res[0];
		int iCatNum = Integer.parseInt(catNum);
		
		setVariable("catnum", catNum);
		
		String ctPrompt = "";
		
		for (int i=1; i<=iCatNum; i++)
		{
			setVariable("vdrcat" + i, res[i]);
			
			ctPrompt = ctPrompt + "Press " + i + " for " + res[i] + ",";
			
		}
		
		String unid = getVariable("UNIQUEID");
		String fname = "/tmp/allcat" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, ctPrompt);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("pJobMainCates", fname);	
		
		
		
		
		
		
		

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
