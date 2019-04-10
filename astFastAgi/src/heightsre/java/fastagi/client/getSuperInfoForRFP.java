package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getSuperInfoForRFP extends BaseAgiScript {

	@Override
	public void service(AgiRequest request, AgiChannel channel)
			throws AgiException {
		// TODO Auto-generated method stub
     /*   String cmd = "GETSUPERINFOFORRFP";
		
		String blgCode = getVariable("BlgCode");
		String unid = getVariable("UNIQUEID");
		
	//	String fname = "/tmp/super" + unid;
		
		String[] opts = new String[1];
		opts[0] = blgCode;
		
		String url = "http://www.heightsre.com/Examples/Test/DailyCal.nsf/AstPortal";
		
		
        NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		setVariable("DOCID", res[0]);
		*/
	/*	String SuperPromptOut = res[1];
		
		String tmpTxtFile = fname + ".txt";  // temporay file
		
		WriteToFile(tmpTxtFile, SuperPromptOut);   // write the tip1 to the tmp file.
    	 
    	ConverToWav(fname + ".wav", tmpTxtFile);  // convert it to wav file
			
		setVariable("SuperPromptOut", fname);
		
		*/
		
		
		

	}

}
