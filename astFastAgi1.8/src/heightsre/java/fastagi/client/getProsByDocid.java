package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getProsByDocid extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String cmd = "GETPROSPECTSBYDOCID";
		
		String[] opts = new String[1];
		opts[0] = docid;
		
		
        String url = "http://www.heightsre.com/Examples/Test/nrentalt.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String bldg = res[0];
		String aptNo = res[1];
		String unType = res[2];
		String obnames = res[3];
		
		if (!bldg.equals(""))
		{
			setVariable("BLDG", bldg);         
			setVariable("APTNO", aptNo);
			setVariable("UITYPE", unType);
			
			
		}
		
		
		if (!obnames.equals(""))
		{
			obnames = obnames.replace("1_ConvMAIN", "");
			obnames = "/var/spool/asterisk/monitor/" + obnames;
			obnames = obnames.replace("&", "&/var/spool/asterisk/monitor/");
			
			
			obnames = obnames.replace(".WAV", "");
			obnames = obnames.replace(".amr", "");
			obnames = obnames.replace(".wav", "");
			
			
			setVariable("MSG", obnames);
			
			
			
			
			
			
		}
		
		
		
		
		
		

	}

}
