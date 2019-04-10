package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurRfpPoDailyOfOne extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String i = getVariable("I");
		String docid = getVariable("DOCID" + i);
		String spName = getVariable("EMPNAME");
		
		String[] opt = new String[1];
	     
	     // these are the options for the service command
        opt[0] = docid;
   
       setVariable("DOCID", docid);
      
   
       String cmd = "GETRFPDAILYBYID"; 
	   	
       String url = "http://www.heightsre.com/Examples/Test/rfp.nsf/AstPortal";
	    
	   NotesWSClient wsclient = new NotesWSClient(url);
		
	   String[] res = wsclient.generalCommand(cmd, opt);
	   
	   String bldg = res[0];
	   String po = res[1];
	   String jtitle = res[2];
	   String sext = res[3];
	   String supercell = res[4];
	   String nodate = res[5];
	   String vendor = res[6];
	   
	   vendor = vendor.replace(",", " ");
	   
	   setVariable("BBlgAddr", bldg);
	   setVariable("PONUM", po);
	   setVariable("JTITLE", jtitle);
	   
	   
	   setVariable("sext", sext);
	   setVariable("callid", supercell);
	   setVariable("nodate", nodate);
	   
	   setVariable("VENDOR", vendor);
	   
	   
	  String bpo = "";
      for (int idx=0;idx<po.length()-1;idx++)
      {
    	 bpo = bpo + po.substring(idx,idx+1) +  " ";
      }
       
      
      bpo = bpo + po.substring(po.length()-1);
	   
	  setVariable("BPONUM", bpo);  
	   
		
		

	}

}
