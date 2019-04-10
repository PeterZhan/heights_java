package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getPOInvoiceTextFax extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String orderoil = getVariable("orderoil");
		String empdoc = getVariable("EMPDOCID");
		String bldgdoc = getVariable("BLDGDOC");
		
		String runmode = getVariable("runmode");
		
		String oilgal = getVariable("oilgal");
		
		
		String[] opts = new String[5];
		opts[0] = orderoil;
		opts[1] = empdoc;
		opts[2] = bldgdoc;
		opts[3] = runmode;
		opts[4] = oilgal;
		
		String cmd = "GETPOINVOICETEXTFAX";
		
		
        String url = "http://www.heightsre.com/Examples/Test/Utility.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String po = res[0];
		String oilcom = res[1];
		String oilphone = res[2];
		String ordDoc = res[3];
		String invDoc = res[4];
		
		
		setVariable("PO", po);
		setVariable("oilcom", oilcom);
		setVariable("oilcomphone", oilphone);
		
		setVariable("ORDDOC", ordDoc);
		setVariable("INVDOC", invDoc);
		
		
		

	}

}
