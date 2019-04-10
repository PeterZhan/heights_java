package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class sendLockBoxEmail extends BaseAgiScript {

	@Override
	
	// this agi is for sending the lockbox address to user
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("DOCID");  // get the document id
		String mailaddr = getVariable("EMAILADDR"); // get the email address
		String lockbox = getVariable("LOCKBOXADDR");  // get the lockbox address string
		
		
		// the url of the web service
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    //	 AstPortalProxy service = new AstPortalProxy();  
	 	//	 service.setEndpoint(url); // set the web service url
	 		 
	 		NotesWSClient wsclient = new NotesWSClient(url);
	 		// String[] res = wsclient.generalCommand(cmd, options, data);
	 		 
	 		 
	 		 String[] opt = new String[3];
	 		 
	 		 opt[0] = docid;  // document id
	 		 opt[1] = mailaddr; // mail address 
	 		 opt[2] = lockbox;  // the lockbox string
	 		 System.out.println("Starting get");
	 		 
	 		 String[] res = wsclient.generalCommand("EMAILLOCKBOX", opt);// call the web service
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 setVariable("VARTEST", res[0]);
	 	//	 streamFile("thank-you-cooperation");
	 	//	 System.out.println(res[0]);
	 		 
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	// hangup();
		 }
		
		
		

	}

}
