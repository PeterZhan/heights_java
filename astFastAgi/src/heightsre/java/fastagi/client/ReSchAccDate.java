package heightsre.java.fastagi.client;

import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.io.File;
import java.text.*;

public class ReSchAccDate extends BaseAgiScript {

	@Override
	// this agi is for user re-scheduled the access date
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
        String exten = getVariable("EXTEN");  // get the day user input
		
		if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);// remove the "#" at the end of the String
		
		 
		
		int dayOfMonth = Integer.parseInt(exten);
		Calendar c = Calendar.getInstance();
		
        // get the max days of this month
		int maxdays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		
		// if the day number input is larger than the maxinum day of this month
		// then play "invalid", and prompt to re-input
		if (!(dayOfMonth > 0 && dayOfMonth < maxdays + 1))
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
		}
		
		// today
		int currentDay = c.get(Calendar.DAY_OF_MONTH);
		
		
		// if this month
		if (dayOfMonth >= currentDay)
			c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		else// if next month
		{
			int month = c.get(Calendar.MONTH);
			
			int year = c.get(Calendar.YEAR);
			
			month = month + 1;
			
			if (month > 12) 
				{
				year = year + 1;
				month = month - 12;
				}
			
			
			
			c.set(year, month, dayOfMonth);
		
		
		}
		
		// set the date format
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE,MMMM dd,yyyy");
		
		
		String reScheduledDate = dateFm.format(c.getTime());
		
		// set the reSchedule Date
		setVariable("RESCHDATE", reScheduledDate);
		
		
		
		String streamText = "You have selected " + reScheduledDate;
		
		
		
		// invoke the linux shell command to transfer the text to wave
		String unid = getVariable("UNIQUEID");
		
		String[] cmds = new String[3];
		cmds[0] = "/var/javalib/swiftStr.sh";
		cmds[1] = "/tmp/sel" + unid + ".wav";
		cmds[2] = streamText;
		
		
		
		 try{
		       // transfer the text to wave
		        Process proc =  Runtime.getRuntime().exec(cmds);

		        
	         if (proc != null)
	         {
	         	proc.waitFor();
	         }


		        
		        
	     }catch(Exception e)
	     {
	     	System.out.println(e);
	     }
		
		
		// play the rescheduled date
		 streamFile("/tmp/sel" + unid);
		 
		 File f = new File("/tmp/sel" + unid + ".wav");
		 
		 f.delete();  // delete the temporay wave file.
		
		
		
		
		
		
		
		
		

	}

}
