package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidatePONum extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		String blgCode = getVariable("BlgCode");
		String PONum = getVariable("EXTEN");
		
		if (PONum.endsWith("#"))
			PONum = PONum.substring(0,PONum.length()-1);
		
		
		String url = "http://www.heightsre.com/Examples/Test/RFP.nsf/AstPortal";
		   
	     try{
	    	 
	    	 NotesWSClient wsclient = new NotesWSClient(url); // set the web service url
	 		 
	 		 String[] opt = new String[2];
	 		 
	 		 opt[0] = blgCode;
	 		 opt[1] = PONum;
	 		
	 	
	 		 String[] res = wsclient.generalCommand("VALIDATEPONUM", opt);
	 		 
	 		 if (res[0].equals("0"))
	 		 {
	 			 setExtension("notfound");
	 			 setPriority("1");
	 			 return;
	 		 }
	 		 
	 		 setVariable("DOCID", res[1]);
	 		 
	 		 setVariable("SUPER", res[2]);
	 		 
	 		 setVariable("sext", res[3]);
	 		 
	 		 setVariable("PONUM", PONum);
	 		 setExtension("codefound");
	 		 setPriority("1");
	 		 
	 		 
	 		String bpo = "";
	 	      for (int idx=0;idx<PONum.length()-1;idx++)
	 	      {
	 	    	 bpo = bpo + PONum.substring(idx,idx+1) +  " ";
	 	      }
	 	       
	 	      
	 	      bpo = bpo + PONum.substring(PONum.length()-1);
	 		   
	 		  setVariable("BPONUM", bpo);  
	 		 
	 		 
	 		 
	 		 
	 		 
	 		 
	 	
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	
		 }
		
		

	}

}
