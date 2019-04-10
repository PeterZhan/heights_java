package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getallspopenpo extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String blgcode = getVariable("BlgCode");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = blgcode;
      
      
      
      String cmd = "GETALLSPOPENPO"; 
	   	
      String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);	
	   
	   int ponum = Integer.parseInt(res[0]);
	   
	   setVariable("POCOUNT", res[0]);
	   
	
	   
	   String poprompt = "You have no open po numbers";
	   
	   
	   if (ponum > 0) 
	   {   
		   poprompt = "You have " + res[0] + " open Superintendent Service Call PO numbers,they are ";
		   for (int i=1; i<=ponum; i++)
		   {
			   setVariable("PO"+i, res[i]);
			   poprompt = poprompt + res[i] + ",";
		   }
	   }
	   
	   
	   
	   
	   
	    String unid = getVariable("UNIQUEID");
		String fname = "/tmp/allpo" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, poprompt);   // write the tip1 to the tmp file.
   	 
   	    ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("allopenpo", fname); 
		
		setVariable("HPDCOUNT", res[res.length-1]);
		

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
