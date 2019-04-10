package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setOccupantNum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
         String occupantnum = getVariable("NUMBERINPUT");
		 String docid = getVariable("DOCID");
		 
		 setVariable("occupantnum", occupantnum);
			
		 if (docid.equals("none")) return;
		
		 
		//	if (occupantnum.endsWith("#")) 
		//		occupantnum = occupantnum.substring(0, occupantnum.length() -1);
			
		 
			
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = occupantnum;
		String cmd = "SETOCCUNUM";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);

	}

}
