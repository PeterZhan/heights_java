package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ChkSaveNewVacUnit extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String bldgCode = getVariable("BlgCode");
        String bldgNo = getVariable("BlgNo");
        String unitCode = getVariable("UNITNO");
        String supername = getVariable("EMPNAME");
		
		String cmd = "CHECKSAVENEWVAC";
		String[] opts = new String[4];
		opts[0] = bldgCode;
		opts[1] = bldgNo;
		opts[2] = unitCode;
		opts[3] = supername;
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
						
			setExtension("exis");
			setPriority("1");
		}
		
		String vacid = res[1];
		setVariable("VACDOC", vacid);
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
