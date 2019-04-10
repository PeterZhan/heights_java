package heightsre.java.prog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import heightsre.java.fastagi.client.NotesWSClient;
import heightsre.java.fastagi.soapstub.*;

import org.asteriskjava.manager.action.*;
import org.asteriskjava.manager.response.ManagerResponse;

import java.util.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class getLegalOff {

	/**
	 * @param args
	 * 
	 * 
	 * 
	 * 
	 */
	public static Config cfg = new Config();
	
	//public static AstConnection astConn = null;
	
	public static void DogetLegalOff()
	{
		
		
		
		try{
		
		 cfg.getParams();
		 
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		
		
		String url = "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";
		   
	     try{
	    	
	    	 NotesWSClient wsclient = new NotesWSClient(url);
	    	// AstPortalProxy service = new AstPortalProxy();  
	 		// service.setEndpoint(url);
	    	 
	    	 
	      
	    //     stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
	         
	  //       AstPortalServiceStub.GetPositionByBuildingCode request = new AstPortalServiceStub.GetPositionByBuildingCode();
	         
	    //     request.setBuildingcode(arg0.getParameter("code"));
	    
	         System.out.println("Start getting ");
	         
	         String[] response = wsclient.generalCommand("GETALLLEGALOFF", null);//service.generalCommand("GETALLLEGALOFF", 
	        		// null);//stub.GetPositionByBuildingCode(request).getGetPositionByBuildingCodeReturn();
	         
	         
	         if (response[0].equals("GOT"))	        	 
	         {   
	        	 
	       //  now get the parameters and astConnetion 	 
	     /*   	 try{
	     			cfg.getParams();
	     		   }catch(IOException e){
	     			    
	     			   System.out.println("Reading params errors," + e);
	     			  	
	     			   System.exit(0);
	     			   
	     		   }
	     		   
	     		   
	     		
	     		   astConn = new AstConnection();
	     			 
	     			 try{
	     			     astConn.SetConnection();	
	     			 } catch(Exception e){
	     				 
	     			 }
	        	 */
	        	 
	        	 
	  // now making the calls      	 
	        	 
	        	 
	     //   	 for (int i=1; i<response.length; i++) 
	       // 	 {
	        		makecall(response); 
	        		 
	     //   	 } 
	        	 
	        	 
	        	 
	         }
	         
	         
	        
	         
	         for (int i=0; i<response.length; i++)
	        	  System.out.println(response[i]);
	           
	           
	        
	         
	     }   
	     catch(Exception e)
	     {
	    	System.out.println(e); 
	     }finally
	     {
	    	 
	     }
		
		
		
	}
	
	public static String getacdate(String s)
	{
		String[] res = s.split("\\/");
		
		int month = Integer.parseInt(res[0]);
		int day = Integer.parseInt(res[1]);
		int year = Integer.parseInt(res[2]);
		
		System.out.println(year + "-" + month + "-" + day);
		
		//Date d = new Date(year, month, day);
		
		
		Calendar  c =Calendar.getInstance();
		c.set(year , month-1, day, 0, 0, 0);
		
		System.out.println(c.getTime().toLocaleString());
		
		return Long.toString(c.getTime().getTime()/1000);
		
	}
	
	
    public static void makecall(String[] response)
    {
    	
    	if (response.length < 2) return;
    	
    	
    	 String calls = "";
    	 for (int i=1; i<response.length;i++)
		   {
			  String call = response[i];
			  String res[] = call.split("\\,");
			  
			  String acdocid = res[0];
	    	  String acname = res[1];
	    	  String acphone = res[2];
	    	  String acdate = getacdate(res[3]);
	    	  
	    	  calls = calls +
	    	         "Action: originate\r\n" +
                   "Channel: " + cfg.outtrunk + "/" + acphone + "\r\n" +
                   "WaitTime: 30\r\n" +
                   "CallerId: 6465726400" + "\r\n" +
                   "Exten: s"  + "\r\n" +
                   "Async: true\r\n" +
                   "Variable: ACDOCID=" + acdocid + ",ACNAME=" + acname + ",ACDATE=" + acdate + "\r\n" +
                   "Context: legaloff\r\n" +
                   "Priority: 1\r\n\r\n" ;
	    	  
	    	  
	    	  
			   
		   }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	try{
    		   String hostname = "127.0.0.1";
    		   SocketFactory factory = null;
    		   
    		   if (false)
    		    factory= (SSLSocketFactory)SSLSocketFactory.getDefault();
    		   else
    		    factory = SocketFactory.getDefault();	
    		   	
    		   Socket clientRequest = factory.createSocket(hostname, 5038);
    		 
    			BufferedReader input; //输入流
    			PrintWriter output; //输出流
    		   
    		   InputStreamReader reader;
    		   OutputStreamWriter writer;

    		 
    		   reader = new InputStreamReader(clientRequest.getInputStream());
    		   
    		   input = new BufferedReader(reader);
    		     
    		   writer = new OutputStreamWriter(clientRequest.getOutputStream());
    		     
    		   output = new PrintWriter(writer,true);
    	
    		   
    		  String actions = "Action: login\r\n" +
    		                   "Events: off\r\n" +
    		                   "Username: admin\r\n" +
    		                   "Secret: amp111\r\n\r\n" +
    		                   calls +
    		                   /*
    		                   "Action: originate\r\n" +
    		                   "Channel: SIP/" + local + "\r\n" +
    		                   "WaitTime: 30\r\n" +
    		                   "CallerId: " + CallerID + "\r\n" +
    		                   "Exten: " + remote + "\r\n" +
    		                   "Async: true\r\n" +
    		                   "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID + ",DBNAME=" + dbname + "\r\n" +
    		                   "Context: clicktodial\r\n" +
    		                   "Priority: 1\r\n\r\n" +
    		                   */
    		                   
    		                   "Action: Logoff\r\n\r\n";
    		                   
    		                   
    		output.println(actions);
    		   

    		 

    		   
    		   
    		   input.close();
    		   output.close();
    		   clientRequest.close();
    		 
    		 }catch(Exception e)
    		 {
    			  
    		 }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	//D8BD2,Heidi Coles,652,08/31/2009
    	
    	
    	 
    	
    /*	
    	
    	OriginateAction action = new OriginateAction();
    	
    	action.setAsync(true);
    	
    	action.setChannel(cfg.outtrunk + "/" + acphone);
    	
    	action.setCallerId("6465726400");
    	
    	action.setContext("legaloff");
    	
    	action.setExten("s");
    	
    	action.setPriority(1);
    	
    	action.setVariable("ACDOCID", acdocid);
    	
    	action.setVariable("ACNAME", acname);
    	
    	action.setVariable("ACDATE", acdate);
    	
    	
    	
    	try{
	      	
			 AstConnection.managerConnection.sendAction(action, 1000);
	      //   Thread.sleep(100);
			// System.out.println(rep);
		 }
	      catch (Exception e){
	    	  System.out.println(e);
	      };
	      
	    
	     action = null;
    	
    	*/
    	
    	
    	
    //	System.out.println(response[1]);
    	
    	
    }
	
	
	public static void main(String[] args) {
		
		
		DogetLegalOff();
		// TODO Auto-generated method stub

	}

}
