package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CreateForLVGram {
	
	public String makeLVGram(String subDir, String filename, String count, String[] content, String type)
	{
		
		String[] opts = new String[content.length + 4];
		opts[0] = subDir;
		opts[1] = filename;
		opts[2] = count;
		opts[3] = type;
		
		for (int i=0; i<content.length; i++)
		{
			opts[i+4] = content[i];
			
			
			
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
		
		String type = options[3];
		
		try{
			resultFile = new FileWriter(tf + "/" + fname);
			PrintWriter myFile = new PrintWriter(resultFile); 
			
			
			
			
			
				
			
			myFile.write("#ABNF 1.0 UTF-8;\r\n");
			
			myFile.write("mode " + type + ";\r\n");
			
			myFile.write("language en-US;\r\n");

			myFile.write("tag-format <semantics/1.0.2006>;\r\n");

			myFile.write("root $directive;\r\n");

		//	myFile.write(;
			
			String content = "";
			String directive = "$directive = ";
			
		    for (int i=1; i<=count;i++)
		    {
		      String vlu = options[i+3];
		      
		      
		      if (vlu.contains(":")){
		    	  String[] kv = vlu.split(":");
		    	  content = content + "$R" + i + "= (" + kv[0] + ") {out = \"" + kv[1] + "\"};\r\n";
		    	  
		      }else
		    	  content = content + "$R" + i + "= (" + vlu + ") {out = \"" + vlu + "\"};\r\n";  
		      
		    //  if (i<count)
		        
		     // else
		    	//  content = content + "(" + vlu + ") {out = \"" + vlu + "\"};";
		       directive += "$R" + i + "|";
					
		    }
		    
		    
		    if (directive.endsWith("|"))
		    		directive = directive.substring(0, directive.length() - 1);
		    
		    content = content + directive + ";";//"(keypad) {out = \"keypad\"};";
		    
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
