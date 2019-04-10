package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateRfpAct extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String jt = getVariable("EXTEN");
		if (jt.endsWith("#"))
			jt = jt.substring(0, jt.length()-1);
		
		
		String typeNum = getVariable("actnum");
		
		int iJt = Integer.parseInt(jt);
		int itypeNum = Integer.parseInt(typeNum);
		
		
		if (iJt > itypeNum)
		{
			setExtension("notfound");
			setPriority("1");
			return;
		}
		
		String jobType = getVariable("at" + iJt);
		
		setVariable("ACTION", jobType);
		
		setExtension("codefound");
		setPriority("1");
		
		
	}

}
