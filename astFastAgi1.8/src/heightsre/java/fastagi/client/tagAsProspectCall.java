package heightsre.java.fastagi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class tagAsProspectCall extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String empName = getVariable("EMPNAME");
		String custDocid = getVariable("CUSTDOCID");
		String orgDocid = getVariable("ORGDOCID");
		String sCode = getVariable("BlgCode");

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=CALLPROSPECT&ORGDOCID="

				+ orgDocid
				+ "&EMPNAME="
				+ empName
				+ "&CODE="
				+ sCode
				+ "&APTNO=BUILDING";

		try {
			String html = getHTML(url);
			if (html.contains("Successfully"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getHTML(String urlToRead) throws Exception {

		urlToRead = urlToRead.replace(" ", "%20");
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();

		setVariable("HTML", result.toString());

		return getResult(result.toString());
	}

	private String getResult(String html) throws AgiException {
		int p1 = html.indexOf("<h3>");
		int p2 = html.indexOf("</h3>");
		String result = html.substring(p1 + 4, p2);
		setVariable("RSTULT", result);
		return result;
	}

}
