package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class tryOtherPhone extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		    String docid = getVariable("DOCID");
			
			String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
			
			NotesWSClient wsclient = new NotesWSClient(url);
			
			
			
			String[] options = new String[4];
			options[0] = docid;
			options[1] = getVariable("typeofdetect");
			options[2] = getVariable("NAMECALLED");
			options[3] = getVariable("REMOTEEXTEN");
			
			try{
			
			  String[] res = wsclient.generalCommand("NOBODYANSWER", options);
			}catch(Exception e)
			{
				System.out.println(e);
			}
		
		
		
		
		
		
		
		
		
		
		
		String otherphone = getVariable("OTHERPHONE");
		
		if (otherphone == null || otherphone.equals(""))
		{
			
           
		
		
		} else
		{
			
		//	String docid = getVariable("DOCID");
			
		//	String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
			
			String[] opts = new String[2];
			opts[0] = otherphone;
			opts[1] = docid;
			
			try{
		//	AstPortalProxy service = new AstPortalProxy();  
		//	service.setEndpoint(url);
			String[] res = wsclient.generalCommand("TRYOTHERPHONE", opts);
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			
		}
			
		//hangup();	
		
		

	}

}
