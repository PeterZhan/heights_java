package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateNumber extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String number = getVariable("EXTEN");
		
        if (number.endsWith("#")) 
        	number = number.substring(0, number.length() -1);
		
		
		
		if (!MyJMathLib.isNumeric(number))
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
			
		}
		
		setVariable("NUMBERINPUT", number);

	}

}
