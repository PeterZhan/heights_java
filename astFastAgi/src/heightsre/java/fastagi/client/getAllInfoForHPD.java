package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllInfoForHPD extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String bldgCode = getVariable("BlgCode");
		
		String[] opts = new String[1];
		opts[0] = bldgCode;
		
	 	   
    	 String cmd = "GETALLHPDINFOS";
   		
   		
   		
   		  
   		String url = "http://www.heightsre.com/Examples/Test/HPDTrack.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts);
		
   		if (!res[0].equals("0"))
   		{
   		
   		setVariable("DOCID", res[0]);
   		setVariable("BLDG", res[1]);
   		setVariable("OCOUNT", res[2]);
   		setVariable("WSCOUNT", res[3]);
   		setVariable("SUPERNAME", res[4]);
   		}
   		else
   		{
   			setVariable("NODOC", "Yes");
   		}
   		
   	
		
		

	}

}
