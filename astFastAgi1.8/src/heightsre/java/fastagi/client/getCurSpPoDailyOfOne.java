package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurSpPoDailyOfOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String i = getVariable("I");
		String code = getVariable("CODE" + i);
		String spName = getVariable("SUPER");
		
	//	String[] opt = new String[2];
	     
	     // these are the options for the service command
   //     opt[0] = code;
   //     opt[1] = spName;
   
       setVariable("BlgCode", code);
    //   setVariable("SUPERNAME", spName);
   
    /*   String cmd = "GETSPPODAILYBYID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String bldg = res[0];
	 
	   
	   setVariable("BlgCode", bldg);
	  
		*/

	}

}
