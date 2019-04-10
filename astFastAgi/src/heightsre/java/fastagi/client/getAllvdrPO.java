package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllvdrPO extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

        String bldgCode = getVariable("BlgCode");
		
        String onemode = getVariable("onemode");
		
		String[] opts = new String[2];
		opts[0] = bldgCode;
		opts[1] = onemode;
		
	 	   
    	 String cmd = "GETALLVDRPOINFOS";
   		
   		
   		
   		  
   		String url = "http://www.heightsre.com/Examples/Test/poreq.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts);
		
   		String sNum = res[0];
   		int iNum = Integer.parseInt(sNum);
   		
   		setVariable("vdrnum", sNum);

        if (iNum > 0)
        {
        	for (int i=1; i<=iNum; i++)
        	{
        	  setVariable("PO" + i, res[i]);	
        		
        		
        	}
        	
        	
        	
        }
   		
		
		
		
	}

}
