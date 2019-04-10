package heightsre.java.fastagi.client;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getPermDetails extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String si = getVariable("i");
		int i = Integer.parseInt(si);
		
		String docids = getVariable("DOCIDS");
		String[] docArray = docids.split("\\*");
		
		String docid = docArray[i-1];
		setVariable("DOCID11", docid);
		
		String cmd = "GETPERMDETAILS";
		String[] opts = new String[1];
		opts[0] = docid;
		
		
		setVariable("DOCID11", opts[0]);
	//	opts[4] = opt;
		
		try {
		   String[] res = ASTPORTAL.generalCommand(cmd, opts, "Mechanic.nsf");
		   
		   String exp30 = res[0];
		   String perNo = res[1];
		   String issDate = res[2];
		   String expDate = res[3];
		   String desp = res[4];
		   
		   setVariable("EXPDATE30", exp30);
		   setVariable("PERMNO", perNo);
		   setVariable("ISSDATE", issDate);
		   setVariable("EXPDATE", expDate);
		   setVariable("DESCRIP", desp);
		   setVariable("BLDGADDR", res[5]);
		   
		   
		   
		   
		   
		   
		   
		   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			setVariable("ERROR", e.toString());
			e.printStackTrace();
		}
		

	}

}
