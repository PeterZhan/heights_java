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

public class CallNextPhone extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String phones = getVariable("PHONES");
		if (phones == null || phones.equals("")) return;
		
		String[] opts = new String[5];
		
		opts[4] = phones;
		opts[0] = getVariable("DOCID");
		opts[1] = getVariable("SUFFIX");
		opts[2] = getVariable("LORD");
		opts[3] = getVariable("EXPDATE");
		
		
		callTenantForRenewal(opts);
		
		
		

	}
	
	
	private String callTenantForRenewal(String[] options)
	{
		String res = "OK";
		
		String docid = options[0];
		String suffix = options[1];
		String lordName = options[2];
		String expDate = options[3];
		String phones = options[4];
		
		String[] phoneArray = phones.split("&");
		String leaserec = "lease" + System.currentTimeMillis();
		
		if (phoneArray.length <= 0) return res;
		
		String NamePhone = phoneArray[0];
		String[] NameAPhone = NamePhone.split("-");
		String tenantName = NameAPhone[0];
		String phone = NameAPhone[1];
		
		String context1 = "callcelloutgoing";
		String context2 = "calltenforrenewal";
		
		String leasetype = "";
		try {
			leasetype = getVariable("LEASETYPE");
		} catch (AgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (leasetype.equals("COMMERCIAL"))
		{
			context1 = "callcomcelloutgoing";
			context2 = "callComforrenewal";
		}
		
		String apdPhones = "";
		
		if (phoneArray.length > 1){
			
			apdPhones = "Variable: PHONES=";
			for (int i=1; i<phoneArray.length-1; i++)
			{
				
				apdPhones  += phoneArray[i] + "&";
				
			}
			apdPhones  += phoneArray[phoneArray.length-1];
			
			apdPhones  += "\r\n";
		}
		
		
		String call = "Action: originate\r\n" 
		         //   + "Channel: SIP/xo/" + phone + "\r\n" 
		            + "Channel: LOCAL/s@" + context1 + "/n" + "\r\n" 
				    + "WaitTime: 120\r\n"
				    + "CallerId: 2123171423\r\n" 
				    + "Variable: TENANT=" + tenantName + "\r\n" 
				    + "Variable: SUFFIX=" + suffix + "\r\n" 
				    + "Variable: LORD="	+ lordName + "\r\n" 
				    + "Variable: NAMEPHONE=" + NamePhone + "\r\n" 
				    + "Variable: leaserec=" + leaserec + "\r\n" 
				  //  + "Variable: OPT=-1" + "\r\n"
				    + "Variable: EXPDATE=" + expDate + "\r\n" 
				    + "Variable: PHONE=" + phone + "\r\n" 
				    + "Variable: DOCID="   + docid + "\r\n" + apdPhones
				    + "Exten: s\r\n" 
				    + "Async: true\r\n"
				    + "Context: " + context2 + "\r\n" 
				    + "Priority: 1\r\n\r\n";

		makecalls(call);
		
		
		
		
		
		
		
		
		
		
		
		
		
		return res;
	}
	private void makecalls(String calls) {
		try {
			String hostname = "127.0.0.1"; // localhost ip
			SocketFactory factory = null;

			if (false)
				factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			else
				factory = SocketFactory.getDefault(); // get the socket factory

			Socket clientRequest = factory.createSocket(hostname, 5038); // connect
																			// to
																			// asterisk
																			// 5038
																			// port
																			// of
																			// manager
																			// api

			BufferedReader input; // the input stream
			PrintWriter output; // the outpur stream

			InputStreamReader reader; // the input reader
			OutputStreamWriter writer; // the outpur writer

			reader = new InputStreamReader(clientRequest.getInputStream());

			input = new BufferedReader(reader); // get the input reader

			writer = new OutputStreamWriter(clientRequest.getOutputStream());

			output = new PrintWriter(writer, true); // get the outpur writer

			String actions = "Action: login\r\n" + "Events: off\r\n"
					+ "Username: admin\r\n" + "Secret: amp111\r\n\r\n" + // first
																			// action
																			// is
																			// logging
																			// the
																			// asterisk
																			// manager
					calls + // then send the action of command
					/*
					 * "Action: originate\r\n" + "Channel: SIP/" + local +
					 * "\r\n" + "WaitTime: 30\r\n" + "CallerId: " + CallerID +
					 * "\r\n" + "Exten: " + remote + "\r\n" + "Async: true\r\n"
					 * + "Variable: LOCALEXTEN=" + local + ",DOCID=" + DocID +
					 * ",DBNAME=" + dbname + "\r\n" + "Context: clicktodial\r\n"
					 * + "Priority: 1\r\n\r\n" +
					 */

					"Action: Logoff\r\n\r\n"; // then log off the asterisk
												// manager

			output.println(actions); // write to the socket

			String line = input.readLine();

			while (line != null) {

				line = input.readLine();
			}

			// close all streams
			input.close();
			output.close();
			clientRequest.close();

		} catch (Exception e) {
		}

	}
}
