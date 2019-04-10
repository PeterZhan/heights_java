package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.*;

public class validateKeyInput extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String keyInput = getVariable("EXTEN");
		if (keyInput.endsWith("#"))
			keyInput = keyInput.substring(0, keyInput.length()-1);
		
		
		if (keyInput.length() % 2 != 0)
		{
			keyInput = keyInput + "1";
		}
		
		int kl = keyInput.length();
		
		if (kl < 4)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
	
		
		
		
		String kstr = "";
		String kstrwithBlank = "";
		
		String Akey;
		
				
	    for (int i=1; i<=kl/2; i++)
	    {
	    	Akey = keyInput.substring((i-1)*2, i*2);
	    	
	    	Akey = getRealChar(Akey);
	    	
	    	
	    	
	    	if (Akey != null)
	    	{
	    		//if (i==1)
		    	//	Akey = Akey.toUpperCase();
	    		
	    		kstr = kstr + Akey;
	    		kstrwithBlank = kstrwithBlank + " " + Akey;
	    	} else
	    	{
	    		setExtension("i");
				setPriority("1");
				return;
	    	}
	    	
	    	
	    }
		
		setVariable("CHARSINPUT", kstr);
		
		setVariable("CHARSINPUTB", kstrwithBlank);
		
		

	}
	
	private String getRealChar(String Akey)
	{
		String ret = null;
		
	    HashMap<String, String> keyCharMap = new HashMap<String, String>();
		keyCharMap.put("21", "A");
		keyCharMap.put("22", "B");
		keyCharMap.put("23", "C");
		
		keyCharMap.put("31", "D");
		keyCharMap.put("32", "E");
		keyCharMap.put("33", "F");
		
		keyCharMap.put("41", "G");
		keyCharMap.put("42", "H");
		keyCharMap.put("43", "I");
		
		keyCharMap.put("51", "J");
		keyCharMap.put("52", "K");
		keyCharMap.put("53", "L");
		
		keyCharMap.put("61", "M");
		keyCharMap.put("62", "N");
		keyCharMap.put("63", "O");
	    
		keyCharMap.put("71", "P");
		keyCharMap.put("72", "Q");
		keyCharMap.put("73", "R");
		keyCharMap.put("74", "S");
		
		keyCharMap.put("81", "T");
		keyCharMap.put("82", "U");
		keyCharMap.put("83", "V");
		
		keyCharMap.put("91", "W");
		keyCharMap.put("92", "X");
		keyCharMap.put("93", "Y");
		keyCharMap.put("94", "Z");
		
		
		ret = keyCharMap.get(Akey);
		
		
		
		
		
		
	    keyCharMap = null;
	    
	    return ret;
		
	}
	
	
	
	
}
