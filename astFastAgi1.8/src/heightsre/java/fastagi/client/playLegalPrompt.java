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

public class playLegalPrompt extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String channelSpied = getVariable("CHANNEL");
		
		
		
		String call = "Action: originate\r\n" +
        "Channel: " + "LOCAL/s@playlegalprompt/nj\r\n" +
        "WaitTime: 30\r\n" +
        "CallerId: 2123171423" + "\r\n" +
        "Exten: s\r\n" +
        "Async: true\r\n" +
        "Variable: CHANSPIED=" + channelSpied  + "\r\n" +//",LOCKBOXADDR=" + lockboxaddr + ",PRWAVE=" + tmpWavFile +",REMOTEEXTEN="+ phone 
        "Context: channelspied\r\n" +           
        "Priority: 1\r\n\r\n" ;

       makecalls(call); 
		
		
		
		

	}
	
	
	private void makecalls(String calls)
	{
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
		
		
		
	}

}
