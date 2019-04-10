package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getExtByEmpName extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String empname = getVariable("EMPNAME");
		
		
		try{
			   String[] opts = new String[1];
			   opts[0] = empname;
			 
			   
			
				
				String cmd = "GETEXTBYEMPNAME";
				
				
		       String url = "http://www.heightsre.com/Examples/Test/empllist.nsf/AstPortal";
			    
				NotesWSClient wsclient = new NotesWSClient(url);
				
				String[] res = wsclient.generalCommand(cmd, opts);
				
				String ext = res[0];
				
				setVariable("EMPEXT", ext);
		
		}catch(Exception e)
		   {
			   setVariable("VARTEST", e.toString());
		   }
		
		
		
		

	}

}
