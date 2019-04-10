package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getVacInfo extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
         String cmd = "GETVACINFO";
		

    //    String empDoc = getVariable("EMPDOCID");
         
         String bldgCode = getVariable("BlgCode");
		
		String[] opts = new String[1];
		opts[0] = bldgCode;
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
		
		
        NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String bldg = res[0];
		String code = res[1];
		
		String vacnum= res[2];
		String units = res[3];
		
		String sgrents = res[4];
		
		String supername = res[5];
		
		setVariable("BlgAddr", bldg);
		setVariable("BlgCode", code);
		setVariable("vacnum", vacnum);
		setVariable("units", units);
		setVariable("sgrents", sgrents);
		setVariable("REPNAME", supername);
		
		
	
		
		

	}

}
