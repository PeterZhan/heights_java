package heightsre.java.fastagi.client;

import java.io.File;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getCurRfpRepUnheard extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
     /*   String empName = getVariable("EMPNAME");
		
		String replist = getVariable("REPLIST");
		String[] reps = replist.split(":");
		
		String curnum = getVariable("CURNUM");
		int icur = Integer.parseInt(curnum);
		
		String idx = reps[icur-1];
		
		String[] opts = new String[2];
		opts[0] = empName;
		opts[1] = idx;
		
		String cmd = "GETREPORTBYINDX";
		
		
        String url = "http://www.heightsre.com/Examples/Test/Rfp.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String filewav = res[0];
		
		String bldg = res[1];
		String bldgwithb = "";
		
		for (int i=0; i<bldg.length()-1;i++)
		{
			bldgwithb = bldgwithb + bldg.substring(i,i+1) + " ";
		}
		
		bldgwithb = bldgwithb + bldg.substring(bldg.length()-1);
	
       String fname = "/var/spool/asterisk/monitor/" + filewav;
		
		File f = new File(fname + ".WAV");
		if (!f.exists())
			fname = "/var/spool/asterisk/lastmonth/" + filewav;
		
	
		setVariable("CURDAILYREP", fname);
		setVariable("BLDG", bldgwithb);
		setVariable("REPDATE", res[2]);
		setVariable("REPTIME", res[3]);
		setVariable("PLAYHS", res[4]);
		setVariable("CALLDOC", res[5]);
		setVariable("CALLDB", "Rfp.nsf");
		setVariable("APTNO", res[6]);

      */
		
        String empName = getVariable("EMPNAME");
		
		String replist = getVariable("REPLIST");
		String[] reps = replist.split(":");
		
		String curnum = getVariable("CURNUM");
		
		String datadoc = getVariable("EMPDATA");
		
		int icur = Integer.parseInt(curnum);
		
		String idx = reps[icur-1];
		
		String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = idx;
		opts[2] = datadoc;
		
		String cmd = "GETREPORTBYINDX";
		
		
        String url = "http://www.heightsre.com/Examples/Test/heightscalls.nsf/AstPortal";
	    
		NotesWSClient wsclient = new NotesWSClient(url);
		
		String[] res = wsclient.generalCommand(cmd, opts);
		
		String filewav = res[0];
		
		String bldg = res[1];
		String bldgwithb = "";
		
		for (int i=0; i<bldg.length()-1;i++)
		{
			bldgwithb = bldgwithb + bldg.substring(i,i+1) + " ";
		}
		
		bldgwithb = bldgwithb + bldg.substring(bldg.length()-1);
	
        String fname = "/var/spool/asterisk/monitor/" + filewav;
		
		File f = new File(fname + ".WAV");
		if (!f.exists())
			fname = "/var/spool/asterisk/lastmonth/" + filewav;
		
	
		setVariable("CURDAILYREP", fname);
		
		setVariable("BLDG", bldgwithb);
		setVariable("REPDATE", res[2]);
		setVariable("REPTIME", res[3]);
		setVariable("PLAYHS", res[4]);
		setVariable("CALLDOC", res[5]);
		setVariable("APTNO", res[7]);
		setVariable("CALLDB", "Rfp.nsf");
		
		
		
	}

}
