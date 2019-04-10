package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getRespDocID extends BaseAgiScript {

	@Override
	
	// this service is for getting payResponse child document id 
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		 
		
		String docid = getVariable("DOCID");  // get document id
		
		String phonecalled = getVariable("REMOTEEXTEN");  // get the phone number
		
		String namecalled = getVariable("NAMECALLED");  // get the person's name
		
		String options[] = new String[4];
		
		options[0] = docid;
		options[1] = "payResponse"; // this is for child document form
		options[2] = phonecalled;
		options[3] = namecalled;
		
		String cmd = "NEWRESPONSEDOC";  // command to create a new child document
		
		try{
		
	//	AstPortalProxy service = new AstPortalProxy();  
	///	 service.setEndpoint(url);  // set the url for the web service
	//	 String[] res = service.generalCommand(cmd, options);  // send the web service command
		 NotesWSClient wsclient = new NotesWSClient(url);
 		 String[] res = wsclient.generalCommand(cmd, options);
		 
		setVariable("RESPDOCID", res[0]);  // get the child document id and set it to a variable
		
		}catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		
		
		
		

	}

}
