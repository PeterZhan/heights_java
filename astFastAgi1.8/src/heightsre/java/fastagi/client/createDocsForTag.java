package heightsre.java.fastagi.client;

import java.rmi.RemoteException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;



import java.io.*;
import java.net.*;

public class createDocsForTag extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		/*
		 * 
		 * 
		 * 
		 * m_ToolTip.AddTool(&m_moveout, _T("Tag as Moveout document"));
		 * m_ToolTip.AddTool(&m_image, _T("Create Call report of check image"));
		 * 
		 * m_ToolTip.AddTool(&m_callrenew,
		 * _T("Create Call report of Lease renewal"));
		 * m_ToolTip.AddTool(&m_callrent,
		 * _T("Create Call report of rent payment"));
		 * 
		 * m_ToolTip.AddTool(&m_callrentstate,
		 * _T("Create Call report of rent statement"));
		 * 
		 * m_ToolTip.AddTool(&m_complaint, _T("tag as tenant complaint"));
		 */

		String empName = getVariable("EMPNAME");
		String custDocid = getVariable("CUSTDOCID");
		String orgDocid = getVariable("ORGDOCID");

		String x = getVariable("EXTEN");

		int ix = Integer.parseInt(x);

		switch (ix) {

		case 1:
			TagTenantCompaint(empName, custDocid, orgDocid);
			break;

		case 2:
			TagRentStatement(empName, custDocid, orgDocid);
			break;

		case 3:
			TagRentPayment(empName, custDocid, orgDocid);
			break;

		case 4:
			TagLeaseRenewal(empName, custDocid, orgDocid);
			break;

		case 5:
			TagCheckImage(empName, custDocid, orgDocid);
			break;

		case 6:
			TagMoveOut(empName, custDocid, orgDocid);
			break;

		case 7:
			Tag3DNReport(empName, custDocid, orgDocid);
			break;
		
		case 8:
			TagBillReport(empName, custDocid, orgDocid);
			break;
		/*case 8:
			TagProspect(empName, custDocid, orgDocid);
			break;	*/
			
		default:
			setExtension("i");
			setPriority("1");
			break;

		}

	}
	private void TagBillReport(String empName, String custDocid, String orgDocid)
			throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=TAGWSCCHARGE&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

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
	
	private void TagProspect(String empName, String custDocid, String orgDocid)
			throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=CALLPROSPECT&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

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
	
	private void Tag3DNReport(String empName, String custDocid, String orgDocid)
			throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=3DAYNOTICECALLREPORT&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

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
	
	private String getResult(String html) throws AgiException
	{
		int p1 = html.indexOf("<h3>");
		int p2 = html.indexOf("</h3>");
		String result = html.substring(p1 + 4, p2);
		setVariable("RSTULT", result);
		return result;
	}

	private void TagMoveOut(String empName, String custDocid, String orgDocid) {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=MOVEOUT&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;
       
		
		try {
			setVariable("URL", url);
			
			String html = getHTML(url);
			
			setVariable("HTML", html);
			
		///	String result = getResult(html);
			
			if (html.contains("Successfully"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				setVariable("EXCEPTION", e.getMessage());
			} catch (AgiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		/*
		 * String[] opts = new String[3]; opts[0] = empName; opts[1] =
		 * custDocid; opts[2] = orgDocid;
		 * 
		 * String cmd = "TagMoveOut";
		 * 
		 * try { String res[] = ASTPORTAL.generalCommand(cmd, opts,
		 * "heightscalls.nsf"); String result = res[0];
		 * 
		 * if (result.equals("OK")) setVariable("idx", "9"); else
		 * setVariable("idx", "10");
		 * 
		 * } catch (RemoteException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	private void TagCheckImage(String empName, String custDocid, String orgDocid)
			throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=LATESTIMAGE&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

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

		
		/*String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = custDocid;
		opts[2] = orgDocid;

		String cmd = "TagCheckImage";

		try {
			String res[] = ASTPORTAL.generalCommand(cmd, opts,
					"heightscalls.nsf");
			String result = res[0];

			if (result.equals("OK"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	private void TagLeaseRenewal(String empName, String custDocid,
			String orgDocid) throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=CALLREPORTOFLRENEWAL&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

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

		
		
		
		/*String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = custDocid;
		opts[2] = orgDocid;

		String cmd = "TagLeaseRenewal";

		try {
			String res[] = ASTPORTAL.generalCommand(cmd, opts,
					"heightscalls.nsf");
			String result = res[0];

			if (result.equals("OK"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	private void TagRentPayment(String empName, String custDocid,
			String orgDocid) throws AgiException {

		/*String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = custDocid;
		opts[2] = orgDocid;

		String cmd = "TagRentPayment";

		try {
			String res[] = ASTPORTAL.generalCommand(cmd, opts,
					"heightscalls.nsf");
			String result = res[0];

			if (result.equals("OK"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=CALLREPORTOFRPAYMENT&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

		try {
			String html = getHTML(url);
			if (html.contains("Successfully"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			setVariable("EXCEPT", e.getMessage());
			e.printStackTrace();
		}


	}

	private void TagRentStatement(String empName, String custDocid,
			String orgDocid) throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=CALLREPORTOFRSTATEMENT&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID="
				+ orgDocid + "&EMPNAME=" + empName;

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


		
		/*String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = custDocid;
		opts[2] = orgDocid;

		String cmd = "TagRentStatement";

		try {
			String res[] = ASTPORTAL.generalCommand(cmd, opts,
					"heightscalls.nsf");
			String result = res[0];

			if (result.equals("OK"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	private void TagTenantCompaint(String empName, String custDocid,
			String orgDocid) throws AgiException {

		String url = "http://www.heightsre.com/8525760F004BB8DF/generalAgentForAsterisk?openAgent&"
				+ "cmd=TAGTENCOMP&CUSTDOCID="
				+ custDocid
				+ "&ORGDOCID=" + orgDocid 
				+ "&EMPNAME=" + empName;
			//	+ "&PHONENUM=" + 

		
		setVariable("URL", url);
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
		/*String[] opts = new String[3];
		opts[0] = empName;
		opts[1] = custDocid;
		opts[2] = orgDocid;

		String cmd = "CREATTENANTCOMPLAINTTAG";

		try {
			String res[] = ASTPORTAL.generalCommand(cmd, opts,
					"heightscalls.nsf");
			String result = res[0];

			if (result.equals("OK"))
				setVariable("idx", "9");
			else
				setVariable("idx", "10");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
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

}
