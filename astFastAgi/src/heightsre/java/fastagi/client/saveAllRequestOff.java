package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;


public class saveAllRequestOff extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("EMPDOCID");
		
		String reqtype = getVariable("reqtype");
		String reqpaid = getVariable("reqpaid");
		
		String startdate = getVariable("STARTDATE");
		String enddate = getVariable("ENDDATE");
		
		String nextyear = getVariable("nextyear");
		
		String callernum = getVariable("CALLERID(num)");
		
		String recname = "none";
		
		String fname = getVariable("varecording");
		
		if (fname != null && !fname.equals(""))
		{
			recname = fname + ".WAV";
		//	recname = "/var/spool/asterisk/monitor/" + fname;
	
		}
	  
		
		
	    String[] opts = new String[8];
		opts[0] = docid;
		opts[1] = reqtype;
		opts[2] = reqpaid;
		opts[3] = startdate;
		opts[4] = enddate;
		opts[5] = nextyear;
		opts[6] = callernum;
		opts[7] = recname;
		
		byte[] data = null;
		
		if (!recname.equals("none"))
		{
			
			File f = new File("/var/spool/asterisk/monitor/" + recname);
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
		}
		
		
		
		
		//*******************************
		
		String cmd = "SAVEALLREQUESTOFF";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
        setVariable("VARTEST", res[0]);
		
	    if (res[0].equals("0")) 
	    	setVariable("forsuper", "");
		setVariable("DOCID", res[1]);
		

	}

}
