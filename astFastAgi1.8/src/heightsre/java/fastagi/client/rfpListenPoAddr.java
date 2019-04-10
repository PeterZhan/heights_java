package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class rfpListenPoAddr extends BaseAgiScript {

	@Override
	
	// This address is for listening to the building address
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String[] POArray = getVarArray("RFPPOS");  // get the po numbers array
		String[] ARArray = getVarArray("RFPADDRS"); // get the building address array
		
		
		
		String docnum = getVariable("DOCNUM");  // get number of all the documents
		
		int dnum = Integer.parseInt(docnum); // to integer
		
		String prompt = "";
		
		// create the prompt string 
		for (int i=1; i<=dnum; i++)
		{
			String po = POArray[i-1];
			
			String addr = ARArray[i-1];
			
			if (!addr.endsWith(".")) addr = addr + ",";// avoid it playing "DOT"
			
			prompt = prompt + " The P O number is " + po
			         + ", And the address is " + addr + " ";  // get the prompt
			
			
			
			
		}
		
		
		
		streamString("paall", prompt); // Play the prompt
		
		setExtension("s");  // after playing, return back to the beginning of  the dial plan
		setPriority("begin");
		
		

	}
	// get the array of the variable
	private String[] getVarArray(String varname) throws AgiException
	{
		String[] res = null;
		
		String lstr = getVariable(varname);  // get the variable string
		
		if (lstr.endsWith(":"))
			lstr = lstr.substring(0, lstr.length()-1);  // delete the last character ":"
		
		
		res = lstr.split(":"); // get the array
		
		
	    return res;  
	}
	
	
	// playing any string we needed
	private void streamString(String pre, String prompt)throws AgiException
	{
		
       String unid = getVariable("UNIQUEID");  // get channel uniqueid
       
       
       String tmpwav = "/tmp/" + pre + unid; // define the wav file name
		
		String[] cmds = new String[3];  // use the linux command to get the wav file
		cmds[0] = "/var/javalib/swiftStr.sh";  // the linux shell file
		cmds[1] = tmpwav + ".wav";  // the destination wav file
		cmds[2] = prompt; // the source string
		
		
		
		 try{
		       
		        Process proc =  Runtime.getRuntime().exec(cmds);  //run the linux command

		        
	         if (proc != null)
	         {
	         	proc.waitFor();  // wait till the command is executed
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
