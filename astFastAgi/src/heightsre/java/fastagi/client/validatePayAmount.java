package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class validatePayAmount extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String exten = getVariable("EXTEN");
		
		
		
		if (exten.endsWith("#")) exten = exten.substring(0, exten.length() -1);
		
		if (!isDigits(exten))
		{
			
			setExtension("s");
			setPriority("begin");
			streamFile("invalid");
			
			return;
			
			
		}
		
		
		int payment = Integer.parseInt(exten);
		
		double fpay = payment/100.0;
		
		setVariable("PAYAMOUNT", Double.toString(fpay));
		
		String pAmountProm = "You have entered $" + fpay;
		
		
		streamString("pamount", pAmountProm);
		
		
		

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
	
	
	public static boolean isDigits(String s){
		if(s==null ||s.length()==0)return false;
		for(int i=0;i <s.length();i++){
		if(!Character.isDigit(s.charAt(i)))return false;
		}
		return true;
		} 

}
