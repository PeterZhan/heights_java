package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getOilInfo extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String empDoc = getVariable("EMPDOCID");
		String bldgCode = getVariable("BlgCode");
		
		String[] opts = new String[2];
		opts[0] = empDoc;
		opts[1] = bldgCode;
		
		String cmd = "GETOILINFO";
		
		
       String url = "http://www.heightsre.com/Examples/Test/Utility.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	  String[] res = wsclient.generalCommand(cmd, opts);
		
	  String Capacity = res[0];
	  String Fuel_type = res[1];
	  String bldgDocid = res[2];
	  
	  setVariable("OilTotal", Capacity);
	  setVariable("OilType", Fuel_type);
	  setVariable("BLDGDOC", bldgDocid);
		
		

	}

}
