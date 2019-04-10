package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setApplicantNum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
        String applicantnum = getVariable("NUMBERINPUT");
		String docid = getVariable("DOCID");
		
		setVariable("applicantnum", applicantnum);
		
		if (docid.equals("none")) return;
		
	//	if (applicantnum.endsWith("#")) 
	//		applicantnum = applicantnum.substring(0, applicantnum.length() -1);
		
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = applicantnum;
		String cmd = "SETAPPLINUM";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);


	}

}
