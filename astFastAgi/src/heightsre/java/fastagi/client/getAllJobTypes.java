package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getAllJobTypes extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
		
		String cmd = "GETALLJOBTYPES";
		String[] opts = new String[1];
		String bgcode = getVariable("BlgCode");
		opts[0] = bgcode;
		
		
		
		
        String url = "http://www.heightsre.com/Examples/Test/Category.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String typeNum = res[0];
		int iTypeNum = Integer.parseInt(typeNum);
		
		setVariable("typenum", typeNum);
		
		String jtPrompt = "";
		String jtPromptLV = "Please say the type of job,you have following options, say ";
		
		for (int i=1; i<=iTypeNum; i++)
		{
			setVariable("jt" + i, res[i]);
			
			jtPrompt = jtPrompt + "Press " + i + " for " + res[i] + ",";
			jtPromptLV = jtPromptLV + res[i] + ",";
			
		}
		
		String unid = getVariable("UNIQUEID");
		String fname = "/tmp/alljt" + unid;
		
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, jtPrompt);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("alljobtypes", fname);	
		
		//***********************************************
		if (jtPromptLV.endsWith(","))
			jtPromptLV = jtPromptLV.substring(0,jtPromptLV.length()-1);
		
		jtPromptLV = jtPromptLV + " or keypad.";
		
		fname = fname + "LV";
		
        tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, jtPromptLV);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("pSay", fname);	
		
		
		
		
		String gramFile = "/opt/grammer/spservicecall/spjobtypes/" + "alltypes.gram";
	/*	
		File f = new File(gramFile);
		if (!f.exists())
		{
			String subDir = "spservicecall/spjobtypes/";
			String filename =  bgcode + ".gram";
			String count = typeNum;
			
			String[] ct = new String[iTypeNum];
			for (int i=0; i<iTypeNum; i++)
			{
				ct[i] = res[i+1];
				
				
			}
			
			
			makeLVGram(subDir, filename, count, ct);
			
			
			
		}*/
		
		
		setVariable("GRAM", gramFile);
		
		//*************************************************

	}
	
	private String makeLVGram(String subDir, String filename, String count, String[] content)
	{
		
		String[] opts = new String[content.length + 3];
		opts[0] = subDir;
		opts[1] = filename;
		opts[2] = count;
		
		for (int i=0; i<content.length; i++)
		{
			opts[i+3] = content[i];
			
			
			
		}
		
		return createLumonVoxGram(opts);
		
	}
	
	
	
	private String createLumonVoxGram(String[] options)
	{
		
		String pp = "/opt/grammer/" + options[0];
		
		String ppt = pp;
		if (ppt.endsWith("/"))
			ppt = ppt.substring(0, ppt.length()-1);
		
		File tf = new File(ppt);
		
		if (!tf.exists()) 
			{ 
			  tf.mkdirs();
			  tf.setWritable(true, false);
			}
		
		
		String fname = options[1];
		
		
		FileWriter resultFile = null;
		int count = Integer.parseInt(options[2]);
		
		try{
			resultFile = new FileWriter(tf + "/" + fname);
			PrintWriter myFile = new PrintWriter(resultFile); 
			myFile.write("#ABNF 1.0;\r\n");
			
			myFile.write("mode voice;\r\n");
			
			myFile.write("language en-US;\r\n");

			myFile.write("tag-format <semantics/1.0>;\r\n");

			myFile.write("root $directive;\r\n");

			myFile.write("$directive = ");
			
			String content = "";
		    for (int i=1; i<=count;i++)
		    {
		      String vlu = options[i+2];
		      
		    //  if (i<count)
		        content = content + "(" + vlu + ") {out = \"" + vlu + "\"} | \r\n";
		     // else
		    	//  content = content + "(" + vlu + ") {out = \"" + vlu + "\"};";
		    
					
		    }
		    content = content + "(keypad) {out = \"keypad\"};";
		    
		    myFile.write(content);
		  
		}catch(Exception e)
		{
			return e.toString();
		}finally
		{
			try{
			   resultFile.close(); 
			}catch(Exception e){
				return e.toString();
			}
		}

		return "OK";
		
		
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
