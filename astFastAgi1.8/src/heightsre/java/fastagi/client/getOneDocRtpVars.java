package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class getOneDocRtpVars extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String[] DOCArray = getVarArray("RFPDOCS"); //get document array
		String[] ARArray = getVarArray("RFPADDRS"); // get building address array
		String[] JTArray = getVarArray("RFPJTITLES"); // get job title array
		String[] JNArray = getVarArray("RFPJNUMS");  // get job number array
		String[] POArray = getVarArray("RFPPOS"); // get PO numbers
		
		
		
	//	String po = getVariable("PN1");
		setVariable("CURPO", POArray[0]); // set the first element as current PO
		
	//	String docid = getVariable("PO" + po);
		
		setVariable("CURDOC", DOCArray[0]);  // set current document id
		
	//	String jobnumber = getVariable("JN" + po);
		
		setVariable("CURJN", JNArray[0]);  // set currentjob number
		
    //    String jobaddress = getVariable("AR" + po);
		
		setVariable("CURAR", ARArray[0]);  // set current buidling address
		
     //  String jobtitle = getVariable("JT" + po);
		
		JTArray[0] = JTArray[0].replace(",", "\\\\,");
		
		setVariable("CURJT", JTArray[0]);  // set current job title
		
		
		
		
		

	}
	
	// get the arrey from a string seperated by ":"
	private String[] getVarArray(String varname) throws AgiException
	{
		String[] res = null;
		
		String lstr = getVariable(varname);  // get variable
		
		if (lstr.endsWith(":"))
			lstr = lstr.substring(0, lstr.length()-1); // delete the last character ':'
		
		
		res = lstr.split(":");  // split by ':'
		
		
	    return res;
	}
	

}
