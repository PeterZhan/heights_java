package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getEmailFaxTenant extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		
		
		    String docid = getVariable("DOCID");
	       // String exten = getVariable("EXTEN");
			
			
			String cmd = "GETEMAILFAXFORTEN";
			String[] opts = new String[1];
			opts[0] = docid;
			//opts[1] = exten;
			
		//	opts[4] = opt;
			
			try {  
				
				String[] res = ASTPORTAL.generalCommand(cmd, opts, "TenantNu.nsf");
				String emails = res[0];
				String faxes = res[1];
				
				setVariable("EMAILS", emails);
				setVariable("FAXES", faxes);
				
				
				
				setVariable("EMAILSBLANK", strSplitByBlank(emails));
				setVariable("FAXESBLANK", strSplitByBlank(faxes));
				
				
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				setVariable("ERROR", e.toString());
				e.printStackTrace();
			}
			
		
		
		
		
		
		
		
		
		
		
		

	}
	
	
	private String strSplitByBlank(String s)
	{
		String res = "";
		
		for (int i=0; i<s.length(); i++)
		{
			res += s.substring(i, i+1) + " ";
		}
		
		
		
		return res;
		
	}
	
	
	

}
