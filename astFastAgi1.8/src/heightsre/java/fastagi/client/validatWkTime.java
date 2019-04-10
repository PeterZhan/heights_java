package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.*;

public class validatWkTime extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
        String exten = getVariable("EXTEN");
		
		if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);

		
	    boolean d = validatePayAmount.isDigits(exten);
		
		if (!d)
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
		}
		
	    
		if (exten.length() != 4)
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
		}
		
		
		String hours = exten.substring(0,2);
		String minutes = exten.substring(2);
		
		int h = Integer.parseInt(hours);
		int m = Integer.parseInt(minutes);
		
		if (h<0 || h>23 || m<0 || m>59)
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
		}
		
		
		String wktime = hours + ":" + minutes;
		
		
	//	String prompt = "The arrival time you just input is " + wktime;
		
	//	streamString("wktime", prompt);
		
		setVariable("WKTIME", wktime);
		
		
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR_OF_DAY, h);
		c.set(Calendar.MINUTE, m);
		
		String wktimep = "" + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
		
		if (c.get(Calendar.AM_PM) == Calendar.AM)
			wktimep = wktimep + " " + "AM";
		else
			wktimep = wktimep + " " + "PM";
		
		setVariable("WKTIMEP", wktimep);
		
		
		
		

	}
	
	
	private void streamString(String pre, String prompt)throws AgiException
	{
		
       String unid = getVariable("UNIQUEID");
       
       
       String tmpwav = "/tmp/" + pre + unid;
		
		String[] cmds = new String[3];
		cmds[0] = "/var/javalib/swiftStr.sh";
		cmds[1] = tmpwav + ".wav";
		cmds[2] = prompt;
		
		
		
		 try{
		       
		        Process proc =  Runtime.getRuntime().exec(cmds);

		        
	         if (proc != null)
	         {
	         	proc.waitFor();
	         }


		        
		        
	     }catch(Exception e)
	     {
	     	System.out.println(e);
	     }
		
		
		
		 streamFile(tmpwav);
		 
		 File f = new File(tmpwav + ".wav");
		 
		 f.delete();
		
		
		
		
		
	}
	
	

}
