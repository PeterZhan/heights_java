package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class conflistenedmsg extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String dbname = getVariable("DBNAME");
		String docid = getVariable("DOCID");
		String fdname = getVariable("FDNAME");
		
		
		String callername = getVariable("CALLNAME");
		
		
		 String[] opts = new String[4];
			opts[0] = dbname;
			
			opts[1] = docid;
			
			opts[2] = fdname;
			
			opts[3] = callername;
		
			
		
			
	
			
			
			
			
			
			//*******************************
			
			String cmd = "CONFLiSTNEDMSG";
			
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, opts);
			
			
		
		

	}

}
