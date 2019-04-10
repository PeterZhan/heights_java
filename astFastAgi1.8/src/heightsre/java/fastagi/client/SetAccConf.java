package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SetAccConf extends BaseAgiScript {

	@Override
	// this agi is for set if the access date call is confirmed
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		
		String acdocid = getVariable("DOCID");  // get the document id
		String namecalled = getVariable("NAMECALLED"); // the the called name
		String numcalled = getVariable("REMOTEEXTEN");  // get the phone number called
		
		// the web service url
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);  // set the web serice url
	 		 
	 		 String[] opt = new String[3];
	 		 
	 		 opt[0] = acdocid;  
	 		 opt[1] = namecalled;
	 		 opt[2] = numcalled;
	 		 System.out.println("Starting get");
	 		 // send the web service command
	 		NotesWSClient wsclient = new NotesWSClient(url);

			String[] res = wsclient.generalCommand("ACCESSCONFIRMED", opt);
	 		 
	 		// String[] res = service.generalCommand("ACCESSCONFIRMED", opt);
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 
	 		// streamFile("thank-you-cooperation");
	 		 System.out.println(res[0]);
	 		 
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	// hangup();
		 }
		
		
		// TODO Auto-generated method stub

	}

}
