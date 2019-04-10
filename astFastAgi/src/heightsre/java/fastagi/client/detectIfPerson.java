package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class detectIfPerson extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		int i = -1;
		char key = 0;
		
	String tenantName = getVariable("NAMECALLED");
	
	
	String docid = getVariable("DOCID");
	//	String namecalled = getVariable("NAMECALLED");
	String numcalled = getVariable("REMOTEEXTEN");
	
	
	
	String unid = getVariable("UNIQUEID");
	
	String prompt = "Press 1- listen to message. Press 2- if you are not " + tenantName;
	
	String[] cmds = new String[3];
	cmds[0] = "/var/javalib/swiftStr.sh";
	cmds[1] = "/tmp/ifperson" + unid + ".wav";
	cmds[2] = prompt;
	
	 try{
	       
	        Process proc =  Runtime.getRuntime().exec(cmds);

	        
         if (proc != null)
         {
         	proc.waitFor();
         }


	        
	        
     }catch(Exception e)
     {
     	System.out.println(e);
     }
	
	
	
	
		
		
	while( i< 1)   
	{
		
		 key = streamFile("/tmp/ifperson" + unid, "#*1234567890");
	   	
	   	if (key == '1') 
	   		{
	   		
	   		setVariable("typeofdetect", "PERSON");
	   		setAccessNameCalled(docid, tenantName, numcalled);
	   		
	   		return;
	   		
	   		
	   		}
	   	else
	   	{
	   		if (key == '2') break;
	   		
	   		
	   		
	   		if (key != 0)
	   		{
	   			streamFile("invalid");
	   			i++;
	   			continue;
	   			
	   		} else
	   		{
	   			key = waitForDigit(10*1000);
	   			
	   			if (key == '1')
	   				{
	   				setVariable("typeofdetect", "PERSON");
	   				setAccessNameCalled(docid, tenantName, numcalled);
	   				
	   				return;
	   				
	   				
	   				}
	   			else
	   			{
	   				i++;
	   				
	   				if (key == '2') break;
	   				
	   				if (key == 0)
	   				{
	   					
	   					
	   				}else
	   				{
	   					streamFile("invalid");
	   				}
	   				
	   				continue;
	   			}
	   			
	   			
	   		}
	   		
	   		
	   		
	   	}
	   	
	   	
	   	
		
	}	
	
	if (key == '2')
	{
		setVariable("typeofdetect", "WRONGPERSON");
		streamFile("custom/wrongperson");
		
		
		//setVariable("typeofdetect", "WRONGPERSON");
	}
/*	
	
	String otherphone = getVariable("OTHERPHONE");
	
	if (otherphone == null ,, otherphone.equals(""))
	{
	
	
	} else
	{
		
		String docid = getVariable("DOCID");
		
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		
		String[] options = new String[2];
		options[0] = otherphone;
		options[1] = docid;
		
		try{
		AstPortalProxy service = new AstPortalProxy();  
		service.setEndpoint(url);
		String[] res = service.generalCommand("TRYOTHERPHONE", options);
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}*/
		
	hangup();	

	}
	
	
	public void setAccessNameCalled(String docid, String namecalled, String numcalled)
	{
	//	String acdocid = getVariable("DOCID");
	//	String namecalled = getVariable("NAMECALLED");
	//	String numcalled = getVariable("REMOTEEXTEN");
		
		 String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	 
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);
	 		 
	 		 String[] opt = new String[3];
	 		 
	 		 opt[0] = docid;
	 		 opt[1] = namecalled;
	 		 opt[2] = numcalled;
	 		 System.out.println("Starting get");
	 		 
	 		// String[] res = service.generalCommand("ACCESSNAMECALLED", opt);
	 		 
	 		 
            NotesWSClient wsclient = new NotesWSClient(url);
			
			String[] res = wsclient.generalCommand("ACCESSNAMECALLED", opt);
	 		 
	 		 
	 		 System.out.println("Ending get");
	 		 System.out.println(res[0]);
	 		 
	 		 
	 	//	 streamFile("thank-you-cooperation");
	 	//	 System.out.println(res[0]);
	 		 
	     } 
	 	 catch(Exception e)
		 {
		    System.out.println(e);	 
		 }finally
		 {
		  	// hangup();
		 }
		
		
		
		
	}
	

}
