package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class SendMailToAgent extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String rentalType = getVariable("rentaltype");
		String newdoc = getVariable("newdoc");
		String docid = getVariable("DOCID");
		
		if (rentalType.equals("Residential") && docid.equals("none"))
		{
			docid = sendAllResiData();
		}
		
		if (rentalType.equals("Commercial"))
		{
			docid = sendAllCommData();
		}
		
		
		
		//*******send all data to notes
		
		
		
		if (docid.equals("none")) return;
		
		String[] options = new String[2];
		options[0] = docid;
		options[1] = rentalType;
	//***************************************
        String cmdr = "SETRENTALTYPE";
		
		String urlr = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient_r = new NotesWSClient(urlr);
		
		String[] res = wsclient_r.generalCommand(cmdr, options);
		
		
		
		
		
		
		
		
		
		//**************************************
		String[] opts = new String[1];
		opts[0] = docid;
				
		
		
		String cmd = "SENDEMAILTOAGENT";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		res = wsclient.generalCommand(cmd, opts);
		

	}
	private String sendAllCommData() throws AgiException 
	{
		String webid = getVariable("webid");
		String docid = getVariable("DOCID");
		
		String callernum = getVariable("CALLERID(num)");  // caller id number
		String callername = getVariable("CALLERID(name)"); 
		
		if (webid == null || webid.equals(""))
		{
			if (!docid.equals("none"))
			{
				return docid;
			}
			
		}
		
		String[] opts = new String[4];
		opts[0] = docid;
		opts[1] = webid;
		opts[2] = callernum;
		opts[3] = callername;
		
        String cmd = "SETCOMMWEBID";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		setVariable("DOCID", res[0]);
		return res[0];
		
		
		
	}
	
	private String sendAllResiData() throws AgiException 
	{
		String callernum = getVariable("CALLERID(num)");  // caller id number
		String callername = getVariable("CALLERID(name)"); 
		
		String creditscore = getVariable("creditscore");
		String webid = getVariable("webid");
		String bedrooms = getVariable("bedrooms");
		String annIncom = getVariable("annIncom");
		String CALLID = getVariable("CALLID");
		String movingday = getVariable("movingday");
		String occupantnum = getVariable("occupantnum");
		String applicantnum = getVariable("applicantnum");
		String GUARANTOR = getVariable("GUARANTOR");
		String guaIncoming = getVariable("guaIncoming");
		String fname = getVariable("fname");
		
		
		
		String[] opts = new String[13];
		
		for(int i=0;i<13;i++)
		{
			opts[i] = "";
		}
		
		if (creditscore != null)
		   opts[0] = creditscore;
		
		if (webid != null)
		  opts[1] = webid;
		
		if (callernum != null)
		  opts[2] = callernum;
		
		
		if (callername != null)
		  opts[3] = callername;
		
		if (bedrooms != null)
		  opts[4] = bedrooms;
		
		if (annIncom != null)
		  opts[5] = annIncom;
		
		if (CALLID != null)
			  opts[6] = CALLID;
		
		
		if (movingday != null)
			  opts[7] = movingday;
		
		if (occupantnum != null)
			  opts[8] = occupantnum;
		
	
		if (applicantnum != null)
			  opts[9] = applicantnum;
		
		opts[10] = "no";
		if (GUARANTOR != null)
			  opts[10] = GUARANTOR;
		
		
		
		if (guaIncoming != null)
			  opts[11] = guaIncoming;
		
		if (fname != null)
			  opts[12] = fname;
		
		
		
		
        String cmd = "SENDALLRESIDATA";
		
		String url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		setVariable("DOCID", res[0]);
		
		String docid = res[0];
		
		
		if (fname != null && !fname.equals(""))
		{
		String[] opts2 = new String[2];
		opts2[0] = docid;
		opts2[1] = fname;
		String cmd2 = "SETRESIMSG";
		
		File f = new File("/var/spool/asterisk/monitor/" + fname);
 		
 		long fsize = f.length();
    	byte[] data = new byte[(int)fsize];
    	
    	try{
    	FileInputStream fin = new FileInputStream(f);
    	
    	fin.read(data);
    	
    	fin.close();	
		
    	}catch (Exception e)
    	{
    		
    	}
		
		
		
		
		
		
		
		url = "http://www.heightsre.com/Examples/Test/NRentalT.nsf/AstPortal";
	    
		NotesWSClient wsclient2 = new NotesWSClient(url);
		
		wsclient2.generalCommand(cmd2, opts2, data);
		}
		
		
		
		
		
		
		
		return docid;
		
		
		
		
	}

}
