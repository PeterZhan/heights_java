package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savecompsuper extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String fname = " ";
        fname = getVariable("compsuper") + ".WAV";
        
                
        
        
       
       if (fname==null) fname = " ";
       setVariable("RET", fname);
       
       
       String tendoc = getVariable("TENDOC");
		
		if (tendoc == null)
			tendoc = "";
       
       String noten = getVariable("NOTEN");
		
		if (noten == null)
			noten = "0";
       
		
		String conSup = getVariable("SHARED(SPANSWERED)");
		
		if (conSup == null)
			conSup = "No";
     //  String fname2 = getVariable("comprecording") + ".WAV";
		
		String[] opts = new String[9];
		opts[0] = getVariable("COMPDOC");
		opts[1] = fname;
		opts[2] = tendoc;
		opts[3] = getVariable("TENCID");
		opts[4] = getVariable("CALLERID(name)");
		opts[5] = getVariable("compifold");
		opts[6] = getVariable("comptype");
		opts[7] = noten;
		opts[8] = conSup;
		
		
		
		
		byte[] data = null;
		/*
		if (opts[7].equals("1"))
		{
			/*File f = new File("/var/spool/asterisk/monitor/" + fname2);
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
			data = new byte[1];
			data[0] = 1;
			
			setVariable("RET1", "NODATA");
		
		} else */
		{
		
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
    		   setVariable("RET1", e.toString());
    	   }
    	   
		}
    	   String cmd = "SAVECOMPSUPER";
   		
   		
   		
   		
   		String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		setVariable("RET1", "START noten=" + opts[7]);
   		
   		String[] res = wsclient.generalCommand(cmd, opts, data);
		
		
		setVariable("RET1", res[0]);
		
		
		//new String(data);

	}

}
