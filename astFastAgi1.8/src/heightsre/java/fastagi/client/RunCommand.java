package heightsre.java.fastagi.client;
import java.io.*;

public class RunCommand {
	
	public static String runit(String cmds[])
	{
		String s;
		String ret=null;
		try{
		       
	        Process proc =  Runtime.getRuntime().exec(cmds);
      
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
           
            
            while((s=bufferedReader.readLine()) != null)
            	ret = s;
            
            
            return ret;
        }catch(Exception e)
        {
        	return e.toString();
        }
        
		
		
		
		
		
	}
	 

}
