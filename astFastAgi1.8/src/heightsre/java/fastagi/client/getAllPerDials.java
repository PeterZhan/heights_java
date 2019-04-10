package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllPerDials extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String docid = getVariable("DOCID");
		
		
		String[] opts = new String[1];
		opts[0] = docid;
	//	opts[1] = spdial;
		
        String cmd = "GETPERSONALDIALS";
		
		
        String url = "http://www.heightsre.com/Examples/Test/TrackVdr.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String alldials = res[0];
	//	String phonenum = res[1];
		
	   
		if (alldials.equals(""))
		{
	      setVariable("NOPERDIALS", "yes");
	      alldials = "You have not set your personal speed dial yet";
		}
		else
		{
			setVariable("NOPERDIALS", "no");
			
		}
			
		String unid = getVariable("UNIQUEID");
		String fname = "/tmp/allpd" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, alldials);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("allperspeeddials", fname);	
		
		
		
		

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
