package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validatWkNum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String exten = getVariable("EXTEN");
		
		if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);

		
	    boolean d = validatePayAmount.isDigits(exten);
		
		if (!d)
		{
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
		}
		
		
		setVariable("WKNUMBER", exten);
		
		streamString("wknum", "You are reporting " + exten + " workers at the jobsite today");


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
