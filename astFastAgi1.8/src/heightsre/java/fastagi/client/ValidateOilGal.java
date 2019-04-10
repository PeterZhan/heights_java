package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateOilGal extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String OilGal = getVariable("EXTEN");
		if (OilGal.endsWith("#")) 
			OilGal = OilGal.substring(0, OilGal.length() -1);
		
		
		if (!MyJMathLib.isNumeric(OilGal))
		{
			setExtension("i");
			setPriority("1");
	
			
			return;
			
		}
		
		int iOilGal = Integer.parseInt(OilGal);
		
		String oilToTal = getVariable("OilTotal");
		
		int iOilTotal = Integer.parseInt(oilToTal);
		
		
		if (iOilGal > iOilTotal) 
		{
			setExtension("i");
			setPriority("1");
		
			
			return;
		}
		
        setVariable("oilgal", OilGal);
        
        int iOrderOil = iOilTotal * 9 /10 - iOilGal;
        
        setVariable("orderoil", "" + iOrderOil);
        
        
        
	}

}
