package heightsre.java.fastagi.client;

import heightsre.java.fastagi.soapstub.ssl.AstPortalProxy;

// the class is for Notes web service client
public class NotesWSClient {
	
	private AstPortalProxy service;   // a private member of the web service
	boolean ssl = false;
	
	public NotesWSClient(String url)  // construction function to initialize the web service
	{
				
		
		
		if (ssl) {
			//System.setProperty("javax.net.ssl.trustStore", "/opt/jdkrun/jre/lib/security/cacerts"); 
			//System.setProperty("javax.net.ssl.trustStorePassword", "changeit"); 
		//	System.setProperty("javax.net.ssl.trustStore", "E:\\client.truststore"); 
		//	System.setProperty("javax.net.ssl.trustStorePassword", "password");
		//	System.setProperty("javax.net.debug", "ssl");
		//	System.setProperty("javax.net.ssl.trustStore", "heightskeyring.sth");
		//	System.setProperty("javax.net.ssl.trustStorePassword", PASS_TRUSTSTORE);
		//	System.setProperty("javax.net.ssl.keyStoreType", KEYSTORE_TYPE);\
		/////	System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		    url = url.replace("http://www.heightsre.com", "https://www.heightsre.com:443");
		 
		}
		service = new AstPortalProxy(); 
		service.setEndpoint(url); 
	}
	
	// send the web service command without voice data
	public String[] generalCommand(String cmd, String[] opts)
	{
		String[] res = null;
	    try{	
		   res = service.generalCommand(cmd, opts);  // send the command
		
	    }
	    catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		return res;
		
		
	}
	
	// send the web service command with voice data
	public String[] generalCommand(String cmd, String[] opts, byte[] data)
	{
		String[] res = null;
	    try{	
		   res = service.generalCommand(cmd, opts, data);  // send the command
		
	    }
	    catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		return res;
		
		
	}
	
	

}
