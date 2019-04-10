package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savecommtsforpic extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		
	
		
		String fname = getVariable("piccommrec") + ".WAV";;
		
		String reclen = getVariable("reclen");
				
		
	    String[] opts = new String[3];
		opts[0] = docid;
		
		opts[1] = fname;
		
		opts[2] = reclen;
		
		
		
	
		
		byte[] data = null;
		
		
			
		  File f = new File("/var/spool/asterisk/monitor/" + fname);
		//******************************
		   long fsize = f.length();
    	   data = new byte[(int)fsize];
    	
    	   try{
    	     FileInputStream fin = new FileInputStream(f);
    	
    	     fin.read(data);
    	
    	     fin.close();	
		
    	   }catch (Exception e)
    	   {
    		
    	   }
		
		
		
		
		
		//*******************************
		
		String cmd = "SAVECOMMFORPIC";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/MgrInsp.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		

	}

}
