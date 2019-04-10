package heightsre.java.fastagi.client;

import java.util.Calendar;


import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class checkoffworkinghours extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		int indx = -1;
		String holiday = getVariable("HOLIDAYTODAY");
		if (holiday == null) holiday = "";
		String OpenOrClosed = getVariable("OpenOrClosed");

		if (holiday.equals("1") && OpenOrClosed.equals("Closed"))
			indx = 6;
		else {
			int startTime = 9;
			int endTime = 17;

			Calendar cal = Calendar.getInstance();
			int h = cal.get(Calendar.HOUR_OF_DAY);
			int w = cal.get(Calendar.DAY_OF_WEEK);

			if (w == Calendar.SATURDAY || w == Calendar.SUNDAY)
				indx = 3;
			else {
             //   h = 6;
				if (h < startTime)
					indx = 1;
				else {
					if (h >= endTime) {
						if (w != Calendar.FRIDAY)
							indx = 2;
						else
							indx = 3;
					}
				}
			}
		}
		if (indx != -1) {
			

			String callerid = getVariable("CALLERID(num)");
			String[] opts = new String[1];
			opts[0] = callerid;

			String cmd = "GETTENANTINFO";

			String url = "http://www.heightsre.com/Examples/Test/Certmail.nsf/AstPortal";

			NotesWSClient wsclient = new NotesWSClient(url);

			String[] res = wsclient.generalCommand(cmd, opts);

			if (!res[6].equals("Active")) return;
				
			setVariable("offwork", "Yes");
			setVariable("indx", "" + indx);
			
			boolean isTenant = res[0].equals("1");
			String superExt = res[1];
			String bldg_no = insertBlankOfBldg(res[2]);
			String aptNo = insertBlank(res[3]);
			
			
			String customerName = res[4];
			String superName = res[5];

			if (isTenant) {
				setVariable("isTenant", "Yes");
				setVariable("SUPEREXTEN", superExt);
				setVariable("BLDGNO", bldg_no);
				setVariable("APTNO", aptNo);
				setVariable("TENANT", customerName);
				setVariable("__SUPER", superName);
				setVariable("SUPERCELLS", res[7]);
				
			}
			setVariable("TENANTDOC", res[8]);

		}

	}
	
	
	private String insertBlankOfBldg(String s)
	{
		String sArray[] = s.split(" ");
		if (sArray.length > 0)
		{
			sArray[0] = insertBlank(sArray[0]);
			s = sArray[0];
			for (int i =1; i<sArray.length; i++)
			{
				s += " " + sArray[i];
			}
			
		}
		
		
		return s;
	}
	
	
	private String insertBlank(String s)
	{
		s = s.replace("", " ").trim();
		
		
		return s;
	}

}
