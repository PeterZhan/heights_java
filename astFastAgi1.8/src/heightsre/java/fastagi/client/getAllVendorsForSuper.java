package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllVendorsForSuper extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		
		String cmd = "GETALLVDRSFORSUPER";
		String[] opts = new String[1];
		String bgcode = getVariable("BlgCode");
		opts[0] = bgcode;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/POReq.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String vdrNum = res[0];
		int ivdrNum = Integer.parseInt(vdrNum);
		
		setVariable("vdrnum", vdrNum);
		
		String ctPrompt = "There are " + vdrNum + " vendors for this building,";
		String ctPromptLV = "There are " + vdrNum + " vendors for this building, say ";
		
		for (int i=1; i<=ivdrNum; i++)
		{   
			
			String vdrwithnick = res[i];
			String[] vdrandnic = vdrwithnick.split(":");
			String vdr = vdrandnic[0];
			String vdrnick = vdrandnic[1];
			
			setVariable("vdr" + i, vdr);
			setVariable("vdrnick" + i, vdrnick);
			
			ctPrompt = ctPrompt + "Press " + i + " for " + vdrnick + ",";
			
			ctPromptLV = ctPromptLV + vdrnick + ",";
			
		}
		
		String unid = getVariable("UNIQUEID");
		String fname = "/tmp/allvdr" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, ctPrompt);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("spvdrlist", fname);	
		
		//***********************************************
		if (ctPromptLV.endsWith(","))
			ctPromptLV = ctPromptLV.substring(0,ctPromptLV.length()-1);
		
		ctPromptLV = ctPromptLV;
		
		fname = fname + "LV";
		
        tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, ctPromptLV);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("pSay", fname);	
		
		
		
		
		String gramFile = "/opt/grammer/spservicecall/spvendors/" + bgcode + ".gram" + 
				          "/opt/grammer/spservicecall/spvendors/" + bgcode + "k.gram" ;
		
	//	File f = new File(gramFile);
	//	if (!f.exists())
	//	{
			String subDir = "spservicecall/spvendors/";
			String filename =  bgcode + ".gram";
			String filenamek =  bgcode + "k.gram";
			String count = vdrNum;
			
			String[] ct = new String[ivdrNum];
			String[] ctkeys = new String[ivdrNum];
			for (int i=0; i<ivdrNum; i++)
			{
				int idx = i+1;
				String vdrwithnick = res[i+1];
				String[] vdrandnic = vdrwithnick.split(":");
				String vdr = vdrandnic[0];
				String vdrnick = vdrandnic[1];
				ct[i] = vdrnick+":"+ idx;
				ctkeys[i] = InsertBlankForDigits(""+idx)+":"+ idx; 
				
			}
			
			
			CreateForLVGram cflv = new CreateForLVGram();
			cflv.makeLVGram(subDir, filename, count, ct, "voice");
			cflv.makeLVGram(subDir, filenamek, count, ctkeys, "dtmf");
			
			
			
	//	}
		
		
		setVariable("GRAM", gramFile);
		
		//*************************************************
		
		
		
		
		
		

	}
	
	private String InsertBlankForDigits(String src)
    {
    	String dst = "";
    	HashSet hsD = new HashSet();
    	
    	for (int k=0;k<10;k++)
    	{
    		hsD.add("" + k);
    	}
    	
    	
    	for (int i=0; i<src.length(); i++)
    	{
    		
    		String d = src.substring(i,i+1);
    		
    		if (hsD.contains(d))
    		 	dst = dst + " " + src.substring(i,i+1) + " ";
    		else
    			dst = dst + src.substring(i,i+1);
    		
    		
    		
    	}
    	
    	if (dst.endsWith(" "))
    		dst = dst.substring(0, dst.length()-1);
    	
    	
    	if (dst.startsWith(" "))
    		dst = dst.substring(1);
    	
    	return dst;
    	
    	
    	
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
