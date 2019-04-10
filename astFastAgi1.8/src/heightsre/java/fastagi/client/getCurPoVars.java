package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;


// RFP CALL to get current PO number and other variables
public class getCurPoVars extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		
		String npo = getVariable("NPO");  // current  index
		
		int n = Integer.parseInt(npo);  // to integer
		
		
		
		String[] DOCArray = getVarArray("RFPDOCS");  // document id array
		String[] ARArray = getVarArray("RFPADDRS");  // building address array
		String[] JTArray = getVarArray("RFPJTITLES"); // job titles
		String[] JNArray = getVarArray("RFPJNUMS");  // job numbers
		String[] POArray = getVarArray("RFPPOS");  // PO numbers
		
		
       
		
		
		
		
		
	//	String po = getVariable("PN" + npo);
		setVariable("CURPO", POArray[n-1]);// Set current po number
		
	//	String docid = getVariable("PO" + po);
		
		setVariable("CURDOC", DOCArray[n-1]);  // set current document id
		
	//	String jobnumber = getVariable("JN" + po);
		
		setVariable("CURJN", JNArray[n-1]);  // set current job number
		
    //    String jobaddress = getVariable("AR" + po);
		
		setVariable("CURAR", ARArray[n-1]); //set current building address
		
   //     String jobtitle = getVariable("JT" + po);
		
		setVariable("CURJT", JTArray[n-1]);  // set current job title

	}
	// get the values array from a variable
	private String[] getVarArray(String varname) throws AgiException
	{
		String[] res = null;
		
		String lstr = getVariable(varname);  // get variable string value
		
		if (lstr.endsWith(":"))
			lstr = lstr.substring(0, lstr.length()-1);  // remove the last character ":"
		
		
		res = lstr.split(":");  // split by ":"
		
		
	    return res;  // return the array
	}
	

}
