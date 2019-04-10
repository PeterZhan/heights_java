package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendRecordForExt extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String doclogid = getVariable("LOGDOCID");
		String IVREXT = getVariable("IVREXT");
		
		String endtime = getVariable("EPOCH");
		String starttime = getVariable("STARTTIME");
		//*CallerID*KCC ARCHITECTS*LogID*ABD123456FDC*Incoming Call For Extension*612*Remote*7183920078*Seconds*70
		int reclen = Integer.parseInt(endtime) - Integer.parseInt(starttime);
		
		String callername = getVariable("CALLERID(name)");
		String callernum = getVariable("CALLERID(num)");
		
		
		
		try{
			   String[] opts = new String[6];
			   opts[0] = callername;
			   opts[1] = doclogid;
			   opts[2] = IVREXT;
			   opts[3] = callernum;
			   opts[4] = "" + reclen;
			   opts[5] = "incoming" + getVariable("UNIQUEID") + ".WAV";
			   
			   
			  
			    
			    File f = new File("/var/spool/asterisk/monitor/" + opts[5]);
			
			    long fsize = f.length();
			   	byte[] data = new byte[(int)fsize];
			   	
			    FileInputStream fin = new FileInputStream(f);
			   	
			    fin.read(data);
			   	
			   	fin.close();
			   
			   
			
				
				String cmd = "SENRECORDFOREXT";
				
				
		       String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
			    
				NotesWSClient wsclient = new NotesWSClient(url);
				
				String[] res = wsclient.generalCommand(cmd, opts, data);
		
		}catch(Exception e)
		   {
			   setVariable("VARTEST", e.toString());
		   }
		
		
		
		

	}

}
