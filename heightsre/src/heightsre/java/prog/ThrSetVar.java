package heightsre.java.prog;

import org.asteriskjava.manager.action.SetVarAction;
import org.asteriskjava.manager.response.ManagerResponse;

public class ThrSetVar extends Thread {
	
	String ch, vn, vv;
	
	public ThrSetVar(String chann, String varname, String varvalue)
	{
		ch = chann;
		vn = varname;
		vv = varvalue;
		
		
		
		
		
		
		
	}
	
	
	
	public void run()
	{
		SetVarAction action = new SetVarAction(ch, vn, vv);
		  
	   	
		   try{
		   	  ManagerResponse response = AstConnection.managerConnection.sendAction(action);
		 	   
		       	
		       	 
		    }
		    catch (Exception e){	 };
		   		
		    action = null;
		
		
		
		
	}
	
	

}
