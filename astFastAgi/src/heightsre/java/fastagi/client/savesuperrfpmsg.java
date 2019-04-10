package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesuperrfpmsg extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
        String docid = getVariable("DOCID");
		
		String ComingTime = getVariable("comingtime");
		String workerNum = getVariable("workernum");
		
		String rfpprob = getVariable("rfpprob");
	
		
		String callernum = getVariable("CALLERID(num)");
		
		String superName = getVariable("SUPER");
		
		String reclength = getVariable("reclen");
		
		String fname = getVariable("srfprecording") + ".WAV";;
		
				
		
	    String[] opts = new String[8];
		opts[0] = docid;
		opts[1] = ComingTime;
		opts[2] = workerNum;
		opts[3] = rfpprob;
		opts[4] = callernum;
		opts[5] = superName;
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
		
		String cmd = "SAVESUPERRFPMSG";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
      //  setVariable("sext", res[0]);
		
	    
		
  	      
	 
	
		
		
		
		
		

	}

}
