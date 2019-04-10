package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.util.Calendar;

public class SetDayOfMonth extends BaseAgiScript {

	@Override
	
	// the agi is for set the payment day
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		
		String docid = getVariable("RESPDOCID");  // get the response document id
		String payday = getVariable("PAYDAY");  // get the payment day
		
	//	if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);
		
		 
		
		int dayOfMonth = Integer.parseInt(payday);
	/*	
		Calendar c = Calendar.getInstance();
		
		int maxdays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		if (!(dayOfMonth > 0 && dayOfMonth < maxdays + 1))
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
		}
		
		*/
		 
		// the web service url
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);// set the web service url
	 		 
	 		 String[] opt = new String[2];
	 		 
	 		 opt[0] = docid;
	 		 opt[1] = payday;
	 		 System.out.println("Starting get");
	 		 
	 		 // send the web service command
	 		NotesWSClient wsclient = new NotesWSClient(url);
	 		String[] res = wsclient.generalCommand("PAYDAYOFMONTH", opt);
	 		// String[] res = service.generalCommand("PAYDAYOFMONTH", opt);
	 		 
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
		
		
		// TODO Auto-generated method stub

	}
		
		
		
		// TODO Auto-generated method stub

	

}
