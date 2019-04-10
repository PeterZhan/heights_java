package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savecomplaint extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String compifold = getVariable("compifold");
		
		String fname = getVariable("comprecording") + ".WAV";
		String reclen = getVariable("reclen");
		
		String noten = getVariable("NOTEN");
		
		if (noten == null)
			noten = "0";
		
		String tenaddr = getVariable("TENADDR");
		
		
		if (tenaddr == null) tenaddr = "";
		
		String aptno = getVariable("APTNO");
		
		if (aptno == null)
			aptno = "";
		
		
        String tendoc = getVariable("TENDOC");
		
		if (tendoc == null)
			tendoc = "";
		
		
		
		String[] opts = new String[10];
		opts[0] = compifold;
		opts[1] = tendoc;
		
		if (compifold.equals("new"))
		{
			opts[2] = getVariable("comptype");
			opts[3] = "";
			
			opts[5] = getVariable("servprob");
			opts[6] = tenaddr;
			opts[7] = aptno;
		}else
		{
			if (compifold.equals("old"))
			{
			   opts[2] = "";
			   opts[3] = getVariable("COMPLAINT");
			   
			   opts[5] = "";
			   opts[6] = "";
			   opts[7] = "";
			}
		
		}
		
		opts[4] = fname;
		
		opts[8] = reclen;
		
		opts[9] = noten;
		
		byte[] data = null;
		
		
		
		File f = new File("/var/spool/asterisk/monitor/" + fname);
		//******************************
		   long fsize = f.length();
    	   data = new byte[(int)fsize];
    	
    	   try{
    	     FileInputStream fin = new FileInputStream(f);
    	
    	     fin.read(data);
    	
    	     fin.close();	
		
    	   }catch (Exception e)
    	   {
    		
    	   }
    	   
    	   
    	   String cmd = "SAVECOMPLAINT";
   		
   		
   		
   		
   		String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		setVariable("COMPDOC", res[0]);
		setVariable("complain", res[1]);
		
		if (!noten.equals("1"))
		{
			setVariable("sext", res[2]);	
			
		}
		
		
		String complain = res[1];
		String complaintB = "";
		
		for (int i=0; i<complain.length(); i++)
		{
		   complaintB = complaintB + complain.substring(i,i+1) + " ";	
		}
		
		if (complaintB.endsWith(" "))
			complaintB = complaintB.substring(0, complaintB.length()-1);
		
		
		setVariable("complainB", complaintB);
		
		
		if (noten.equals("1"))
		{
			String[] args = new String[2];
			args[0] = res[0];
			String fname2 = getVariable("tenaddr") + ".WAV";
			args[1] = fname2;
			
			byte[] data2 = null;
			
			File f2 = new File("/var/spool/asterisk/monitor/" + fname2);
			//******************************
			   long fsize2 = f2.length();
	    	   data2 = new byte[(int)fsize2];
	    	
	    	   try{
	    	     FileInputStream fin = new FileInputStream(f2);
	    	
	    	     fin.read(data2);
	    	
	    	     fin.close();	
			
	    	   }catch (Exception e)
	    	   {
	    		
	    	   }
	    	   
	    	   
	    	   String cmd2 = "SAVETENADRREC";
	   		
	   		
	   		
	   		
	   		//String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
	   	    
	   		//NotesWSClient wsclient = new NotesWSClient(url);
	   		
	   		String[] res2 = wsclient.generalCommand(cmd2, args, data2);
			
			
			
			
			
			
			
			
			
			
		}
		

	}

}
