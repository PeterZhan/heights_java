package heightsre.java.prog;

import java.sql.Connection;

import heightsre.java.fastagi.client.DbConnection;
import heightsre.java.fastagi.client.MyJMathLib;
import heightsre.java.fastagi.client.NotesWSClient;
import heightsre.java.fastagi.soapstub.AstPortalProxy;

import org.asteriskjava.manager.event.*;

public class ProcessDial extends Thread {
	
	private String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	
	//private AstPortalProxy service = new AstPortalProxy();
//	NotesWSClient wsclient = new NotesWSClient(url);
	
	DialEvent dEvent = null;
	String srcChann = "";
	String dstChann = "";
	
	String callType = "";
	String phonenumber = "";
	String empExt = "";
    String empName = "";
    String uid = "";
	
	public ProcessDial(DialEvent de, String src, String dst)
	{
       dEvent = de;
       srcChann = src;
       dstChann = dst;
     //  service.setEndpoint(url);
       
       

	}
	
	private String getExtenFromChann(String chann)
	{
		String res = "";
		
		int p1 = chann.indexOf('/');
		int p2 = chann.indexOf('-');
		
		if (p2 > p1 + 1)
			res = chann.substring(p1+1, p2);
		
		
		
		return res;
		
	}
	
	private String getNumberFromDialString(String DialString)
	{
		String res = "";
		
		System.out.println(DialString);
		
		if (DialString.startsWith("SIP/xo/"))
		{
		     res = DialString.replace("SIP/xo/", "");
		     int p2 = res.indexOf(',');
		     
		     res = res.substring(0, p2);
		     
		}
		
		return res;		
	}
	
	public void run()
	{
	   
		
		
		uid = dEvent.getUniqueId();
		
		
		//String DialString = dEvent.getDialString();
		
		System.out.println("Processing Dial Event:");
		System.out.println(srcChann);
		System.out.println(dstChann);
	//	System.out.println(DialString);
		
		if ((srcChann.startsWith(heightsReMain.cfg.astChann)) && !dstChann.startsWith(heightsReMain.cfg.astChann))
		{
			callType = "In";
			phonenumber = dEvent.getCallerIdNum();
			
			empExt = getExtenFromChann(dstChann);
			
			String did = AstConnection.getVariable(srcChann, "pbDID");
			
			if (did != null && !did.equals(""))
				callType = "InFor" + did;
			
			
		}
		
		if ((!srcChann.startsWith(heightsReMain.cfg.astChann)) && dstChann.startsWith(heightsReMain.cfg.astChann))
		{
			callType = "Out";
						
			empExt = getExtenFromChann(srcChann);
			
			phonenumber = AstConnection.getVariable(dstChann, "CALLERID(num)");
			
		}
		
		System.out.println(callType + ":" + empExt + ":" + phonenumber);
		
		if (callType.equals("") || empExt.equals("") || phonenumber.equals(""))
			return;
		
		if (empExt.length() != 3) return;
		
		if (phonenumber.length() < 10) return;
		
		if (!MyJMathLib.isNumeric(empExt)) return;
		
		if (!MyJMathLib.isNumeric(phonenumber)) return;
		
		try{
	    	 
			String sql = "insert into callingrecord values(\'" + empExt + "\',"
            + "\'" + callType   + "\',"
            + "\'" + phonenumber    + "\'," 
            + "\'0\',"
            + "\'\',"
            + "\'" + uid + "\')";
        //    + "\'" + dEvent.getTimestamp() + "\')";
           

        

           DbConnection.sqlExecute(sql); 
	     /*	 
		      String cmd = "SETCALLING";
		      String[] opt = new String[5];
		      
		      opt[0] = phonenumber;
		      opt[1] = callType;
		      opt[2] = empName;
		      opt[3] = empExt;
		      opt[4] = uid;
		      
		 //     System.out.println(exten + " " + status);
		      
		      String[] response = service.generalCommand(cmd, opt);
		
		      */
		 //     System.out.println(response[0]);
		
		     
		
		
	     }   
	     catch(Exception e)
	     {
	    	System.out.println(e); 
	    	e.printStackTrace();
	     }finally
	     {
	    	 
	     }
	
	
	
	}
	
	

}
