package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getPassByBldgEmpCode extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String empCode = getVariable("EXTEN");
		String blgCode = getVariable("BlgCode");
        
        if (empCode.endsWith("#")) 
       	 empCode = empCode.substring(0, empCode.length() -1);
		
		
		String[] opts = new String[1];
		opts[0] = blgCode + " " + empCode;
		
		String cmd = "GETPASSBYEMPBUILDINGCODE";
		
		
       String url = "http://www.heightsre.com/Examples/Test/Empllist.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String ifEmp = res[0];
		String ifHaspass = res[1];
		String punchpass = res[2];
		String empName = res[3];
		String empDoc = res[4];
		String SuperPromptOut = res[5];
	//	String empName = res[4];
		
		
		if(!ifEmp.equals("1"))
		{
		//	setContext("getPassByEmpCode");
			setExtension("i");
			setPriority("1");
		    return;	
		}
		
        
        setVariable("EMPNAME", empName);
		
		setVariable("EMPDOCID", empDoc);
				
		if (ifHaspass.equals("1"))
		{
			setVariable("emppass", punchpass);
			
			
		}
		
		
	}

}
