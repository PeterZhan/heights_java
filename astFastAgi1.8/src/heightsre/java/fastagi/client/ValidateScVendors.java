package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateScVendors extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String vdr = getVariable("EXTEN");
		if (vdr.endsWith("#"))
			vdr = vdr.substring(0, vdr.length()-1);
		
		int ivdr = Integer.parseInt(vdr);
		
		String vdrNum = getVariable("VDRNUM");
		int ivdrNum = Integer.parseInt(vdrNum);
		
		
		if (ivdr > ivdrNum) 
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		
		  setVariable("VENDOR", getVariable("VDR" + ivdr));
		  setVariable("vdrphone", getVariable("VDRP" + ivdr));
		  setVariable("VENDORNICK", getVariable("NICK" + ivdr));
		
		  setVariable("HASW9", getVariable("W9" + ivdr));
		  setVariable("HASINS", getVariable("INS" + ivdr));
		
		
		

	}

}
