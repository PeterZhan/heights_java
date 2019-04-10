package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.*;

public class getIfHWStoreTime extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		boolean workingtime = false;
		
		Calendar c = Calendar.getInstance();
		
		int h = c.get(c.HOUR_OF_DAY);
		int w = c.get(c.DAY_OF_WEEK);
		
		workingtime = (h>=7 && h<=17 && w != c.SUNDAY && w != c.SATURDAY);
	
		/*
		if (workingtime)
		{
		   boolean notInHoliday = true;
		
		
		   String[] opts = new String[1];
		   opts[0] = "";
		
		   String cmd = "IFHOLIDAY";
		
		   String url = "http://www.heightsre.com/Examples/TEST/heightscalls.nsf/AstPortal";
	    
		   NotesWSClient wsclient = new NotesWSClient(url);
		
		   String[] res = wsclient.generalCommand(cmd, opts);
		
		   setVariable("testvar", res[0]);
		
		   notInHoliday = res[0].equals("0");
			
		
		   workingtime = workingtime && notInHoliday;
		}*/
		if(workingtime)
		{
			setExtension("officetime");
			setPriority("1");
			
		}else
		{
			setExtension("noworking");
			setPriority("1");
		}
		
		

	}

}
