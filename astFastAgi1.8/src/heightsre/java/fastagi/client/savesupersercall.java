package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class savesupersercall extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String bldgCode = getVariable("BlgCode");
		String unitCode = getVariable("UNITNO");
		
		if (unitCode.equals("0"))
			unitCode = getVariable("COMAREA");
		
		String jobtype = getVariable("JOBTYPE");
		String jobcate = getVariable("JOBCATE");
		
		String startdate = getVariable("DATEMILLSECS");
		
		String jobdays = getVariable("jobdays");
		
		String whoProMat = getVariable("whoprov");
		
		String whodeli = getVariable("whodeli");
		
		String reclen = getVariable("reclen");
		
		String vendor = getVariable("VENDOR");
		
		String wkernum = getVariable("wkernum");
		
		
		String rec = getVariable("sserrecording");  // get the channel unique id
		 
	try{ 
	    String fname =  rec + ".WAV"; 
	    
	    File f = new File("/var/spool/asterisk/monitor/" + fname);
	
	    long fsize = f.length();
	   	byte[] data = new byte[(int)fsize];
	   	
	    FileInputStream fin = new FileInputStream(f);
	   	
	    fin.read(data);
	   	
	   	fin.close();
	   	
	    String[] opt = new String[12];
	     
	     // these are the options for the service command
       opt[0] = bldgCode;
       opt[1] = unitCode;
       opt[2] = jobtype;
       opt[3] = jobcate;
       opt[4] = startdate;
       opt[5] = jobdays;
       opt[6] = whoProMat;
       opt[7] = whodeli;
       opt[8] = fname;
       opt[9] = reclen; 
       opt[10] = vendor;
       opt[11] = wkernum;
       
       String cmd = "SAVESUPERSERCALL"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt, data);	
	   	
	   setVariable("PONUM", res[0]);	
	   setVariable("VENDORPHONE", res[1]);
	   setVariable("DOCID", res[2]);
	   	
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
