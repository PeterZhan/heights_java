package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateComp extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		int iNum = Integer.parseInt(getVariable("compnum"));
		
		String sel = getVariable("EXTEN");
		if (sel.endsWith("#"))
			sel = sel.substring(0,sel.length()-1);
		
		int isel = Integer.parseInt(sel);
		
		if (isel>iNum || isel == 0)
		{
		   setExtension("i");
		   setPriority("1");
		   return;
		
		}
		
		setVariable("COMPLAINT", getVariable("comp" + isel));
		
		

	}

}
