package heightsre.java.fastagi.client;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateScJobTypes extends BaseAgiScript {
	
/*	
	String[] vdrjt = {"Burner/Boiler",
			"Sewer",
			"Boiler Cleaning",
			"Elevator", 
			"Intercom",
			"Plumbing",
			"Roof",
			"Glass and Windows",
			"Exterminator",
			"Fuel Computer",
			"Oil Tank Cleaning",
			"Electrical",
			"Sand & Polyurethane Wood Floors",
			"Appliances",
			"Containers",
			"Bathtub Reglazing",
			"Welding",
			"Dust Test",
			"Pump",
			"Snow Removal",
			"Heating Blowers",
			"Network Maintenance",
			"Camera System",
			"Generator",
			"Locksmith",
			"Phone Contractor",
			"HVAC",
			"Appliance Repairs",
			"Alarm Monitoring",
			"Fire Alarm"   
			
};
*/
	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		
		String jt = getVariable("EXTEN");
		if (jt.endsWith("#"))
			jt = jt.substring(0, jt.length()-1);
		
		int ijt = Integer.parseInt(jt);
		
		String typeNum = getVariable("typenum");
		int itypeNum = Integer.parseInt(typeNum);
		
		
		if (ijt > itypeNum) 
		{
			setExtension("i");
			setPriority("1");
			return;
		}
		
		String jobtype = getVariable("vdrtype" + ijt);
		
		setVariable("JOBTYPE", jobtype);
		
		String bldgCode = getVariable("BlgCode");
		
		
		
		String cmd = "GETVENDORINFOBYJOBTYPE";
		String[] opts = new String[2];
		opts[0] = bldgCode;
		opts[1] = jobtype;
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		if (res[0].equals("0"))
		{
			setExtension("notfound");
			setPriority("1");
			return;
		}
		
		String vendor = res[1];
		String vdrphone = res[2];
		String vendornick = res[3];
		
		String hasW9 = res[4];
		String hasIns = res[5];
		
		setVariable("VARTEST", vendor + " " + vdrphone 
				               + " " + vendornick + " " 
				               + hasW9 + " " + hasIns);
		
		
		setVariable("VDRNUM", res[0]);
		
		if (res[0].equals("1"))
		{
		
		
		  setVariable("VENDOR", vendor);
		  setVariable("vdrphone", vdrphone);
		  setVariable("VENDORNICK", vendornick);
		
		  setVariable("HASW9", hasW9);
		  setVariable("HASINS", hasIns);
		
		}else
		{
			String[] vdrs = vendor.split(":");
			String[] vdrphones = vdrphone.split(":");
			String[] vdrnicks = vendornick.split(":");
			String[] w9s = hasW9.split(":");
			String[] inss = hasIns.split(":");
			
			int num = Integer.parseInt(res[0]);
			String vdrPrompt = "";
			for (int i=1;i<=num;i++)
			{
				  setVariable("VDR" + i, vdrs[i-1]);
				  setVariable("VDRP" + i, vdrphones[i-1]);
				  setVariable("NICK" + i, vdrnicks[i-1]);
				
				  setVariable("W9" + i, w9s[i-1]);
				  setVariable("INS" + i, inss[i-1]);
				
				  vdrPrompt = vdrPrompt + "Press " + i + " for " + vdrs[i-1] + ",";
					
				
				
			}
			
			
			
		
			
			String unid = getVariable("UNIQUEID");
			String fname = "/tmp/allvdrs" + unid;
			
			
			String tmpTxtFile = fname + ".txt";  // temporay file
			
			WriteToFile(tmpTxtFile, vdrPrompt);   // write the tip1 to the tmp file.
	    	 
	    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
				
			setVariable("pAllVdrs", fname);	
			
			
			
			
			
			
			
			
			
		}
		
		

	}
	
	
	private void ConverToWav(String w, String t)
	   {
		   
		   String[] cmds = new String[3];
		   cmds[0] = "/var/javalib/swift.sh";
		   cmds[1] = w;
		   cmds[2] = t;
		   
		   runshellcmds(cmds);

		   
		  
		   String[] rmcmds = new String[2];
		   
		   rmcmds[0] = "rm";
		   rmcmds[1] = t;
		   
		   runshellcmds(rmcmds);
		   // /var/javalib/swift.sh w t
		   
		       
		   
	   }
	   
	
	private void runshellcmds(String[] cmds)
	{
          Process pid = null;

      try{

           pid = Runtime.getRuntime().exec(cmds);
  
          if (pid != null)
          {
	            pid.waitFor();
          }
  
  
  
       }catch(Exception e)
     {
	   
	   System.out.println(e);
	   
      } 
		   
		   
		   
		   
	   }
	
	private void WriteToFile(String f, String c)
	{
		try{
		  FileWriter resultFile = new FileWriter(f);
		  PrintWriter myFile = new PrintWriter(resultFile);
		
		  myFile.println(c);
		  resultFile.close(); 
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	
		
		
		
		
		
		
		
		
		

	}

}
