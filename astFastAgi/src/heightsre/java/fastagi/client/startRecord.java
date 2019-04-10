package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;

import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.util.*;

public class startRecord extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		
	Date d = new Date();
	setVariable("RECSTART", "" + d.getTime()/1000);
	setVariable("RECSAVED", "NO");
		
		
		// TODO Auto-generated method stub

	}

}
