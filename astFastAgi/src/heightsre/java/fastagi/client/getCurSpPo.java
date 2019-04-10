package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurSpPo extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String sidx = getVariable("idx");
		int iidx = Integer.parseInt(sidx);
		
		String po = getVariable("PO" + sidx);
		
		setVariable("CURPO", po);
		
		 String bpo = "";
	      for (int idx=0;idx<po.length()-1;idx++)
	      {
	    	 bpo = bpo + po.substring(idx,idx+1) +  " ";
	      }
	       
	      
	      bpo = bpo + po.substring(po.length()-1);
		   
		  setVariable("BCURPO", bpo);  
		   
		
		
		
		
		
		
		String bldgCode = getVariable("BlgCode");
		
		String[] opts = new String[2];
		opts[0] = bldgCode;
		opts[1] = po;
		
	 	   
    	 String cmd = "GETSPPOINFO";
   		
   		
   		
   		  
   		String url = "http://www.heightsre.com/Examples/Test/poreq.nsf/AstPortal";
   	    
   		NotesWSClient wsclient = new NotesWSClient(url);
   		
   		String[] res = wsclient.generalCommand(cmd, opts);
		
   		
   		String bldg = res[0];
   		String apt = res[1];
   		String jtype = res[2];
   		String jcate = res[3];
   		String duedate = res[4];
   		String spvoice = res[5];
   		String docid = res[6];
   		
   		spvoice = spvoice.replace(".WAV", "");
   		spvoice = spvoice.replace(".wav", "");
   		spvoice = spvoice.replace(".amr", "");
   		
   		
   		
   		setVariable("BLDG", bldg);
   		setVariable("APTNO", apt);
   		setVariable("JOBTYPE", jtype);
   		setVariable("JOBCATE", jcate);
   		setVariable("DUEDATE", duedate);
   		setVariable("SPVOICE", "/var/spool/asterisk/monitor/" + spvoice);
   		setVariable("DOCID", docid);
   		
   		
		
		
		
		

	}

}
