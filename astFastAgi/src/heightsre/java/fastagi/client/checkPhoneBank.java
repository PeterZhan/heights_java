package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class checkPhoneBank extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String did = getVariable("EXTEN");
		String callerid = getVariable("CALLERID(num)");
		String callername = getVariable("CALLERID(name)");
		
		
		 String[] opts = new String[3];
			opts[0] = did;
			
			opts[1] = callerid;
			
			opts[2] = callername;
			
		
			
		
		
			
			
			
			
			
			//*******************************
			
			String cmd = "CHECKFORDID";
			
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, opts);
		
		   
			String v = res[0];
			
			setVariable("TEST01", v);
			
			if (v.equals("1"))
			{
				setVariable("DIDSTATUS", res[1]);
				setVariable("INQDOC", res[2]);
				setVariable("BLDG", res[4]);
				
			
				setVariable("APTNO", res[5]);
				
				setVariable("UNITTYPE", res[6]);
				
				String managecell = res[3];
				if (!managecell.equals("") && managecell.length()>=10)
				{
					
					setVariable("MANAGCELL", managecell);
					
					
				}
				
				
				setVariable("pbDID",did);
				
				setVariable("INCALLTYPE", "PHONEBANK " + did);
				
				String ext = "634";
				
				if (!res[6].equals("Residential"))
					ext = "625";
				
				
				if (managecell.length() == 3)
					ext = managecell;
				
				setVariable("EXT", ext);
				
				
				
				setContext("phonebankforvac");
				setExtension("s");
				setPriority("1");
				
				
				
				
				
			}else
			{
				
				setContext("from-pstn-orig");
				setExtension(did);
				setPriority("1");
				
				
				
			}
		
		
		
		
		
		

	}

}
