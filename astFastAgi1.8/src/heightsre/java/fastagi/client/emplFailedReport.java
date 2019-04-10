package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class emplFailedReport extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		    String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
	       
	      //  setVariable("RECSAVED", "LATER");
	        
			String[] res = null;
	        String docid = getVariable("DOCID");
	        
	        String recFile = getVariable("EMPLRECALL");
	        
	        String fname = recFile + ".WAV";
	        
			
			String cmd = "EMPLFAILEDREP";
			String[] options = new String[2];
			
			options[0] = docid;
			options[1] = fname;
			
			try{
				// AstPortalProxy service = new AstPortalProxy();  
		 	//	 service.setEndpoint(url);
		 		NotesWSClient wsclient = new NotesWSClient(url);
				
			//	NotesWSClient wsclient = new NotesWSClient(url);
				
				
				
				
	            File f = new File("/var/spool/asterisk/monitor/" + fname);
		    	boolean fe = f.exists();
	            
		    	if (fe)
		    	{
		    	long fsize = f.length();
		    	byte[] data = new byte[(int)fsize];
		    	
		    	FileInputStream fin = new FileInputStream(f);
		    	
		    	fin.read(data);
		    	
		    	fin.close();	
		    	  res = wsclient.generalCommand(cmd, options, data);//service.generalCommand(cmd, options, data);
		    	} else
		    		 res = wsclient.generalCommand(cmd, options);	
				
				
				
				
			
	 		 
			}
			catch(Exception e)
			{
				setVariable("RETVAL", e.toString());
				System.out.println(e);
			}
			finally
			{
				hangup();
			}

	}

}
