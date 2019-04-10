package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateBuildingCode extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String blgCode = getVariable("EXTEN");
		
		if (blgCode.endsWith("#")) 
			blgCode = blgCode.substring(0, blgCode.length() -1);
		
		
		String cmd = "GETBUILDINGBYCODE";
		String[] opts = new String[1];
		opts[0] = blgCode;
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/Building.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("1"))
		{
			setVariable("BldgStreetNum", res[1]);
			setVariable("BldgStreetName", res[2]);
			
			setVariable("BlgCode", blgCode);
			setVariable("BlgNo", res[3]);
			
			String faxnum = res[4];
			String faxnumblk = "";
			
			String bldgNum = res[1];
			String bldgNumblk = "";
			for (int i=0; i<=bldgNum.length()-1;i++)
			{
				bldgNumblk = bldgNumblk + bldgNum.substring(i,i+1) + " ";
				
				
			}
			
			if (bldgNumblk.endsWith(" "))
				bldgNumblk = bldgNumblk.substring(0, bldgNumblk.length()-1);
			   
			setVariable("BldgStreetNumBk", bldgNumblk);
			
			for (int i=0;i<=faxnum.length()-1;i++)
			   {
				   faxnumblk = faxnumblk + faxnum.substring(i,i+1) + " ";
			   }
			   
			   if (faxnumblk.endsWith(" "))
				   faxnumblk = faxnumblk.substring(0, faxnumblk.length()-1);
			   
			   
			   setVariable("FAXNUM", faxnum);
			   setVariable("FAXNUMBLK", faxnumblk);
			   
			String cell = res[5];
			setVariable("sext", cell);
			
			
			setVariable("SUPER", res[6]);
			
		//	setVariable("JOBTITLE", res[7]);
			
			setVariable("BLGDOCID", res[8]);
			
			setExtension("codefound");
			setPriority("1");
			
			
			
		} else
		{
			setExtension("notfound");
			setPriority("1");
		}
		
		
		

	}

}
