package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getTestPhone extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String[] options = new String[1];
		options[0] = "TEST";
		
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		 
		
        String cmd = "GETTESTPHONE";
		
		try{
			NotesWSClient wsclient = new NotesWSClient(url);
	 		 String[] res = wsclient.generalCommand(cmd, options);
			
		//AstPortalProxy service = new AstPortalProxy();  
		// service.setEndpoint(url);
		// String[] res = service.generalCommand(cmd, options);
	
		 
		setVariable("TESTPHONE", res[0]); 
		
		}catch(Exception e)
		{
			System.out.println(e);
			
		}
		  

	}

}
