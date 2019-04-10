package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getPhoneForAndrd extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String callerid = getVariable("CALLERID(num)");
	    String curchannel = getVariable("CHANNEL");
		
		if (callerid.startsWith("1") && callerid.length() == 11)
			callerid = callerid.substring(1);
		
		
		String cmd = "GETPHONEFORANDROID";
		String[] opts = new String[2];
		opts[0] = callerid;
		opts[1] = curchannel;
		
       String url = "http://www.heightsre.com/Examples/Test/Certmail.nsf/AstPortal";
		
		
        NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String phonenum = res[0];
		String ext = "";
		
		if (phonenum.contains(","))
		{
			String[] phoneext = phonenum.split(",");
			phonenum = phoneext[0];
			ext = phoneext[1];
			
		}
		
		
		setVariable("phonenum", phonenum);
		setVariable("EMPEXT", res[2]);
		setVariable("EMPDID", res[1]);
		
		setVariable("EMPNAME", res[3]);
		
		setVariable("ANDRDOC", res[4]);
		
		setVariable("DBNAME", res[5]);
		setVariable("PDOCID", res[6]);
		
		if (!ext.equals(""))
		{

		    		
			
			setVariable("EXT", ext);
			
			
			
			
		}
	//	setVariable("DOPT","U(andDTMF^" + ext + ")");
		
		

	}

}
