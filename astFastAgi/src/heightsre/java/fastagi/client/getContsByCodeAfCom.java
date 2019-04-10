package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getContsByCodeAfCom extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String comCode = getVariable("comcode");
		String conCode = getVariable("CODESINPUT");
		String calleetype = getVariable("calleetype");
		
        String cmd = "GETCONTACTSFOR" + calleetype + "AfterCom";
		
		String[] opts = new String[2];
		opts[0] = conCode;
		opts[1] = comCode;
		
		String db = "Certmail.nsf";
			
		
        String url = "http://www.heightsre.com/Examples/Test/" + db + "/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		int namenum = Integer.parseInt(res[0]);
		
		if (namenum == 0)
		{
			setExtension("notfound");
			setPriority("1");
			return;
		}
		
		
		setVariable("contnum", res[0]);
		
		String prompt = "";
		
		for (int i=1;i<=namenum;i++)
		{
			String[] namephone = res[i].split(":");
			setVariable("contname" + i, namephone[0]);
			setVariable("contphone" + i, namephone[1]);
			
			
			if (namephone.length>2)
				setVariable("cellphone" + i, namephone[2]);
			else
				setVariable("cellphone" + i, "");
			
			prompt = prompt + "Press " + i + " to call " + namephone[0] + ",";
			
			
			
			
		}
		
		String unid = getVariable("UNIQUEID");
		String fname = "/tmp/allcont" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, prompt);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("pAllContacts", fname);	
		

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
