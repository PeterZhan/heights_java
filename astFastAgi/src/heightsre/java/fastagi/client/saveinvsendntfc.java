package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveinvsendntfc extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String saved = getVariable("INVSAVED");
		
		if (saved != null && saved.equals("TRUE"))
			return;
		
		
		String oilDoc = getVariable("ORDDOC");
		String invDoc = getVariable("INVDOC");
		
		String oilgal = getVariable("oilgal");
		
		String OilTotal = getVariable("OilTotal");
	
		
		String fname = getVariable("oilrecording") + ".WAV";
		
		String reclen = getVariable("reclen");
		
		String oilcell = getVariable("oilcomphone");
		
		String reclenRe = getVariable("reclenRe");
		
		String ifcheckM = getVariable("ifcheckmeter");
		
				
		
	    String[] opts = new String[9];
		opts[0] = invDoc;
		
		opts[1] = fname;
		
	    opts[2] = oilgal;
	    
	    opts[3] = OilTotal;
		
	    opts[4] = oilDoc;
	    
	    opts[5] = reclen;
	    
	    opts[6] = oilcell;
	    
	    opts[7] = reclenRe;
	    
	    opts[8] = ifcheckM; 
		
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
		
		String cmd = "SAVETALKSENDINV";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/Utility.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		
		setVariable("INVSAVED", "TRUE");
		
		

	}

}
