package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class rfpListenPos extends BaseAgiScript {

	@Override
	
	// this web service is for listening to the PO number
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String[] POArray = getVarArray("RFPPOS");  // get the PO number array
		
        String docnum = getVariable("DOCNUM");  // get the document number
		
		int dnum = Integer.parseInt(docnum);  // to integer
		
		String prompt = "The p O numbers are";
		
		
		// create the P O numbers prompt string
		for (int i=1; i<=dnum; i++)
		{
			String po = POArray[i-1];
			
		
			
			prompt = prompt  + po + ",";
			        
			
			
			
			
		}
		
		
		// play the PO numbers
		streamString("poall", prompt);
		
		setExtension("s");  // make the dial plan to the beginning
		setPriority("begin");
		

	}
	
	
	// get the array of the variable
	private String[] getVarArray(String varname) throws AgiException
	{
		String[] res = null;
		
		String lstr = getVariable(varname); // get the variable value string
		
		if (lstr.endsWith(":"))
			lstr = lstr.substring(0, lstr.length()-1); // delete the last : character
		
		
		res = lstr.split(":"); // split the string by :
		
		
	    return res;
	}
	
	
	// play the string
	private void streamString(String pre, String prompt)throws AgiException
	{
		
       String unid = getVariable("UNIQUEID");  // get the channel uniqueid
       
       
       String tmpwav = "/tmp/" + pre + unid;  // get the wav file name without extension
		
		String[] cmds = new String[3];  // command array to covert the text to wav file
		cmds[0] = "/var/javalib/swiftStr.sh";
		cmds[1] = tmpwav + ".wav";
		cmds[2] = prompt;
		
		
		
		 try{
		       
		        Process proc =  Runtime.getRuntime().exec(cmds);  // invoke the linux shell command to convert

		        
	         if (proc != null)
	         {
	         	proc.waitFor();  // wait until the command finished
	         }


		        
		        
	     }catch(Exception e)
	     {
	     	System.out.println(e);
	     }
		
		
		
		 streamFile(tmpwav);  // play the wav file
		 
		 File f = new File(tmpwav + ".wav");
		 
		 f.delete();  // delete the temporay wav file.
		
		
		
		
		
	}
	

}
