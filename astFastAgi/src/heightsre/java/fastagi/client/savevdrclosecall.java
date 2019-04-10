package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savevdrclosecall extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
       String podoc = getVariable("PODOC");
       String ifWarrty = getVariable("ifwarrty");
		
		
		
		String rec = getVariable("vscfrecording");  // get the channel unique id
		 
		try{ 
		    String fname =  rec + ".WAV"; 
		    
		    File f = new File("/var/spool/asterisk/monitor/" + fname);
		
		    long fsize = f.length();
		   	byte[] data = new byte[(int)fsize];
		   	
		    FileInputStream fin = new FileInputStream(f);
		   	
		    fin.read(data);
		   	
		   	fin.close();
		   	
		    String[] opt = new String[3];
		     
		     // these are the options for the service command
	       opt[0] = podoc;
	       opt[1] = fname;
	       opt[2] = ifWarrty;
	       
	       String cmd = "SAVEVENDORPOCLOSECALL"; 
		   	
	       String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
		    
		   NotesWSClient wsclient = new NotesWSClient(url);
			
		   String[] res = wsclient.generalCommand(cmd, opt, data);
		
		
		} 	catch(Exception e)
		{
	 		setVariable("vartest", e.toString());
		    System.out.println(e);	 
		}finally
		{
		  	// hangup();
		}
		
		
		
		
		
		
		
		

	}

}
