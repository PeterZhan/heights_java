package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateOrderGal extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

		String OrderGal = getVariable("EXTEN");
		if (OrderGal.endsWith("#")) 
			OrderGal = OrderGal.substring(0, OrderGal.length() -1);
		
		
		if (!MyJMathLib.isNumeric(OrderGal))
		{
			setExtension("i");
			setPriority("1");
	
			
			return;
			
		}
		
		int iOrderOil = Integer.parseInt(OrderGal);
		
        String oilToTal = getVariable("OilTotal");
		
		int iOilTotal = Integer.parseInt(oilToTal);
		
		
		String oilGal = getVariable("oilgal");
			
		int ioilGal = Integer.parseInt(oilGal);
		
		if ((ioilGal + iOrderOil) > (iOilTotal*95/100))
		{
			setExtension("overflow");
			setPriority("1");
			return;
		}
		
		setVariable("orderoil", OrderGal);
		
		
	}

}
