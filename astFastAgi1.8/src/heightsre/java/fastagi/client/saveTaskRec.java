package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveTaskRec extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String extname = getVariable("EXTNAME");
		String empname = getVariable("EMPNAME");
		String calldb = getVariable("CALLDB");
		String calldoc = getVariable("CALLDOC");
		
		
		
		
		String rec = getVariable("taskrecord");  // get the channel unique id
		 
	try{ 
	    String fname =  rec + ".WAV"; 
	    
	    File f = new File("/var/spool/asterisk/monitor/" + fname);
	
	    long fsize = f.length();
	   	byte[] data = new byte[(int)fsize];
	   	
	    FileInputStream fin = new FileInputStream(f);
	   	
	    fin.read(data);
	   	
	   	fin.close();
	   	
	    String[] opt = new String[5];
	     
	     // these are the options for the service command
       opt[0] = extname;
       opt[1] = empname;
       opt[2] = calldb;
       opt[3] = calldoc;
     
       opt[4] = fname;
       
       
       String cmd = "SAVETASKREC"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt, data);	
	   	
	  
	   	
	}
	   	catch(Exception e)
		{
	 		setVariable("vartest", e.toString());
		    System.out.println(e);	 
		}finally
		{
		  	// hangup();
		}

	}
		
		
		
		
		

	

}
