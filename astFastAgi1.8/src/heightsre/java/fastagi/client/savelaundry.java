package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savelaundry extends BaseAgiScript {

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
		
		String fname = getVariable("ladryrecording") + ".WAV";	
		
		
		String aptno = "";
		if (!NOTEN) bldgCode = getVariable("APTNO");
		
		String tenant = "";
		if (!NOTEN) tenant = getVariable("TENANT");
		
		String tendoc = "";
		if (!NOTEN) tendoc = getVariable("TENDOC");
		
		 String[] opts = new String[7];
			opts[0] = vdrDoc;
			
			opts[1] = fname;
			
			opts[2] = bldgCode;
			
			opts[3] = noten;
			
			opts[4] = aptno;
			
			opts[5] = tenant;
			
			opts[6] = tendoc;
		
			
		
			
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
			
			String cmd = "SAVELAUNDRY";
			
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/Machine.nsf/AstPortal";
		    
			NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand(cmd, opts, data);
			
			
			
			
			if (NOTEN)
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
				
				
				
				
				
				
				
				
				
				
			} else
			{
				
				String[] args = new String[2];
				args[0] = res[0];
				String fname2 = getVariable("callvdr") + ".WAV";
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
		    	   
		    	   
		    	   String cmd2 = "SAVELAUNDRYVDR";
		   		
		   		
		   		
		   		
		   		//String url = "http://www.heightsre.com/Examples/Test/TenComplain.nsf/AstPortal";
		   	    
		   		//NotesWSClient wsclient = new NotesWSClient(url);
		   		
		   		String[] res2 = wsclient.generalCommand(cmd2, args, data2);
				
				
				
				
				
			}
			
		
		
		
		

	}

}
