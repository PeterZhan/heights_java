package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getPassByCodeEmp extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String empCode = getVariable("EXTEN");
		String blgCode = getVariable("BlgCode");
        
        if (empCode.endsWith("#")) 
       	 empCode = empCode.substring(0, empCode.length() -1);
		
		
		String[] opts = new String[1];
		opts[0] = blgCode + " " + empCode;
		
		String cmd = "GETPASSBYEMPBUILDINGCODE";
		
		
       String url = "http://www.heightsre.com/Examples/Test/Empllist.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifHaspass = res[1];
		String punchpass = res[2];
		String empName = res[3];
		String empDoc = res[4];
		String SuperPromptOut = res[5];
	//	String empName = res[4];
		
		
		if(!ifEmp.equals("1"))
		{
		//	setContext("getPassByEmpCode");
			setExtension("i");
			setPriority("1");
		    return;	
		}
		
	//	setVariable("ACTPUNCH", punchact);
		setVariable("CALLERID(name)", empName);
		
		
		String unid = getVariable("UNIQUEID");
		
		String fname = "/tmp/empl" + unid; 
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, SuperPromptOut);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("EmpPromptOut", fname);
		
		
		
		
		
				
		if (ifHaspass.equals("1"))
		{
			setVariable("emppass", punchpass);
			setVariable("DOCID", empDoc);
			
			setContext("emppassword");
			setExtension("s");
			setPriority("1");
			
			return;
		}
		
		
		if (ifHaspass.equals("0"))
		{
			//setContext("super766test");
			setExtension("i");
			setPriority("1");
			return;
		}
		

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
