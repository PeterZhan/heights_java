package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saveextersvr extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		boolean NOTEN= false;
        String noten = getVariable("NOTEN");
		
		if (noten == null)
			noten = "0";
		
		if(noten.equals("1")) NOTEN=true;
		
		
		
		
		String vdrDoc = "";
		if (!NOTEN) vdrDoc = getVariable("VDRDOC");
		String bldgCode = "";
		if (!NOTEN) bldgCode = getVariable("BlgCode");
		String aptno = "";
		if (!NOTEN)
			aptno = getVariable("UNITNO");
		
		String tenant = "";
		if (!NOTEN)
			tenant = getVariable("TENANT");
		
	    String extype = getVariable("extype");
		
		String fname = getVariable("exterecording") + ".WAV";
		
		String actype = getVariable("actype");
		
				
		
	    String[] opts = new String[8];
		opts[0] = vdrDoc;
		
		opts[1] = fname;
		
		opts[2] = bldgCode;
		
		opts[3] = aptno;
		
		opts[4] = extype;
		
		opts[5] = noten;
		
		opts[6] = tenant;
		
		opts[7] = actype;
		
	
		
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
		
		
		
		
		
		//*******************************
		
		String cmd = "SAVEEXTERSERVICE";
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/Exterm.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		
		
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
