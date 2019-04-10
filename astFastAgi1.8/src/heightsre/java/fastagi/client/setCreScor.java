package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class setCreScor extends BaseAgiScript {

	@Override
	
	// the agi is for set the credit score
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID"); // get the docuemnt id
		String creditScore = getVariable("NUMBERINPUT");  // get the credit score
		
		setVariable("creditscore", creditScore);
		
        int ics = Integer.parseInt(creditScore);
		
		
		if (ics >= 700)
		{
			setContext("rentalcalltoagent");
			setExtension("s");
			setPriority("1");
			
			
		}
		
		
		
		if (docid.equals("none"))
			return;
		
	//	if (creditScore.endsWith("#")) 
//			creditScore = creditScore.substring(0, creditScore.length() -1);
		
		
		String[] opts = new String[2];
		
		
		
		
		opts[0] = docid;
		opts[1] = creditScore;
		
		
		
		
		
		String cmd = "SETCREDITSCORE";  // the web service ommand
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	   
		// the class for sending web service command
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the web service command
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		
		
		


	}

}
