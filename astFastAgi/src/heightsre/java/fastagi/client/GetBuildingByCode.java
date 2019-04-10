package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;


import heightsre.java.fastagi.soapstub.*;
import java.util.regex.*;


public class GetBuildingByCode extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		
	     String url = "http://www.heightsre.com/Examples/Test/WebServiceTest.nsf/AstPortal";
	   
	     try{
	    	 
	    	 AstPortalProxy service = new AstPortalProxy();  
	 		 service.setEndpoint(url);
	    	 
	    	 
	      
	    //     stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
	         
	  //       AstPortalServiceStub.GetPositionByBuildingCode request = new AstPortalServiceStub.GetPositionByBuildingCode();
	         
	    //     request.setBuildingcode(arg0.getParameter("code"));
	    
	         System.out.println("Start getting ");
	         
	         String[] response = service.getPositionByBuildingCode(arg0.getParameter("code"));//stub.GetPositionByBuildingCode(request).getGetPositionByBuildingCodeReturn();
	         
	         if (response[0].equals(""))
	         {
	        	 streamFile("custom/invalidbuidingcode");
	         } else
	         {
	         
	           String buildingnum = response[0];
	           String buildstreet = response[1];
	         
	           
	           System.out.println("got " + response[0] + " " + response[1]);
	           
	           if (IsNumStr(buildingnum))
	           {
	        	 streamFile("custom/buildingaddress");
	             exec("SayNumber", buildingnum);
	        
		         exec("Festival", "\'" + buildstreet + "\',#");
		         exec("Wait", "0.5");
	           }else
	           {
	        	  streamFile("custom/invalidbuidingcode"); 
	           }
	         }
	         
	     }   
	     catch(Exception e)
	     {
	    	 
	     }finally
	     {
	    	 setExtension("s");
	    	 setPriority("1");
	     }
		// TODO Auto-generated method stub

	}
	
	public boolean IsNumStr(String s)
	{
		
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence)s);
		return matcher.matches();
		
		
		
	}

}
