package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validateCondi extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String cd = getVariable("EXTEN");
		if (cd.endsWith("#"))
			cd = cd.substring(0, cd.length()-1);
		
		
		String condNum = getVariable("condnum");
		
		int icondNum = Integer.parseInt(condNum);
		int icd = Integer.parseInt(cd);
		
		
		if (icd > icondNum)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		String condi = getVariable("cond" + icd);
		
		setVariable("CONDITION", condi);
		
		//setExtension("codefound");
		//setPriority("1");

		
		
		
		
		
		

	}

}
