package heightsre.java.prog;
import java.util.*;

public class SyncEntry {
	private HashMap<String, channelInfo> channels 
	        = new HashMap<String, channelInfo>();
	        
	        
	public synchronized void putin(String unid, channelInfo info)  
	
	{
		channels.put(unid, info);
		
		
	}
	
	public synchronized channelInfo getout(String unid)
	{
		channelInfo info = channels.get(unid);
		
		channels.remove(unid);
		
		return info;
		
		
	}
	
	public synchronized void updateStartDialvalue(String unid, Date value)
	{
		
		channelInfo info = channels.get(unid);
		
	
			info.startDial = value; 
		
		
		
	}
	
	
	

}
