package heightsre.java.fastagi.client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validatePayDay extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		    String exten = getVariable("EXTEN");
		
			if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);
			
			 
			
			int dayOfMonth = Integer.parseInt(exten);
			
			Calendar c = Calendar.getInstance();
			
			int maxdays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			if (!(dayOfMonth > 0 && dayOfMonth < maxdays + 1))
			{
				setExtension("s");
				setPriority("begin");
				streamFile("invalid");
				
				return;
				
			}
			
		    setVariable("PAYDAY", exten);
		    
		    int dtoday = c.get(Calendar.DAY_OF_MONTH);
		    
		    String timebe = " was ";
		    
		    if (dayOfMonth > dtoday)
		    	timebe = " will be ";
		    
		    
			c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			
			
			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE,MMMM dd,yyyy");
			
			
			String paytime = dateFm.format(c.getTime());
			
			String payprompt = "You are informing us that your payment" + timebe + "sent on " + paytime;
			
			
			
			streamString("payday", payprompt);	
			
		
		

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
