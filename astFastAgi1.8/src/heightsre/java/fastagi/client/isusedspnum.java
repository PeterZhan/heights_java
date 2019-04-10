package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class isusedspnum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String docid = getVariable("DOCID");
		String spnum = getVariable("EXTEN");
		if (spnum.endsWith("#")) spnum = spnum.substring(0, spnum.length() -1);

		String[] opts = new String[2];
		opts[0] = docid;
		opts[1] = spnum;
		
        String cmd = "ISUSEDSPNUM";
		
		
        String url = "http://www.heightsre.com/Examples/Test/TrackVdr.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String isused = res[0];
		
		
		if (isused.equals("1"))
		{
			setExtension("inused");
			setPriority("1");
			return;
		}
		
		setVariable("SPDIALNUM", spnum);
		setVariable("SPDIALNUMFORPLAY", InsertBlankToString(spnum));

	}
	
	
	private String InsertBlankToString(String src)
    {
   	 String ret = "";
   	// StringBuffer bfsrc = new StringBuffer(src);
   	
   	 for (int i=0; i<src.length();i++)
   	   ret = ret + src.charAt(i) + " ";
   	
   	
   	 if (ret.endsWith(" "))
   		 ret = ret.substring(0,ret.length()-1);
   	 
   	 return ret;
    }

}
