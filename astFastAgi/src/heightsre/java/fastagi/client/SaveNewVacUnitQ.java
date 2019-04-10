package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SaveNewVacUnitQ extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String docid = getVariable("VACDOC");
        String movedate = getVariable("LMOVEDATE");
        String renodate = getVariable("LRENODATE");
        String garb = getVariable("GARB");
        String repair = getVariable("REPAIR");
        
        String furni = getVariable("FURNI");
		
		String cmd = "SAVENEWVACUNITQ";
		String[] opts = new String[6];
		opts[0] = docid;
		opts[1] = movedate;
		opts[2] = renodate;
		opts[3] = garb;
		opts[4] = repair;
		opts[5] = furni;
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		
		
		

	}

}
