package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveSuperTenComp extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String fname = "";
        fname = getVariable("callten") + ".WAV";
        
        String docid = getVariable("DOCID");
        
        byte[] data = null;
    	String[] opts = new String[2];
    	opts[0] = docid;
    	opts[1] = fname;
        
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
    		   setVariable("RET1", e.toString());
    	   }
    	   
		
    	   String cmd = "SAVECOMPSUPERTENANT";
   		
   		
   		
   		
   		String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		//setVariable("RET1", "START noten=" + opts[7]);
   		
   		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		
		

	}

}
