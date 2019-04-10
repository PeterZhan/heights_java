package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class specialoldexten extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		String exten = getVariable("EXTEN");
		String callidnum = getVariable("CALLERID(num)");
		String callidname = getVariable("CALLERID(name)");
		
		try{
		// AstPortalProxy service = new AstPortalProxy();  
 		// service.setEndpoint(url);
 		  
 		 String[] options = new String[3];
 		 
 		 options[0] = exten;
 		 options[1] = callidnum;
 		 options[2] = callidname;
 		NotesWSClient wsclient = new NotesWSClient(url);

		String[] res = wsclient.generalCommand("SPECIALEXTEN", options);      
 		 
 		// String[] res = service.generalCommand("SPECIALEXTEN", options);
 		 
 		 String p = res[0];
 		 String docid = res[1];
 		 
 		 if (p.startsWith("64657264"))
 			 p = p.replace("4657264", "");
 		 
 		 setVariable("phonenum", p);
 		 setVariable("DOCID", docid);
 		 
 		 if (p.length()>3)
 		 {
 			 setVariable("outnum", "yes");
 		 }else
 		 {
 			 setVariable("outnum", "no");
 		 }
 		 
 		 
 		 
 		 
		}
		catch(Exception e)
		{
			
			setVariable("RETVAL2", e.toString());
			System.out.println(e);
		}
		
		
		
		
		
		

	}

}
