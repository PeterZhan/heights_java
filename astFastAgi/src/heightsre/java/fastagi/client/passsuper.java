package heightsre.java.fastagi.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class passsuper extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String passin = getVariable("EXTEN");
		
	     if (passin.endsWith("#")) 
	    	 passin = passin.substring(0, passin.length() -1);
			
		
	     String punchpass = getVariable("punchpass");
	     
	     if (punchpass.equals(passin))
	     {
	    	
	    	 
	    	 setExtension("rightpass");
	    	 setPriority("1");
	    	
	     }else
	     {
	    	 setExtension("wrongpass");
	    	 setPriority("1");
	     }
		
		
		

	}

}
