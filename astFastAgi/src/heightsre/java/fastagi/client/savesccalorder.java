package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesccalorder extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String rec = getVariable("orderrec");  // get the channel unique id
		String docid = getVariable("DOCID");
	//	String reclen = getVariable("reclen");
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
	       opt[0] = docid;
	       opt[1] = fname;
	  /*     opt[1] = unitno;
	       opt[2] = jobtype;
	       opt[3] = commArea;
	       opt[4] = probStart;
	       opt[5] = ifEmerg;
	  
	       opt[6] = fname;
	       opt[7] = vendor;
	       opt[8] = reclen;  */
	        
	       String cmd = "SAVEVDRSCORDER"; 
		   	
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
