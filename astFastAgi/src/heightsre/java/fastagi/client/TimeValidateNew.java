package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeValidateNew extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String tm = getVariable("EXTEN");
		
		if (tm.endsWith("#")) 
	        	tm = tm.substring(0, tm.length() -1);
			
	
		int h = Integer.parseInt(tm);
		int m = 0;
		
		
		if (h<0 || h>23 || m<0 || m>59)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		Calendar c = Calendar.getInstance();
		
	
		int hnow = c.get(c.HOUR_OF_DAY);
		int mnow = c.get(c.MINUTE);
		
	/*	if ((h*60+m) <= (hnow*60+mnow))
		{
			setExtension("i");
			setPriority("1");
			return;
		}*/
		
		
		c.set(c.HOUR_OF_DAY, h);
		c.set(c.MINUTE, m);
		
	/*
		String punchtime =c.getDisplayName(c.HOUR,c.LONG,null) 
        + ":" + c.getDisplayName(c.MINUTE,c.LONG,null) 
        + " " + c.getDisplayName(c.AM_PM,c.LONG,null);
        */
		
		 SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
    	 
    	 Date now = c.getTime();
    	 String punchtime = df.format(now);
		
		setVariable("timeforpunch", punchtime);
		
		
		SimpleDateFormat dfHour = new SimpleDateFormat("HH:mm");
		
		String timeEnter = dfHour.format(now);
		setVariable("timeEnter", timeEnter);
		
		
		

	}

}
