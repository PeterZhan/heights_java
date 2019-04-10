package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getOilInfoInTen extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String bldgCode = getVariable("BlgCode");
		
		String[] opts = new String[1];
		opts[0] = bldgCode;
		
		
		String cmd = "GETOILINFOINTEN";
		
		
       String url = "http://www.heightsre.com/Examples/Test/Utility.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	  String[] res = wsclient.generalCommand(cmd, opts);
		
	  String invnum = res[0];
	  setVariable("oilponum", invnum);
	  
	  int Intinvnum = Integer.parseInt(invnum);
	  
	  if (Intinvnum <= 0)  return;
	  
	  String poid = null;
	  String[] PoIdList = null;
	  String prompt = "";
	  for (int i=1; i<=Intinvnum; i++)
	  {
		  poid = res[i];
		  PoIdList = poid.split(":");
		  
		  setVariable("PO" + i, PoIdList[0]);
		  setVariable("DOC" + i, PoIdList[1]);
		  setVariable("PHONE" + i, PoIdList[2]);
		  
		  prompt = prompt + "Press " + i + " for P O number " + PoIdList[0] + ",";
		  
		  
	  }
	  
	
	//  if (prompt.endsWith(","))
	//	  prompt = prompt.substring(0, prompt.length()-1);
	  
	  
	   String unid = getVariable("UNIQUEID");
		String fname = "/tmp/poten" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, prompt);   // write the tip1 to the tmp file.
  	 
  	    ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("pAllOilInvs", fname);	
	  
	  
	  
	  
	  
	  setExtension("rctinv");
	  setPriority("1");
	  
	  
	  

		
		
		
		
		
		

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
