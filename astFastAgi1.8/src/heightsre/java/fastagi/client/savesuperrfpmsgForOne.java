package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesuperrfpmsgForOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		   String docid = getVariable("DOCID");
			
			String ComingTime = getVariable("comingtime");
			
			if (ComingTime == null) ComingTime = "";
			
			String workerNum = getVariable("workernum");
			
		//	String rfpprob = getVariable("rfpprob");
		
			
			String callernum = getVariable("CALLERID(num)");
			
			String superName = getVariable("EMPNAME");
			
			String reclength = getVariable("reclen");
			
			String fname = getVariable("srfprecording") + ".WAV";;
			
					
			
		    String[] opts = new String[7];
			opts[0] = docid;
			opts[1] = ComingTime;
			opts[2] = workerNum;
		//	opts[3] = rfpprob;
			opts[3] = callernum;
			opts[4] = superName;
			opts[5] = fname;
			opts[6] = reclength;
		
				
		
	
		
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
		
		String cmd = "SAVENEWSUPERRFPMSG";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
      //  setVariable("sext", res[0]);
		

	}

}
