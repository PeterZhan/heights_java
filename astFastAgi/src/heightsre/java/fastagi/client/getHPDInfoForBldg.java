package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getHPDInfoForBldg extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
	
    
    
      
	   	
      String bldgCode = getVariable("BlgCode");
		
		String[] opts = new String[1];
		opts[0] = bldgCode;
		
	 	   
		String cmd = "NEWGETALLHPDDAILYBYNAME"; 
 		
 		
 		
 		  
 		String url = "http://www.heightsre.com/Examples/Test/HPDTrack.nsf/AstPortal";
 	    
 		NotesWSClient wsclient = new NotesWSClient(url);
 		
 		String[] res = wsclient.generalCommand(cmd, opts);
		
 		if (!res[0].equals("0"))
 		{
 		
 		setVariable("HPDDOCID", res[0]);
 	//	setVariable("BLDG", res[1]);
 		setVariable("OCOUNT", res[1]);
 		setVariable("WSCOUNT", res[2]);
 	//	setVariable("SUPERNAME", res[4]);
 		setExtension("found");
 		setPriority("1");
 		}
 		else
 		{
 			setExtension("notfound");
 	 		setPriority("1");
 		}
		
		


	}

}
