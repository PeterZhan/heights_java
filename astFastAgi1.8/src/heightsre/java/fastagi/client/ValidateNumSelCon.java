package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateNumSelCon extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String numsel = getVariable("EXTEN");
		if (numsel.endsWith("#"))
			numsel = numsel.substring(0, numsel.length()-1);
		
		String namenum = getVariable("contnum");
		
		
		int iNumSel = Integer.parseInt(numsel);
		int iNameNum = Integer.parseInt(namenum);
		
		
		if (iNumSel>iNameNum || iNumSel<1)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		
		String conName = getVariable("contname" + iNumSel);
		String conPhone = getVariable("contphone" + iNumSel);
		String cellphone = getVariable("cellphone" + iNumSel);
		
	//	setVariable("spphone", conPhone);
		setVariable("SPDIALNUM", conName);
		setVariable("officephone", conPhone);
		setVariable("cellphone", cellphone);
		
		
		
		

	}

}
