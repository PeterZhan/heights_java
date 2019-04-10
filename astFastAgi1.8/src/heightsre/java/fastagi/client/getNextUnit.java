package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getNextUnit extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String units = getVariable("units");
		String[] unitarray = units.split(":");
		
		
      //  String sgrents = getVariable("sgrents");
		
	//	String[] rentarray = sgrents.split(":");
		
		
		
		
		String curno = getVariable("curno");
		
		int iCurNo = Integer.parseInt(curno);
		
		iCurNo++;
		
		setVariable("curno", "" + iCurNo);
		
		if (iCurNo<=unitarray.length)
		{
		  setVariable("curunit", unitarray[iCurNo-1]);
		//  setVariable("cursgrent", rentarray[iCurNo-1]); 
		}

	}

}
