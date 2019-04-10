package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class saverfpExt766 extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String bldgCode = getVariable("BlgNo");
		String unitCode = getVariable("UNITNO");
		
		if (unitCode.equals("0"))
			unitCode = getVariable("COMAREA");
		
		String condition = getVariable("CONDITION");
		String whoprov = getVariable("WHOPROV");
		
		String wkernum = getVariable("WKNUM");
		
		
	    String daystart = getVariable("DAYSTART");
		
		
	    String dayscomp = getVariable("DAYCOMPL");
		
		
	    String daysdeci = getVariable("DAYDECI");
		
		
	    
	    String daysubm = getVariable("DAYSUBM");
		
	    
	    
	    String jobcate = getVariable("JOBCATE");
	    
	    
	    String jobtype = getVariable("JOBTYPE");
	    
	    
	    String action = getVariable("ACTION");
	    
	    String room = getVariable("ROOM");
	    
	    String item = getVariable("ITEM");
	    
	    String method = getVariable("method");
	    
	    String faxnum = getVariable("FAXNUM");
	    
	    
	    
	    
	    
		String reclen = getVariable("reclen");
		
		
		
		
		
		
		
		
		
		String rec = getVariable("rfprecording");  // get the channel unique id
		 
	try{ 
	    String fname =  rec + ".WAV"; 
	    
	    File f = new File("/var/spool/asterisk/monitor/" + fname);
	
	    long fsize = f.length();
	   	byte[] data = new byte[(int)fsize];
	   	
	    FileInputStream fin = new FileInputStream(f);
	   	
	    fin.read(data);
	   	
	   	fin.close();
	   	
	    String[] opt = new String[19];
	     
	     // these are the options for the service command
       opt[0] = bldgCode;
       opt[1] = unitCode;
       opt[2] = jobtype;
       opt[3] = jobcate;
       opt[4] = daystart;
       opt[5] = dayscomp;
       opt[6] = whoprov;
       opt[7] = daysdeci;
       opt[8] = fname;
       opt[9] = reclen; 
       opt[10] = daysubm;
       opt[11] = wkernum;
       
       opt[12] = room;
       opt[13] = item;
       opt[14] = method;
       opt[15] = faxnum;
       opt[16] = action;
       opt[17] = condition;
       
       opt[18] = getVariable("CALLERID(num)");
       
       
       String cmd = "SAVERFPEXT776"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt, data);	
	   	
	 //  setVariable("PONUM", res[0]);	
	 //  setVariable("VENDORPHONE", res[1]);
	   	
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
