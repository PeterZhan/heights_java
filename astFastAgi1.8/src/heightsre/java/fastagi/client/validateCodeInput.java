package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateCodeInput extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String keyInput = getVariable("EXTEN");
		if (keyInput.endsWith("#"))
			keyInput = keyInput.substring(0, keyInput.length()-1);
		
		
		String kstrwithBlank = "";
		
		for (int i=0; i<keyInput.length();i++)
		{
			kstrwithBlank = kstrwithBlank + keyInput.charAt(i) + " ";
		}
		
		if (kstrwithBlank.endsWith(" "))
			kstrwithBlank = kstrwithBlank.substring(0, kstrwithBlank.length()-1);
				
	 
		
		setVariable("CODESINPUT", keyInput);
		
		setVariable("CODESINPUTB", kstrwithBlank);

	}

}
