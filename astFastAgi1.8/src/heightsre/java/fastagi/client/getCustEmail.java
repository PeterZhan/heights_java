package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCustEmail extends BaseAgiScript {

	@Override
	// this agi is get the customer email address
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		 
		
		String docid = getVariable("DOCID");  // document id
		String namecalled = getVariable("NAMECALLED");  // customer name
		
        String options[] = new String[2];
		
		options[0] = docid;
		options[1] = namecalled;
		
		
		
		String cmd = "GETCUSTEMAIL";  //get customer email command
		
		try{
		
			NotesWSClient wsclient = new NotesWSClient(url);
	 		 String[] res = wsclient.generalCommand(cmd, options);
		//AstPortalProxy service = new AstPortalProxy();  
		// service.setEndpoint(url);  // set the url of web service
		// String[] res = service.generalCommand(cmd, options);  // send the command
	
		 
		setVariable("EMAILADDR", res[0]); // set the channel variable fot the customer email
		
		}catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		
		
		

	}

}
