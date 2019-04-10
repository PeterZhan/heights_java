package heightsre.java.prog;

import org.asteriskjava.manager.event.*;

public class NEWSESSEvent extends UserEvent {
	
	
	   private String conf;
	   private String channel;
	 
	   public NEWSESSEvent(Object source)
	   {
	     super(source);
	   }
	   
	   public String getConf()
	   {
	     return conf;
	   }
	   
	   public void setConf(String confnum)
	   {
		 String res[] = confnum.split("@");  
		   
		   
	     this.conf = res[0];
	     this.channel = res[1];
	   }

	   public String getChannel()
	   {
	     return channel;
	   }
	   
	 //  public void setChannel(String chan)
	 //  {
	  //   this.channel = chan;
	  // }
}
