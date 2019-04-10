package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validatePO extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String[] POArray = getVarArray("RFPPOS");
		String[] DOCArray = getVarArray("RFPDOCS");
		
		 String exten = getVariable("EXTEN");
			
		if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);

		
		//String varPO = "PO" + exten;
		
	//	String doc = getVariable(varPO);
		
		boolean ifv = isvalidpo(POArray, exten, DOCArray);
		
		
		if (!ifv)
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
		}
		
		
	//	setVariable("CURPO", exten);
	//	setVariable("CURDOC", doc);
		
		
		
		
		
	//	setVariable("typeofdetect", "ANSWEREDCALL");
		
		String pthank = "Thank you, the P O number you just input is " + exten;
		
		streamString("ponum", pthank);
		
	}
	
	private boolean isvalidpo(String[] poarray, String po, String[] docarray) throws AgiException
	{
		boolean res = false;
		
		for (int i=0; i<poarray.length;i++)
		{
			if (poarray[i].equals(po))
			{
				String doc = docarray[i];
				
				setVariable("CURPO", po);
				setVariable("CURDOC", doc);
				
				res = true;
				break;
			}
			
		}
		
		
		
		return res;
	}
	
	
	private String[] getVarArray(String varname) throws AgiException
	{
		String[] res = null;
		
		String lstr = getVariable(varname);
		
		if (lstr.endsWith(":"))
			lstr = lstr.substring(0, lstr.length()-1);
		
		
		res = lstr.split(":");
		
		
	    return res;
	}
	
	
	
	
	private void streamString(String pre, String prompt)throws AgiException
	{
		
       String unid = getVariable("UNIQUEID");
       
       
       String tmpwav = "/tmp/" + pre + unid;
		
		String[] cmds = new String[3];
		cmds[0] = "/var/javalib/swiftStr.sh";
		cmds[1] = tmpwav + ".wav";
		cmds[2] = prompt;
		
		
		
		 try{
		       
		        Process proc =  Runtime.getRuntime().exec(cmds);

		        
	         if (proc != null)
	         {
	         	proc.waitFor();
	         }


		        
		        
	     }catch(Exception e)
	     {
	     	System.out.println(e);
	     }
		
		
		
		 streamFile(tmpwav);
		 
		 File f = new File(tmpwav + ".wav");
		 
		 f.delete();
		
		
		
		
		
	}
	
	
	

}
