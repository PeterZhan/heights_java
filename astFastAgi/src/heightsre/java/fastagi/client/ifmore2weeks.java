package heightsre.java.fastagi.client;

import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ifmore2weeks extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String sdate = getVariable("STARTDATE");
		String edate = getVariable("ENDDATE");
		
		long lsdate = Long.parseLong(sdate);
		long ledate = Long.parseLong(edate);
		
		long d = (ledate-lsdate)/(24*60*60*1000) + 1;
		
		if (d <= 0)
		{
			setExtension("dateinvalid");
			setPriority("1");
		}
		
		if(d>14)
		{
			setContext("More2WeeksVa");
			setExtension("s");
			setPriority("1");
		}
		
		
		

	}

}
