package heightsre.java.prog;
import java.util.*;

public class thRemoveRec extends Thread {
	
	
	
	public void run()
	{
		
		
		while(true)
		{
		  Date dtNow = new Date();
		  long secs = dtNow.getTime()/1000;
		  dtNow = null;
		  
		  secs = secs - 30;
		
		  String sql = "delete from callingrecord where UniID < " + secs;
		
		  DbConnection.sqlExecute(sql);
		  try{
		    sleep(30*1000);
		  }catch(Exception e)
		  {
			
		  }
		}
	}
	
	

}
