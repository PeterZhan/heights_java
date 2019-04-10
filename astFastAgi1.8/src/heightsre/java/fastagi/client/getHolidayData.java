package heightsre.java.fastagi.client;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.sql.Connection;

public class getHolidayData extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub

		/*
		 * String dbname = "punchclk.nsf";
		 * 
		 * 
		 * String[] opts = new String[1]; opts[0] = ""; String cmd =
		 * "GETHOLIDAYDATA";
		 */
		Connection con = null;
		String sql = "select OpenClosed, PMessage, NameofDate, DATE_FORMAT(DATE(EDate), '%W %M %D %Y') as DateHoliday, If(DATE(EDate)=DATE(NOW()),'1','0') as isToday from tblspecial where DATE(NOW()) between DATE(DateDisp) and DATE(DateStop)";
		// String sql =
		// "select OpenClosed, NameofDate, DATE_FORMAT(DATE(EDate), '%W %M %D %Y') as DateHoliday, If(DATE(EDate)=DATE(NOW()),'1','0') as isToday from tblspecial";
		// setVariable("SQL", sql);

		try {
			// String[] res = ASTPORTAL.generalCommand(cmd, opts, dbname);
			// String sql =
			// "select OpenClosed, NameofDate, DATE(EDate) as DateHoliday, If(DATE(EDate)=DATE(NOW()),'1','0') as isToday from tblspecial where DATE(NOW()) between DATE(DateDisp) and DATE(DateStop)";

			con = DbConnectionApache.getConnection();

			// setVariable("CONNECTION", con.toString());

			Statement stat = con.createStatement();
			ResultSet result = stat.executeQuery(sql);
			// result.next();
			// setVariable("RESULT", result.getString("NameofDate"));
			String prompt = "";
			while (result.next()) {

				// String isHoliday = "";
				String openOrClosed = "";
				String nameHoliday = "";
				String dateHoliday = "";
				String isToday = "";
				String PMessage = "";

				// if (isHoliday.equals("1"))
				// {

				setVariable("HOLIDAY", "Yes");

				openOrClosed = result.getString("OpenClosed");
				isToday = result.getString("isToday");
				nameHoliday = result.getString("NameofDate");
				dateHoliday = result.getString("DateHoliday");
				PMessage = result.getString("PMessage");
				// if (openOrClosed.equals("Open") && isToday.equals("1"))
				// prompt = "Happy " + nameHoliday;

				// if (openOrClosed.equals("Closed")){
                setVariable("HOLIDAYTODAY", isToday);
                setVariable("OpenOrClosed", openOrClosed);
				prompt += PMessage + ". ";

				/*
				 * if (isToday.equals("1")) { prompt =
				 * "The company is closed today" + " IN OBSERVATION OF " +
				 * nameHoliday;;
				 * 
				 * 
				 * 
				 * }else {
				 * 
				 * prompt = "Please note that the Company will be closed on " +
				 * dateHoliday + " IN OBSERVATION OF " + nameHoliday;
				 * 
				 * 
				 * }
				 */

				// }

			}

			prompt = prompt.replace(",", "\\\\,");    
			prompt = prompt.replace("..", "."); 
			setVariable("HOLPROMPT", prompt);

			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			setVariable("ERROR", e.getMessage());
			e.printStackTrace();
		} finally {
			DbConnectionApache.FreeCon(con);

		}

	}

}
