package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getFirstUnit extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String units = getVariable("units");
		String[] unitarray = units.split(":");
		
		setVariable("curno", "1");
		
		setVariable("curunit", unitarray[0]);
		
		
		
		
	//	String sgrents = getVariable("sgrents");
		
	//	String[] rentarray = sgrents.split(":");
		
	//	setVariable("cursgrent", rentarray[0]);
		
		
	}

}
