package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SendNuisanceForm extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String docid = getVariable("DOCID");
        String exten = getVariable("EXTEN");
        String fax = getVariable("FAXES");
        
        if (fax.endsWith("#"))
        	fax =fax.substring(0, fax.length()-1);
        
        setVariable("FAXESBLANK", strSplitByBlank(fax));
		
		
		String cmd = "RESENDREMNUISANCE";
		String[] opts = new String[3];
		opts[0] = docid;
		opts[1] = exten;
		opts[2] = fax;
		
	//	opts[4] = opt;
		
		try {  
			
			ASTPORTAL.generalCommand(cmd, opts, "TenantNu.nsf");
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
