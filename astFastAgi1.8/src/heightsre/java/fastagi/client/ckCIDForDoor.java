package heightsre.java.fastagi.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ckCIDForDoor extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		Set<String> h = new HashSet<String>(
				Arrays.asList("601", 
				              "602",
				              "606",
				              "608",
				              "640",
				              "621",
				              "622",
				              "612",
				              "625",
				              "607",
				              "620",
				              "609",
				              "605",
				              "615"
				              )
				);
		
		
		String callerid = getVariable("CALLERID(num)");
		
		if (!h.contains(callerid))
			setExtension("hang");
		
		

	}

}
