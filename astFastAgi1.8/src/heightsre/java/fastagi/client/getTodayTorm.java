package heightsre.java.fastagi.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getTodayTorm extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		Calendar cToday = Calendar.getInstance();
		
		Calendar cTomorrow = Calendar.getInstance();
		cTomorrow.add(Calendar.DAY_OF_MONTH, 1);
		
		setVariable("tomMilsec", "" + cTomorrow.getTimeInMillis());
		
		setVariable("tdMilsec", "" + cToday.getTimeInMillis());
        
		setVariable("tddate", formatCalendar(cToday));
	}
	
	private String formatCalendar(Calendar c)
	{
		
		//String mon = c.getDisplayName(arg0, arg1, arg2)
			Date tasktime = c.getTime();  
		
		SimpleDateFormat df = new SimpleDateFormat("MMMMM d yyyy");  
	  
		return df.format(tasktime);  
	}

}
