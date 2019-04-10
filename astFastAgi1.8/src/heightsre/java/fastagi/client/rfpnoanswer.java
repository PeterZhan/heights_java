package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class rfpnoanswer extends BaseAgiScript {

	@Override
	
	// this agi is for rfp no answered.
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docs = getVariable("MULTIDOCS");  // get the document id array
		
		// the web service url
		 String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
		   
	     try{
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url); // set the web service url
	 		 
	 		NotesWSClient wsclient = new NotesWSClient(url);
	 		// String[] res = wsclient.generalCommand(cmd, options, data);
	 		 
	 		 String[] opt = new String[2];
	 		 
	 		 opt[0] = docs;
	 		 opt[1] = "none";
	 		
	 	//	 System.out.println("Starting get");
	 		 
	 		 // send the web service command
	 		 String[] res = wsclient.generalCommand("RFPNOANSWER", opt);
	 		 
	 		 setVariable("wsResult", res[0]);
	 		 
	 	//	 System.out.println("Ending get");
	 		// System.out.println(res[0]);
	 		 
	 		 
	 		// streamFile("thank-you-cooperation");
	 		// System.out.println(res[0]);
	 		 
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
