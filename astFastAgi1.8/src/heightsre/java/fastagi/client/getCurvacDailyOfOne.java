package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurvacDailyOfOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String i = getVariable("I");
		String code = getVariable("CODE" + i);
		String spName = getVariable("EMPNAME");
		
		String[] opt = new String[2];
	     
	     // these are the options for the service command
        opt[0] = code;
        opt[1] = spName;
   
       setVariable("BlgCode", code);
       setVariable("REPNAME", spName);
   
       String cmd = "GETVACDAILYBYCODE"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String bldg = res[0];
	   String unitnum = res[1];
	   String units = res[2];
	   String sgrents = res[3];
	   String supercell = res[4];
	   
	   setVariable("BlgAddr", bldg);
	   setVariable("vacnum", unitnum);
	   setVariable("units", units);
	   setVariable("sgrents", sgrents);
	   setVariable("callid", supercell);
		
		
		

	}

}
