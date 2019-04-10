package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class faxnumplay extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String faxnum = getVariable("FAXNUM");
		
		if (faxnum.endsWith("#"))
			faxnum = faxnum.substring(0,faxnum.length()-1);
		
		String faxnumblk = "";
		 for (int i=0;i<=faxnum.length()-1;i++)
		   {
			   faxnumblk = faxnumblk + faxnum.substring(i,i+1) + " ";
		   }
		   
		   if (faxnumblk.endsWith(" "))
			   faxnumblk = faxnumblk.substring(0, faxnumblk.length()-1);
		   
		   
		   
		   setVariable("FAXNUMBLK", faxnumblk);
		
		

	}

}
