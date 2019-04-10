package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getSuperByCallerid extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
        String callerID = getVariable("CALLERID(num)");
		
		String[] opts = new String[1];
		opts[0] = callerID;
		
		String cmd = "GETSUPERINFOBYCALLERID";
		
		
        String url = "http://www.heightsre.com/Examples/Test/Empllist.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String bldgcode = res[1];
	
		
		setVariable("VARTEST", res[0]);
		
		if(ifEmp.equals("1"))
		{
			setVariable("BlgCode", bldgcode);
		   	//setContext("getPassByEmpCode");
			
			setVariable("BldgStreetNum", res[2]);
			setVariable("BldgStreetName", res[3]);
			
		
			setVariable("BlgNo", res[4]);
			
			
			
			setExtension("found");
			setPriority("1");
		    return;	
		}
		
		
		setExtension("notfound");
		setPriority("1");
		
		

	}

}
