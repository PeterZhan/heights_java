package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateUnitCode extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String unitCode = getVariable("EXTEN");
		
		if (unitCode.endsWith("#"))
			unitCode = unitCode.substring(0, unitCode.length()-1);
		
		String bldgCode = getVariable("BlgCode");
		
		String cmd = "VALIDATEUNITCODE";
		String[] opts = new String[2];
		opts[0] = bldgCode;
		opts[1] = unitCode;
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/UnitProf.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
			String aptNo = res[1];
			
			setVariable("UNITNO", aptNo);
			
			String baptNo = InsertBlank(aptNo);
			
			setVariable("BLUNITNO", baptNo);
			
			setExtension("codefound");
			setPriority("1");
		}else
		{
			setExtension("notfound");
			setPriority("1");
		}
		
		

	}
	
	private String InsertBlank(String s)
	{
		String ret = "";
		for (int i=0; i< s.length(); i++)
		{
			ret = ret + s.substring(i,i+1) + " ";
		}
		
		if (ret.endsWith(" "))
		{
			ret = ret.substring(0, ret.length()-1);
		}
		
		
		return ret;
	}

}
