package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ifinspnumber extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
	/*
		String calleetype = getVariable("calleetype");
		
        if (calleetype == null ,, !calleetype.equals("vendor"))
        {
        	setExtension("hang");
        	setPriority("1");
        	return;
        }
		*/
		
		String docid = getVariable("DOCID");
		String spnum = getVariable("EXTEN");
		if (spnum.endsWith("#")) spnum = spnum.substring(0, spnum.length() -1);

		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = spnum;
		
        String cmd = "ISUSEDSPPHONENUM";
		
		
        String url = "http://www.heightsre.com/Examples/Test/TrackVdr.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String isused = res[0];
		
		
		if (isused.equals("1"))
		{
			setExtension("HASSET");
			setPriority("1");
			return;
		}
		
		setExtension("NOTSET");
		setPriority("1");
		
	//	setVariable("SPDIALNUM", spnum);
	//	setVariable("SPDIALNUMFORPLAY", InsertBlankToString(spnum));
		
		

	}

}
