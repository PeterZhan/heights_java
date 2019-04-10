package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateScPorbStart extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String probStart = getVariable("EXTEN");
		if (probStart.endsWith("#"))
			probStart = probStart.substring(0, probStart.length()-1);
		

		
		setVariable("PROMSTART", probStart);
		
	}

}
