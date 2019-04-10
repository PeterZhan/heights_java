package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveprospect extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String fname = getVariable("prosrec") + ".WAV";
		
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
		
  	   
  	   
  	    String[] opts = new String[2];
		opts[0] = docid;
		
		opts[1] = fname;
	
		

		String cmd = "SAVEPROSPECT";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		
		

	}

}
