package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateScJobCates extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String jc = getVariable("EXTEN");
		if (jc.endsWith("#"))
			jc = jc.substring(0, jc.length()-1);
		
		
		String catNum = getVariable("catnum");
		
		int iJc = Integer.parseInt(jc);
		int icatNum = Integer.parseInt(catNum);
		
		
		if (iJc > icatNum)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		String jobCat = getVariable("vdrcat" + iJc);
		
		setVariable("JOBCAT", jobCat);
		setVariable("JOBCATE", jobCat);
	//	setExtension("codefound");
	//	setPriority("1");
		

	}

}
