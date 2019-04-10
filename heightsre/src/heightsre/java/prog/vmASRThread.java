package heightsre.java.prog;

import org.asteriskjava.manager.event.MessageWaitingEvent;

import heightsre.java.fastagi.soapstub.AstPortalProxy;

import java.io.*;

import org.json.*;

public class vmASRThread extends Thread {
	
	private String mb;
	private String ext;
	private String vmp;
	public vmASRThread(String m)
	{
		mb = m;
		String[] res = mb.split("@");
		ext = res[0];
		vmp = res[1];
		
	}
	
	public void run()
	{
	   String vmpath = "/var/spool/asterisk/voicemail/" + vmp + "/" + ext + "/INBOX/";
	   String cmdFile = "/var/javalib/FindNewFile.sh";
	   String cmdAsr = "/var/javalib/googleAsr.sh";
	   /*
	    {"status":0,"id":"552306a7b900abc6dd975a30273f0260-1",
	    "hypotheses":[{"utterance":"hi george this is a video on I'm calling you to discuss the studio project construction project please call me 51667606 thank you",
	    "confidence":0.6862697}]}

	    */
	  String[] cmds = new String[2];
	  cmds[0] = cmdFile;
	  cmds[1] = vmpath;
	  
	  String fileName = runshellcmds(cmds);
	  
	  System.out.println(cmds);
	  System.out.println(fileName);
	  
	  fileName = fileName.replace(".WAV", "");
	  fileName = fileName.replace(".wav", "");
	  fileName = fileName.replace(".txt", "");
	  
	  
	  cmds = new String[3];
	  cmds[0] = cmdAsr;
	  cmds[1] = fileName;
	  cmds[2] = vmpath;
	  
	  String gres = runshellcmds(cmds);
	  System.out.println(cmds);
	  System.out.println(gres);
	  
	  gres = gres.replace("[", "");
	  gres = gres.replace("]", "");
	  if (gres.contains("\"status\":0"))
	  {
		try{ 
		  JSONObject jsonObj = new JSONObject(gres);
		  JSONObject  result = jsonObj.getJSONObject("hypotheses");
		  String cf = "" + result.getDouble("confidence");
          String ut = result.getString("utterance");
          
          String fullName = fileName + ".WAV";

          System.out.println(ut);
          System.out.println(fullName);
          //********************************
          File f = new File(vmpath + fullName);
			
		    long fsize = f.length();
		   	byte[] data = new byte[(int)fsize];
		   	
		    FileInputStream fin = new FileInputStream(f);
		   	
		    fin.read(data);
		   	
		   	fin.close();
          
          
          
         //*************************************** 
           String cmd = "VMGOOGLEASR";
			String[] opts = new String[4];
			opts[0] = fullName;
			opts[1] = cf;
			opts[2] = ut;
			opts[3] = ext;
			
			
			 
			
			
			String url = "http://www.heightsre.com/Examples/Test/empllist.nsf/AstPortal";
				     
		    
			AstPortalProxy service = new AstPortalProxy();  
		 	service.setEndpoint(url);
		    	 
			String[] response = service.generalCommand(cmd, opts, data);
			
          
          
          
          
          
		  
		  
		  
		  
		  
		}catch(Exception e)
		{
			System.out.println(e);
		}
		  
		  
	  }
	  
	  
		
		
	}
	
	private String runshellcmds(String[] cmds)
	   {
        Process pid = null;  // the process id
        String ret = "";
        try{
        
           pid = Runtime.getRuntime().exec(cmds);  // run the shell command
           
           if (pid != null)
           {
        	   
        	   BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));    
	             String str = null;    
	             
	             
	             while ((str = in.readLine()) != null)
	             {
	                 ret = ret + str; 
	             }
        	   
        	   
        	   
         	 // pid.waitFor();  // wait for the command is finished
           }
           
           
           
        }catch(Exception e)
        {
     	   
     	   System.out.println(e);
     	   
        } 
		   
		   
		 return ret;  
		   
	   }

}
