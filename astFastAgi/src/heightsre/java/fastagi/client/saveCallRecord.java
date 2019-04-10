package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveCallRecord extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String dbname = getVariable("DBNAME");
		String docid = getVariable("DOCID");
		String fdname = getVariable("FDNAME");
		
		
		String fname = getVariable("callrec") + ".WAV";	
		
		
		 String[] opts = new String[4];
			opts[0] = dbname;
			
			opts[1] = docid;
			
			opts[2] = fdname;
			
			opts[3] = fname;
		
			
		
			
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
			
			String cmd = "SAVECALLANDRECORD";
			
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, opts, data);
			
			
			
		
		

	}

}
