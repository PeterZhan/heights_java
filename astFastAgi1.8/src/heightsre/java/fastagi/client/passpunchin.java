package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.*;
import java.text.*;
public class passpunchin extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		 String passin = getVariable("EXTEN");
			
	     if (passin.endsWith("#")) 
	    	 passin = passin.substring(0, passin.length() -1);
			
		
	     String punchpass = getVariable("punchpass");
	     
	     if (punchpass.equals(passin))
	     {
	    	 try{
	    //	 Calendar c = Calendar.getInstance();
	    	 SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	    	 
	    	 Date now = new Date();
	    	 String punchtime = df.format(now);
	    		 
	    		 /*c.get(c.HOUR)
	    	                   + ":" + c.get(c.MINUTE) 
	    	                   + " " + c.getDisplayName(c.AM_PM,c.LONG,locale);
	    	 */
	    	 setVariable("punchtime", punchtime);
	    	 
	    	 setExtension("rightpass");
	    	 setPriority("1");
	    	 }catch(Exception e)
	    	 {
	    		 setVariable("varException", e.toString());
	    	 }
	     }else
	     {
	    	 setExtension("wrongpass");
	    	 setPriority("1");
	     }
	     
	     
		
	/*	
        String url = "http://www.heightsre.com/Examples/Test/PunchCLK.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
*/
	}

}
