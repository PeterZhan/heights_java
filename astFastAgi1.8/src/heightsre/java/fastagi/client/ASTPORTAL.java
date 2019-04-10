package heightsre.java.fastagi.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

//import heightsre.java.fastagi.soapstub.AstPortalProxy;

public class ASTPORTAL {
	// private static String url =
	// "http://www.heightsre.com/Examples/Test/LegalOff.nsf/AstPortal";

	// private static AstPortalProxy service = new AstPortalProxy();
	public static String[] generalCommand(String cmd, String[] options,
			String dbname) throws RemoteException {
		String url = "http://www.heightsre.com/Examples/Test/" + dbname
				+ "/AstPortal";
		
		NotesWSClient wsclient = new NotesWSClient(url);

		String[] res = wsclient.generalCommand(cmd, options);
	//	AstPortalProxy service = new AstPortalProxy();
	//	service.setEndpoint(url);
	//	String[] res = service.generalCommand(cmd, options);
		// run the command for this function
	//	service = null;
		return res;
	}

	public static String[] generalCommand(String cmd, String[] options,
			String fpath, String dbname) throws IOException {
		File f = new File(fpath);

		long fsize = f.length();
		byte[] data = new byte[(int) fsize]; // allocat enough size for the
												// recording data

		FileInputStream fin = new FileInputStream(f);

		fin.read(data); // get the data into byte array

		fin.close();

		//AstPortalProxy service = new AstPortalProxy();

		String url = "http://www.heightsre.com/Examples/Test/" + dbname
				+ "/AstPortal";

		NotesWSClient wsclient = new NotesWSClient(url);

		String[] res = wsclient.generalCommand(cmd, options, data);
		
	//	service.setEndpoint(url);
	//	String[] res = service.generalCommand(cmd, options, data);
		// run the command for this function
	//	service = null;
		data = null;
		return res;
	}
	
	
	
	public static String CreateWavPromts(String f, String prompt)
	{
		

		String fullname = "/tmp/" + f;
		
		
		String tmpTxtFile = fullname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, prompt);   // write the tip1 to the tmp file.
   	 
   	    ConverToWav(fullname + ".wav", tmpTxtFile);  // convert it to wav file
			
	//	setVariable("allopenpo", fname); 
		
		
		return fullname;
		
		
		
	}

	private static void ConverToWav(String w, String t) {

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

	private static void runshellcmds(String[] cmds) {
		Process pid = null;

		try {

			pid = Runtime.getRuntime().exec(cmds);

			if (pid != null) {
				pid.waitFor();
			}

		} catch (Exception e) {

			System.out.println(e);

		}

	}

	private static void WriteToFile(String f, String c) {
		try {
			FileWriter resultFile = new FileWriter(f);
			PrintWriter myFile = new PrintWriter(resultFile);

			myFile.println(c);
			resultFile.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
