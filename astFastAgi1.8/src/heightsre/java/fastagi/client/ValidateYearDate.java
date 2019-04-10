package heightsre.java.fastagi.client;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.util.regex.*;
import java.util.*;
import java.text.*;

public class ValidateYearDate extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		String YearDate = getVariable("EXTEN");
		if (YearDate.endsWith("#"))
			YearDate = YearDate.substring(0, YearDate.length()-1);
		
		String year = "20" + YearDate.substring(4);
		String month = YearDate.substring(0,2);
		String day = YearDate.substring(2,4);
		
		YearDate = year + "-" + month + "-" + day;//YearDate.substring(0,2) + "/" + YearDate.substring(2,4) + "/" + YearDate.substring(4);
		
		if (!checkDate(YearDate))
		{
		  setExtension("i");
		  setPriority("1");
			
		}else
		{
			
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sfly=new SimpleDateFormat("MM/dd/yyyy");
			
			try{
			  Date dtYearDate = sf.parse(YearDate);
			  String ltime = "" + dtYearDate.getTime();
			  setVariable("LMOVEDATE", ltime);
			  
			  
			  
			  
						  
			  setVariable("MOVEDATE", sfly.format(dtYearDate));
			
			
			}catch(Exception e)
			{
				
			}
			
			
			
			
			
		}
		
		
		
		
		

	}
	
	private boolean checkDate(String date) {
		//String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		
		String eL = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)   (([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		if (b) {
		   System.out.println(date + " 格式正确");
		} else {
		   System.out.println(date + " 格式错误");
		}
		return b;
		}

}
