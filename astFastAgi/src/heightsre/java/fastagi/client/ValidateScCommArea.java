package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateScCommArea extends BaseAgiScript {

	String[] commAreas = {"Basement",
			"Roof",
			"Lobby or Hallways or Bulkhead",
			"Boiler Room",
			"Elevator or Elevator Room",
			"Sidewalk, Courtyards, or Backylards",
			"Stairwells or Stairs",
			"Garbage Area",
			"Mailbox Area",
			"Laundry Room or Storage Room",
			"Miscellaneous",	
			"Parking Lot" 

			
	};
	
	
	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String comArea = getVariable("EXTEN");
		if (comArea.endsWith("#"))
			comArea = comArea.substring(0, comArea.length()-1);
		
		int icomArea = Integer.parseInt(comArea);
		
		
		if (icomArea > 12)
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		setVariable("COMAREA", commAreas[icomArea-1]);
		

	}

}
