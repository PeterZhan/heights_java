package heightsre.web.service;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

// the class is for Notes web service client
public class NotesWSClient {
	
	private AstPortalProxy service;   // a private member of the web service
	
	
	public NotesWSClient(String url)  // construction function to initialize the web service
	{
		
		service = new AstPortalProxy();   
		service.setEndpoint(url);  // set the url
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
