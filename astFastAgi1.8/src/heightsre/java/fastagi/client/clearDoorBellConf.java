package heightsre.java.fastagi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class clearDoorBellConf extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String quemems = getVariable("QUEMEM");
		String[] dbper = quemems.split(":");
		
		
		
		for (int i=0; i<dbper.length; i++)
		{
			
			String call = "Action: QueueRemove\r\n" +
             "Queue: " + "NEWOPERATOR\r\n" +
             "Interface: SIP/" + dbper[i] + "\r\n\r\n" ;
	  
	  
            makecalls(call); 
			
			
			
			
			
			
			
			
		}
		

	}
	
	
	private void makecalls(String calls)
	{
		try{
    		   String hostname = "127.0.0.1";  // localhost ip
    		   SocketFactory factory = null;
    		   
    		   if (false)
    		    factory= (SSLSocketFactory)SSLSocketFactory.getDefault();
    		   else
    		    factory = SocketFactory.getDefault();	// get the socket factory
    		   	
    		   Socket clientRequest = factory.createSocket(hostname, 5038);  // connect to asterisk 5038 port of manager api
    		 
    			BufferedReader input; //the input stream
    			PrintWriter output; //the outpur stream
    		   
    		   InputStreamReader reader; // the input reader
    		   OutputStreamWriter writer; // the outpur writer

    		 
    		   reader = new InputStreamReader(clientRequest.getInputStream());
    		   
    		   input = new BufferedReader(reader);  // get the input reader
    		     
    		   writer = new OutputStreamWriter(clientRequest.getOutputStream());
    		     
    		   output = new PrintWriter(writer,true);  // get the outpur writer
    	
    		   
    		  String actions = "Action: login\r\n" +
    		                   "Events: off\r\n" +
    		                   "Username: admin\r\n" +
    		                   "Secret: amp111\r\n\r\n" +  // first action is logging the asterisk manager
    		                   calls +   // then send the action of command
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
    		                   
    		                   "Action: Logoff\r\n\r\n";  // then log off the asterisk manager
    		                   
    		                   
    		output.println(actions);  // write to the socket
    		   
    		String line = input.readLine();
    	     
    	     while (line != null)
    	     {
    	       
    	     
    	        line = input.readLine();
    	     }  
    		 

    		   
    		  // close all streams 
    		   input.close();
    		   output.close();
    		   clientRequest.close();
    		 
    		 }catch(Exception e)
    		 {
    		 }
		
		
		
	}
	

}
