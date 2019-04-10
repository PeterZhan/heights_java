package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesupervacmsg extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String code = getVariable("BlgCode");
		String unit = getVariable("curunit");
		
		String reporter = getVariable("REPNAME");
		
		String ifready = getVariable("ifready");
		
		String renternum = getVariable("renternum");
		
		String suggestrent = getVariable("suggestrent");
		
		
        String reclength = getVariable("reclen");
		
		String fname = getVariable("svacrecording") + ".WAV";;
		
				
		
	    String[] opts = new String[8];
		opts[0] = code;
		opts[1] = unit;
		opts[2] = reporter;
		opts[3] = ifready;
		opts[4] = renternum;
		opts[5] = suggestrent;
		opts[6] = fname;
		opts[7] = reclength;
	
		
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
		
		String cmd = "SAVESUPERVACMSG";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
      //  setVariable("sext", res[0]);
		
		
		
		

	}

}
