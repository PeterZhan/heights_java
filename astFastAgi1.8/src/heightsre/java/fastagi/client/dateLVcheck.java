package heightsre.java.fastagi.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class dateLVcheck extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String dt = getVariable("RET");
		
		if (dt.contains("?"))
		{
			setExtension("invalid");
			setPriority("1");
			return;			
		}
		String year = dt.substring(0,4);
			
		dt = dt.substring(4);
		
		String strMon = dt.substring(0, 2);
		String strDay = dt.substring(2);
		
		int mon = Integer.parseInt(strMon);
		int day = Integer.parseInt(strDay);
		
		
		
		Calendar c = Calendar.getInstance();
		
		
		int nowMon = c.get(Calendar.MONTH) + 1;
		int nowDay = c.get(Calendar.DAY_OF_MONTH);
		
	
		c.set(c.YEAR,Integer.parseInt(year));
	
		
		c.set(c.MONTH, mon -1);
		
	
		
		c.set(c.DAY_OF_MONTH, day);
			
		setVariable("DATEMILLSECS", "" + c.getTimeInMillis());	
		setVariable("DATEINPUT", formatCalendar(c));
	//	setVariable("nextyear", nextyear.toString());
		
		

	} 
	       
	private String formatCalendar(Calendar c)
	{
		
		//String mon = c.getDisplayName(arg0, arg1, arg2)
	    Date tasktime = c.getTime();  
		
		SimpleDateFormat df = new SimpleDateFormat("MMMMM d yyyy");  
	  
		return df.format(tasktime);  
	}

}
