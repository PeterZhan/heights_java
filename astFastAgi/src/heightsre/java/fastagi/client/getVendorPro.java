package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getVendorPro extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String BlgCode = getVariable("BlgCode");
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = BlgCode;
    
    
    
      String cmd = "GETVDRBYCODE"; 
	   	
      String url = "http://www.heightsre.com/Examples/Test/Exterm.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String vdrdoc = res[0];
	   String lastdate = res[1];
	   String nextdate = res[2];
	   
	   
	   setVariable("VDRDOC", vdrdoc);
	   setVariable("LASTDATE", lastdate);
	   setVariable("NEXTDATE", nextdate);
	   
	   
	
	
	
	
	
	}

}
