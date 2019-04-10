package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateNumSelOil extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String numsel = getVariable("EXTEN");
		if (numsel.endsWith("#"))
			numsel = numsel.substring(0, numsel.length()-1);
		
		String oilnum = getVariable("oilponum");
		
		
		int iNumSel = Integer.parseInt(numsel);
		int ioilNum = Integer.parseInt(oilnum);
		
		
		if (iNumSel>ioilNum || iNumSel<1)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		
		String docid = getVariable("DOC" + iNumSel);
		String phone = getVariable("PHONE" + iNumSel);
		
		
		setVariable("DOCID", docid);
		setVariable("oilcomphone", phone);
		
	//	String conPhone = getVariable("custphone" + iNumSel);
	//	String cellphone = getVariable("cellphone" + iNumSel);
		
	//	setVariable("spphone", conPhone);
	//	setVariable("SPDIALNUM", conName);
	//	setVariable("spphone", conPhone);
	//	setVariable("cellphone", cellphone);

	}

}
