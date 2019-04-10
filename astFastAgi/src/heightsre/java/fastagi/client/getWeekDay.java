package heightsre.java.fastagi.client;

import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getWeekDay extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		Calendar cNow = Calendar.getInstance();
		
		int wd = cNow.get(Calendar.DAY_OF_WEEK);
		String sweek = "";
		
		switch(wd)
		{
		case Calendar.SUNDAY:
			sweek = "Sunday";
			break;
			
		case Calendar.MONDAY:
			sweek = "Monday";
			break;
			
		case Calendar.TUESDAY:
			sweek = "Tuesday";
			break;
			
		case Calendar.WEDNESDAY:
			sweek = "Wednesday";
			break;
			
		case Calendar.THURSDAY:
			sweek = "Thursday";
			break;
			
		case Calendar.FRIDAY:
			sweek = "Friday";
			break;
			
		case Calendar.SATURDAY:
			sweek = "Saturday";
			break;
			
		default:
			sweek = "";
			
			
		}

		setVariable("WeekDay", sweek);
		setVariable("weekdayprompt", "custom/scp" + sweek);

	}

}
