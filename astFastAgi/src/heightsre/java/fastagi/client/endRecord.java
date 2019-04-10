package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;

import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.*;

public class endRecord extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
	
		String recStart = getVariable("RECSTART");
		
		if (recStart == null || recStart.equals(""))
			return;
		
		
		long startRec = Long.parseLong(recStart);
		
		
		Date d = new Date();
		
		long recLen = d.getTime()/1000 - startRec;
		
				
		
		
		
		
		if (recLen <= 15)
		{
			//setPirioty("begin");
			
			streamFile("custom/msgTooShort");
			
			setVariable("RECSAVED", "");
			setVariable("RECLENGTH", "");
			
			setVariable("RECSTART", "");
			
			setExtension("s");
			setPriority("begin");
			
			
			
		} else
		{
			setVariable("RECLENGTH", "" + recLen);
			
			setVariable("RECSAVED", "YES");
		}
		
		
		
		
		// TODO Auto-generated method stub

	}

}
