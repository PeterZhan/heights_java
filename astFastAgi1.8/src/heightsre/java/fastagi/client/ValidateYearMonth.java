package heightsre.java.fastagi.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ValidateYearMonth extends BaseAgiScript {

	@Override
	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {
		// TODO Auto-generated method stub
		
		String YearDate = getVariable("EXTEN");
		if (YearDate.endsWith("#"))
			YearDate = YearDate.substring(0, YearDate.length()-1);
		
		String year = YearDate;
		String month = "01";//YearDate.substring(0,2);
		String day = "01";
		
		YearDate = year + "-" + month + "-" + day; //YearDate = YearDate.substring(0,2) + "/" + YearDate.substring(2);
		
		if (!checkDate(YearDate))
		{
		  setExtension("i");
		  setPriority("1");
			
		}else
		{
			
            int Iyear = Integer.parseInt(year);
			
		
			
			if (Iyear < 1980)
			{
				setExtension("i");
				setPriority("1");
				return;  
			}
			
			
			
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sfly=new SimpleDateFormat("yyyy");
			
			try{
			  Date dtYearDate = sf.parse(YearDate);
			  String ltime = "" + dtYearDate.getTime();
			  setVariable("LRENODATE", ltime);
			  
			  
			  
			  
						  
			  setVariable("RENOMONTH", sfly.format(dtYearDate));
			
			
			}catch(Exception e)
			{
				
			}
			
			
			
			
			
		}
		
		

	}
	
	private boolean checkDate(String date) {
	//	String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		String eL = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)   (([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		if (b) {
			
			
			
			
			
			
		 //  System.out.println(date + " 格式正确");
		} else {
		 //  System.out.println(date + " 格式错误");
		     
		}
		
		
		
		
		
		
		return b;
		}
	
	
	

}
