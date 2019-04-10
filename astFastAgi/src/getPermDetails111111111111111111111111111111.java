import heightsre.java.fastagi.client.NotesWSClient;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;


public class getPermDetails111111111111111111111111111111 extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docs = getVariable("DOCIDS");
		String[] docArray = docs.split("*");
		String i = getVariable("i");
		int intI = Integer.parseInt(i);
		
		String docid = docArray[intI];
		
		String[] opts = new String[1];
		opts[0] = docid;
		
	 	   
    	 String cmd = "GETPERMDETAILS";
   		
   		
   		
   		  
   		String url = "http://www.heightsre.com/Examples/Test/Mechanic.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts);
   		
   		String expireDate30 = res[0];
   		String permitNo = res[1];
   		String issueDate = res[2];
   		String expDate = res[3];
   		String description = res[4];
   		
   		
   		
   		
   		
   		setVariable("pEXPDATE30", expireDate30);
   		setVariable("pPERMNO", permitNo);
   		setVariable("pISSDATE", issueDate);
   		setVariable("pEXPDATE", expDate);
   		setVariable("pDESCRIP", description);
   		

	}

}
