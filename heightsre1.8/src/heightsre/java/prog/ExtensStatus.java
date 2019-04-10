package heightsre.java.prog;
import java.util.*;

public class ExtensStatus {
	private HashMap<String, String> ExtenStatusMap = new HashMap<String, String>();
	
	 public synchronized void  setExtenStatus(String exten, String status) 
	{
	//	System.out.println("add exten status " + exten + ":" + status);
		
		ExtenStatusMap.put(exten, status);
	}
	
	
	 public synchronized HashMap<String, String> getAllExtenStatus()
	{
		
				
		HashMap<String, String> ret = new HashMap<String, String>();
		if (!ExtenStatusMap.isEmpty())
		{
		   ret.putAll(ExtenStatusMap);
		
		   ExtenStatusMap.clear();
		}
		
		return ret;
	}
	

}
