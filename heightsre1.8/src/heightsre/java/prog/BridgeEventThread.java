package heightsre.java.prog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.asteriskjava.manager.event.*;


public class BridgeEventThread extends Thread {
	private BridgeEvent pbe;
	public BridgeEventThread(BridgeEvent be)
	{
		pbe = be;
	}
	
	
	public void run()
	{
		String chan1 = pbe.getChannel1();
		String chan2 = pbe.getChannel2();
		
		System.out.println("Link two channels:" + chan1 + " " + chan2);
		
		boolean isLink = pbe.isLink();
		
		String exten1 = getExtenFromChan(chan1);
		String exten2 = getExtenFromChan(chan2);
		
		if (exten1.length()==3)
		{
			updateExtenStatus(exten1,chan1,chan2,isLink);
			
		}
		
		if (exten2.length()==3)
		{
			updateExtenStatus(exten2,chan2,chan1,isLink);
			
		}
		
		
		
		
	}
	
	private void updateExtenStatus(String exten,
			                       String lchan,
			                       String rchan,
			                       Boolean isLink)
	{
		String status = "UNTALKING";
		if (isLink) status = "INTALKING";
		
		Connection con = null;
		
		String sqlstr = "select * from extenstatus where exten=\'" + exten + "\'";
        String exeSql = "insert into extenstatus(exten,exstatus,localchannel,remotechannel,otherinfo) values(\'" + exten + "\',\'" + status + "\',\'" + lchan + "\',\'" + rchan + "\',\'\')";
       try{
           con = DbConnection.getConnection();
           Statement stat = con.createStatement();
           ResultSet result = stat.executeQuery(sqlstr);

           if (result.next()){
		
        	   exeSql = "update extenstatus set exstatus=" + "\'" + status + "\'," +
        	                                   "localchannel=\'" + lchan + "\'," +
        	                                   "remotechannel=\'" + rchan + "\' " +
        	                                   "where exten=\'" + exten + "\'";
		
		
           }
           
           System.out.println(exeSql);
           
           DbConnection.sqlExecute(exeSql);
           
       }catch(Exception e)
       {
       	System.out.println(e);
       }
       finally
       {
       	DbConnection.FreeCon(con);
       
       	
       }
		
	   
       
       
	}
	
	
	
	
	private String getExtenFromChan(String chan)
	{
		String ret = "";
		int p1=chan.indexOf('/');
		int p2=chan.indexOf('-');
		
		if (p2!=-1 && p1!=-1 && p2>p1)
		{
			ret = chan.substring(p1+1, p2);
		}
		
		
		
		return ret;
		
	}

}
