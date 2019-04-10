package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class superRegReport extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			
		    String resemp = getVariable("RSLT");
			String empdocid = "";
			if (resemp.equals("Found"))
			   empdocid = getVariable("EMPDOCID");
			String blgdocid = getVariable("BLGDOCID");
			
			String fname = getVariable("sprep") + ".WAV";
			String flength = getVariable("reclen");
			
			
			String callerid = getVariable("CALLERID(num)");
			
			
			
			
			String vendornum = getVariable("vendornum");
			String complainnum = getVariable("complainnum");
			String openitemnum = getVariable("openitemnum");
			
			if (vendornum == null) vendornum = "0";
			if (complainnum == null) complainnum = "0";
			if (openitemnum == null) openitemnum = "0";
			
			
			
			String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
			
			
			String cmd = "NEWSUPERDAILYCALLREPORT";
			String[] options = new String[9];
			
			options[0] = empdocid;
			options[1] = fname;
			options[2] = flength;
			options[3] = vendornum;
			options[4] = complainnum;
			options[5] = openitemnum;
			options[6] = callerid;
			options[7] = resemp;
			options[8] = blgdocid;
			
			
			
			
		    try{
		    	
		    	
		    	
		    	File f = new File("/var/spool/asterisk/monitor/" + fname);
		    	
		    	long fsize = f.length();
		    	byte[] data = new byte[(int)fsize];
		    	
		    	FileInputStream fin = new FileInputStream(f);
		    	
		    	fin.read(data);
		    	
		    	fin.close();
		    	
		    	
		    	
		    	 
		    	NotesWSClient wsclient = new NotesWSClient(url);
		 		 String[] res = wsclient.generalCommand(cmd, options, data);
		 		 data = null;
		 		 
		    	setVariable("TESTVAR", res[0]); 
		 	//	verbose("agi out put :" + res[0], 1);
		 		
		 		
		 		//exec("NoOp", res[0]);
		 		// System.out.println(res);
		 		
		 		 
		 		 
		     }catch(Exception e)
		     {
		    	 
		    	// System.out.println(e);
		    	// exec("NoOp", e.toString());
		    	 setVariable("TESTVAR", e.toString());
		    	// verbose("agi out put :" + e.toString(),1);
		     }
			
			
		
		
		

	}

}
