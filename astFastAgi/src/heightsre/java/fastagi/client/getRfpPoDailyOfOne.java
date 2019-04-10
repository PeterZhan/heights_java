package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getRfpPoDailyOfOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String supname = getVariable("SUPER");
		String inmode = getVariable("OneMode");
		String code = getVariable("BlgCode");
		
		if (code == null) code = "";
		String[] opt = new String[3];
	     
	     // these are the options for the service command
       opt[0] = supname;
       opt[1] = code;
       opt[2] = inmode;
    
    
    
      String cmd = "GETALLRFPDAILYBYNAME"; 
	   	
      String url = "http://www.heightsre.com/Examples/Test/rfp.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String num = res[0];
	   setVariable("num", num);
	   int iNum = Integer.parseInt(num);
	   
	   if (iNum > 0)
	   {
		   for(int i=1;i<=iNum;i++)
		   {
			   
			   setVariable("DOCID" + i, res[i]);
			   
			   
		   }
		   
		   
		   
		   
	   }
	   
		
		
		
		

	}

}
