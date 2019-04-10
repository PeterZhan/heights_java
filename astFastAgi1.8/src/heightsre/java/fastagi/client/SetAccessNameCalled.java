package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SetAccessNameCalled extends BaseAgiScript {

	@Override
	// set the confirmed name and phone number of the called
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		
		String ifnamecalled = getVariable("IFNAMECALLED");
		
		if (ifnamecalled.equals("yes")) return;  // if has been set, return.
		
		setVariable("IFNAMECALLED", "yes");
		
		
		String acdocid = getVariable("DOCID"); // get the document id
		String namecalled = getVariable("NAMECALLED"); // get the name called
		String numcalled = getVariable("REMOTEEXTEN"); // get the phone number called
		
		// the web service url
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    //	 AstPortalProxy service = new AstPortalProxy();  
	 	//	 service.setEndpoint(url);  // set the web service url
	 		 
	 		 String[] opt = new String[3];
	 		 
	 		 opt[0] = acdocid;
	 		 opt[1] = namecalled;
	 		 opt[2] = numcalled;
	 		 System.out.println("Starting get");
	 		
	 		 // send the web service command
	 		 
	 		NotesWSClient wsclient = new NotesWSClient(url);

			String[] res = wsclient.generalCommand("ACCESSNAMECALLED", opt);
	 		 
	 		// String[] res = service.generalCommand("ACCESSNAMECALLED", opt);
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 
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
