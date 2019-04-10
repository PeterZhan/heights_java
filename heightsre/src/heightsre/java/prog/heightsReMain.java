package heightsre.java.prog;

import java.io.IOException;
import java.util.HashMap;





public class heightsReMain {

	/**
	 * @param args
	 */
	
	static final Config cfg = new Config();
	public static AstConnection astConn = null;
	
	public static ExtensStatus exstatus = new ExtensStatus();
	
	public static void main(String[] args) {
		
		try{
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
			 
			 
		   new RefExtStThread().start(); 
		   new thRemoveRec().start(); 
		   
		   while(true)
		   {
			   		   
			   
			   try
			      {
			 		  Thread.sleep(10);
			 	  }
			 	  catch(Exception e)
			 	  {
			 		  	
			 			 
			 	  }
		   }
		
		// TODO Auto-generated method stub

	}

}
