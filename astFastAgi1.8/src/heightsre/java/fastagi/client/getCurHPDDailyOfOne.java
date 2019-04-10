package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurHPDDailyOfOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String i = getVariable("I");
		String docid = getVariable("DOCID" + i);
		String spName = getVariable("SUPER");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = docid;
   
       setVariable("DOCID", docid);
       setVariable("SUPERNAME", spName);
   
       String cmd = "GETHPDDAILYBYID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/HPDTrack.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String bldg = res[0];
	   String ocount = res[1];
	   String wcount = res[2];
	   
	   setVariable("BLDG", bldg);
	   setVariable("OCOUNT", ocount);
	   setVariable("WSCOUNT", wcount);
		

	}

}
