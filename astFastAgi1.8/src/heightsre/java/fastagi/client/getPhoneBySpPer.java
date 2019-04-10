package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getPhoneBySpPer extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String spdial = getVariable("EXTEN");
		
		if (spdial.endsWith("#")) 
		 spdial = spdial.substring(0, spdial.length() -1);
			
		String docid = getVariable("DOCID");
		
		
		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = spdial;
		
        String cmd = "GETPERSONALSPNUM";
		
		
        String url = "http://www.heightsre.com/Examples/Test/TrackVdr.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifVendor = res[0];
		String phonenum = res[1];
		
	
		if (ifVendor.equals("0"))
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		setVariable("SPDIALNUM", spdial);
		setVariable("spphone", phonenum);
		
		
		setContext("comperspdial"); 
		setExtension(phonenum);
		setPriority("1");
		
		
		
		

	}

}
