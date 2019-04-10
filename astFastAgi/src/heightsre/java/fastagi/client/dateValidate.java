package heightsre.java.fastagi.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class dateValidate extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String dt = getVariable("EXTEN");
		
		if (dt.endsWith("#")) 
			dt = dt.substring(0, dt.length() -1);
			
		if (dt.length() != 4)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		String strMon = dt.substring(0, 2);
		String strDay = dt.substring(2);
		
		int mon = Integer.parseInt(strMon);
		int day = Integer.parseInt(strDay);
		
		
		if (mon<1 || mon>12 || day<1 || day>31)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		Calendar c = Calendar.getInstance();
		
		
		int nowMon = c.get(Calendar.MONTH) + 1;
		int nowDay = c.get(Calendar.DAY_OF_MONTH);
		
		Boolean nextyear = false;
		if (mon < nowMon || (mon == nowMon && day < nowDay))
		{
			c.set(c.YEAR,c.get(c.YEAR) + 1);
			nextyear = true;
		}
		
		c.set(c.MONTH, mon -1);
		
		if (day > c.getMaximum(c.DAY_OF_MONTH))
		{
			setExtension("i");
			setPriority("1");
			return;
			
		}
		
		c.set(c.DAY_OF_MONTH, day);
			
		setVariable("DATEMILLSECS", "" + c.getTimeInMillis());	
		setVariable("DATEINPUT", formatCalendar(c));
		setVariable("nextyear", nextyear.toString());
		
		

	} 
	       
	private String formatCalendar(Calendar c)
	{
		
		//String mon = c.getDisplayName(arg0, arg1, arg2)
	    Date tasktime = c.getTime();  
		
		SimpleDateFormat df = new SimpleDateFormat("MMMMM d yyyy");  
	  
		return df.format(tasktime);  
	}

}
