package heightsre.java.fastagi.client;

import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getSeasonPrompt extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String seasonP = getVariable("pOilheatSeason");
		String nonseasonP = getVariable("pOilNonheatSeason");
		
		String p = nonseasonP;
		
		
		Calendar cNow = Calendar.getInstance();
		
		Calendar cOct1 = Calendar.getInstance();
		cOct1.set(Calendar.MONTH, Calendar.OCTOBER);
		cOct1.set(Calendar.DAY_OF_MONTH, 1);
		cOct1.set(Calendar.HOUR_OF_DAY, 0);
		cOct1.set(Calendar.MINUTE,0);
		cOct1.set(Calendar.SECOND, 0);
		
		
		Calendar cApr30 = Calendar.getInstance();
		cApr30.set(Calendar.MONTH, Calendar.APRIL);
		cApr30.set(Calendar.DAY_OF_MONTH, 30);
		cApr30.set(Calendar.HOUR_OF_DAY, 23);
		cApr30.set(Calendar.MINUTE,59);
		cApr30.set(Calendar.SECOND, 59);
		
		
		
		if (cNow.compareTo(cApr30)<0)
			p = seasonP;
		
		if (cNow.compareTo(cOct1)>0)
			p = seasonP; 
		
		
		setVariable("pSeasonPrompt", p);

	}

}
