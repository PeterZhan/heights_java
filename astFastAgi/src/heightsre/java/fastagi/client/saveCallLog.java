package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveCallLog extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String doclogid = getVariable("LOGDOCID");
		String log = getVariable("LOG");
		String lastext = getVariable("LASTEXT");
		
		try{
			   String[] opts = new String[3];
			   opts[0] = doclogid;
			   opts[1] = log;
			   opts[2] = lastext;
			   
			
				
				String cmd = "SAVECALLLOGS";
				
				
		       String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
			    
				NotesWSClient wsclient = new NotesWSClient(url);
				
				String[] res = wsclient.generalCommand(cmd, opts);
		
		}catch(Exception e)
		   {
			   setVariable("VARTEST", e.toString());
		   }
		
		log += "\\\\n";
		String uid = getVariable("UNIQUEID");
		if (lastext == null) lastext = "";
		//SET code = CONCAT(IFNULL(code,''), '_standard')
		String sql = "update tblcalleropts set OptSel=CONCAT(IFNULL(OptSel,''), '" + log + "'), ExtCalled='" + lastext + "', LastModified=NOW() where AstUniID='"
				   + uid + "'";
		
		DbConnectionApache.sqlExecute(sql);
	
		

	}

}
