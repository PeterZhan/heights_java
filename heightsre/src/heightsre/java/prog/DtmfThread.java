package heightsre.java.prog;

import org.asteriskjava.manager.action.*;
import org.asteriskjava.manager.response.ManagerResponse;



public class DtmfThread extends Thread {
	
	
	String channel = "";
	public DtmfThread(String chann)
	{
		channel = chann;
	}
	
	public void run()
	{
	    String oldexten = GetChannelVar(channel,"OLDEXTEN");
		
	    if (!oldexten.equals(""))
	    {
	    	TSleep(100);
	    	StringBuffer strbNum = new StringBuffer(oldexten);
	    	sendDtmf(channel, "w");
	    	sendDtmf(channel, "w");
	    		for (int i=0; i<strbNum.length(); i++){
				 sendDtmf(channel,  strbNum.substring(i,i+1));
				 System.out.println("sendDTMF:" + strbNum.substring(i,i+1) + " " + channel);
				 sendDtmf(channel, "w");
				 sendDtmf(channel, "w");
				 sendDtmf(channel, "w");
				// TSleep(1000);
			 }
	    //	sendDtmf(channel,  "2"); 
			strbNum = null;
	    	
	    	
			//SetChannelVar(channel, "OLDEXTEN", "");
			
			
	    	
	    }
		
		
		
	}
	
	public String GetChannelVar(String chann, String varname)
	{
		   
	   String value = "";
	   GetVarAction action = new GetVarAction(chann, varname);
		  
	   	
	   try{
	   	  ManagerResponse response = AstConnection.managerConnection.sendAction(action);
	 	   value = response.getAttribute("Value");
	       	
	       	 
	    }
	    catch (Exception e){	 };
	   		
	    action = null; 
	        
	    System.out.println("**************************************##########################################***************************get channel variable: chann " + chann +
			           "  variable " + varname +
			           "  value " + value);
		   
		if (value == null) value = "";
		if (value.equals("(null)")) value = "";
	        
		return value;
		   
	}
	
	public void sendDtmf(String chann, String digit){
		 
		 
		 PlayDtmfAction action = new PlayDtmfAction();
		 action.setChannel(chann);
		 action.setDigit(digit);
		 try{
		      	
			 ManagerResponse rep = AstConnection.managerConnection.sendAction(action, 1000);
	      //   Thread.sleep(100);
			// System.out.println(rep);
		 }
	      catch (Exception e){};
	      
	    
	     action = null;
		 
		 
	 }
	
	 public void SetChannelVar(String chann, String varname, String varvalue)
	   {
		   
		   SetVarAction action = new SetVarAction();
	   	   action.setChannel(chann);
	   	   action.setVariable(varname);
	   	   action.setValue(varvalue);
	   	
	   	   try{
	       	
	   		    AstConnection.managerConnection.sendAction(action, 3000);
	        }
	        catch (Exception e){	 };
	   		
	        action = null; 
		   
		    System.out.println("set channel variable: chann " + chann +
		    		           "  variable " + varname +
		    		           "  value " + varvalue);
		                       
		   
		   
	   }
	 
	 public void TSleep(long ms)
	 {
		 try
	      {
	 		  Thread.sleep(ms);
	 	  }
	 	  catch(Exception e)
	 	  {
	 		  	
	 			 
	 	  }
	 }

}
