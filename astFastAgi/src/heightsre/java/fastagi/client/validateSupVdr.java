package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateSupVdr extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String vdr = getVariable("EXTEN");
		if (vdr.endsWith("#"))
			vdr = vdr.substring(0, vdr.length()-1);
		
		
		String vdrNum = getVariable("vdrnum");
		
		int ivdr = Integer.parseInt(vdr);
		int ivdrNum = Integer.parseInt(vdrNum);
		
		
		if (ivdr > ivdrNum)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		String vdrname = getVariable("vdr" + ivdr);
		String vdrnick = getVariable("vdrnick" + ivdr);
		
		setVariable("VENDOR", vdrname);
		setVariable("VENDORNICK", vdrnick);
	//	setVariable("JOBCATE", jobCat);
		
		
		
		
		

	}

}
