package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SetPayAmount extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("RESPDOCID");
		
		String payamount = getVariable("PAYAMOUNT");
		
	//	String exten = getVariable("EXTEN");
		
	//	if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);
		
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);
	 		 
	 		 String[] opt = new String[2];
	 		 
	 		 opt[0] = docid;
	 		 opt[1] = payamount;
	 		 System.out.println("Starting get");
	 		 
	 		NotesWSClient wsclient = new NotesWSClient(url);

			String[] res = wsclient.generalCommand("PAYAMOUNT", opt);
	 		 
	 	//	 String[] res = service.generalCommand("PAYAMOUNT", opt);
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 
	 		
	 		 System.out.println(res[0]);
	 		 
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	 
		 }
		

	}

}
