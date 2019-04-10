package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getVdrPendPay extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String comp = getVariable("COMPNAME");
		//	String tenant = getVariable("TENANT");
			
		String[] opts = new String[1];
		opts[0] = comp;
		//	opts[1] = tenant;
	
		
		String cmd = "GETPENDPAYBYCOMP";  // this is the command
		
		// web service url
		String url = "http://www.heightsre.com/Examples/Test/SkyRpts.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		// send the web service request
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (!res[0].equals("0"))
		{
			setVariable("FOUND", "1");
			
			int count = Integer.parseInt(res[0]);
			
			setVariable("PAYCOUNT", res[0]);
			
			for (int i=1; i<=count; i++)
			{
				String[] dateBalance = res[i].split("@");
				
				setVariable("PAY" + i, dateBalance[0]);
				setVariable("DATE" + i, dateBalance[1]);
				setVariable("BLDG" + i, dateBalance[2]);
				
				
			}
			
			
			
			
			//setVariable("NEWDATE", res[0]);
		//	setVariable("BALANCE", res[1]);
			
			
			
		} else
		{
			setVariable("FOUND", "0");
			
		}
		
		
		
		
		

	}

}
