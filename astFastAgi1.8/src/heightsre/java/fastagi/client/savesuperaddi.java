package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesuperaddi extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String podoc = getVariable("PODOC");
		
		
		
		
		String rec = getVariable("saddrecording");  // get the channel unique id
		 
	try{ 
	    String fname =  rec + ".WAV"; 
	    
	    File f = new File("/var/spool/asterisk/monitor/" + fname);
	
	    long fsize = f.length();
	   	byte[] data = new byte[(int)fsize];
	   	
	    FileInputStream fin = new FileInputStream(f);
	   	
	    fin.read(data);
	   	
	   	fin.close();
	   	
	    String[] opt = new String[2];
	     
	     // these are the options for the service command
       opt[0] = podoc;
       opt[1] = fname;
   
       
       String cmd = "SUPERSERCALLFORMORE"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
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
