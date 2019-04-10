package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class passvalidate extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String passin = getVariable("EXTEN");
		String rightpass = getVariable("emppass");
		
	    if (passin.endsWith("#")) 
	    	 passin = passin.substring(0, passin.length() -1);
	     
	    
	    if (passin.equals(rightpass))
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
