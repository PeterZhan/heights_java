package heightsre.java.prog;
import org.asteriskjava.manager.event.*; 
import java.io.*;

public class NewLocalChannelThread extends Thread {
	
	NewChannelEvent e = null;
	public NewLocalChannelThread(NewChannelEvent nce)
	{
		e = nce;
	}
	
	
	public void run()
	{
		String lexten = getLocalExtenFromChann(e.getChannel());
		
		if (!lexten.equals(""))
	    {
			String lgf = "custom/lgpn" + lexten;
			String personLgP = "/var/lib/asterisk/sounds/" + lgf + ".wav";
			
			File f = new File(personLgP);
			
			if (!f.exists())
				lgf = "custom/moniprompt";
			
			
			AstConnection.setVariable(e.getChannel(), "__LGPNAME", lgf);
			
		}
		
		
		
	}
	
	private String getLocalExtenFromChann(String chann)
	{
		String ret = "";
		
		int ps = chann.indexOf('/');
		int pe = chann.indexOf('-');
		
		if (ps > -1 && pe > -1 && pe > ps)
		{
		
		   String exten = chann.substring(ps + 1, pe);
		   if (exten.length() == 3) ret = exten;
		
		}
		
		return ret;
	}
	

}
