package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateCateJob extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String jc = getVariable("EXTEN");
		if (jc.endsWith("#"))
			jc = jc.substring(0, jc.length()-1);
		
		
		String cateNum = getVariable("catenum");
		
		int iJc = Integer.parseInt(jc);
		int icateNum = Integer.parseInt(cateNum);
		
		
		if (iJc > icateNum)
		{
			setExtension("notfound");
			setPriority("1");
			return;
		}
		
		String jobType = getVariable("jc" + iJc);
		
		setVariable("JOBCATE", jobType);
		
		setExtension("codefound");
		setPriority("1");

	}

}
