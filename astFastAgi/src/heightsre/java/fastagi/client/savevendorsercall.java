package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savevendorsercall extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String bldgCode = getVariable("BlgCode");
		String jobtype = getVariable("JOBTYPE");
		String unitno = getVariable("UNITNO");
		
		String hasw9 = getVariable("HASW9");
		if (hasw9 == null | hasw9.equals(""))
			hasw9 = "Yes";
		//else
		//	hasw9 = "No";
		
		
		String hasins = getVariable("HASINS");
		if (hasins == null | hasins.equals(""))
			hasins = "Yes";
		//else
		//	hasins = "No";
		
		
		String commArea = "";
		
		if (unitno.equals("0"))
		  commArea = getVariable("COMAREA");
		
		String probStart = getVariable("PROMSTART");
		
		String ifEmerg = getVariable("ifemerg");
		
		String vendor = getVariable("VENDOR");
		
		
		String rec = getVariable("vserrecording");  // get the channel unique id
		
		String reclen = getVariable("reclen");
		try{ 
		    String fname =  rec + ".WAV"; 
		    
		    File f = new File("/var/spool/asterisk/monitor/" + fname);
		
		    long fsize = f.length();
		   	byte[] data = new byte[(int)fsize];
		   	
		    FileInputStream fin = new FileInputStream(f);
		   	
		    fin.read(data);
		   	
		   	fin.close();
		   	
		    String[] opt = new String[11];
		     
		     // these are the options for the service command
	       opt[0] = bldgCode;
	       opt[1] = unitno;
	       opt[2] = jobtype;
	       opt[3] = commArea;
	       opt[4] = probStart;
	       opt[5] = ifEmerg;
	  
	       opt[6] = fname;
	       opt[7] = vendor;
	       opt[8] = reclen;
	       
	       opt[9] = hasw9;
	       opt[10] = hasins;
	        
	       String cmd = "SAVEVENDORSERCALL"; 
		   	
	       String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
		    
		   NotesWSClient wsclient = new NotesWSClient(url);
			
		   String[] res = wsclient.generalCommand(cmd, opt, data);	
		   	
		   setVariable("PONUM", res[0]);
		   setVariable("DOCID", res[1]);
		   	
		}
		   	catch(Exception e)
			{
		 		setVariable("vartest", e.toString());
			    System.out.println(e);	 
			}finally
			{
			  	// hangup();
			}
		
		
		
		
		

	}

}
