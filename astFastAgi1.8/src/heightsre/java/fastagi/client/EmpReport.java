package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class EmpReport extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub

		String docid = getVariable("DOCID");
		String unid = getVariable("UNIQUEID");
		String fname = "emprep" + unid + ".WAV";
		String flength = getVariable("RECLENGTH");
		String ifSaved = getVariable("RECSAVED");
		
		if (ifSaved == null) return;
		
		if (ifSaved.equals("") || ifSaved.equals("LATER"))
			return;
		
		if (!(ifSaved.equals("YES") || ifSaved.equals("NO")))
			return;
		
		
	
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
		
		
		String cmd = "EMPLOYEEDAILYCALLREPORT";
		String[] options = new String[4];
		
		options[0] = docid;
		options[1] = fname;
		options[2] = flength;
		options[3] = getVariable("CALLERID(num)");
	
		
		
		
	    try{
	    	
	    	
	    	
	    	File f = new File("/var/spool/asterisk/monitor/" + fname);
	    	
	    	long fsize = f.length();
	    	byte[] data = new byte[(int)fsize];
	    	
	    	FileInputStream fin = new FileInputStream(f);
	    	
	    	fin.read(data);
	    	
	    	fin.close();
	    	
	    	
	    	
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);
	    	NotesWSClient wsclient = new NotesWSClient(url);
	 		 String[] res = wsclient.generalCommand(cmd, options, data);
	 		 data = null;
	 		 
	 //		setVariable("TESTVAR", res[0]); 
	 	//	verbose("agi out put :" + res[0], 1);
	 		
	 		
	 		//exec("NoOp", res[0]);
	 		// System.out.println(res);
	 		
	 		 
	 		 
	     }catch(Exception e)
	     {
	    	 System.out.println(e);
	    	// exec("NoOp", e.toString());
	    //	 setVariable("TESTVAR", e.toString());
	    	// verbose("agi out put :" + e.toString(),1);
	     }
		
		
		
	}

}
