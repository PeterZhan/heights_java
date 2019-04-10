package heightsre.java.fastagi.client;



import java.sql.Connection;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class WCBStoreVars extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String dbname = getVariable("dbname");
		String docid = getVariable("docid");
		String uniqueid = getVariable("UNIQUEID");
		
		String sql = "insert into webcallback values(\'" + uniqueid + "\',"
		                                          + "\'" + dbname   + "\',"
		                                          + "\'" + docid    + "\')";
		
		Connection con = null;
		
		DbConnection.sqlExecute(sql);
		
		
		
		
		
		
		
		
		

	}

}
