/*
 * Created on Jun 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.master.util;
import java.text.*;
import java.util.*;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DateFormatter {
	public static String SHORT_DATE = "dd/MM/yyyy",
			DATE = "dd ' de ' MMMM ' de ' yyyyy",
			SHORT_TIME = "HH:mm",
			TIME = "HH:mm:ss",
			DATE_TIME1 = "HH:mm ' do dia' dd/MM/yyyy",
			DATE_TIME2 = "HH:mm ' do dia' dd/MM/yyyy";


	public DateFormatter(){
		
	}
	public static String calendarToString (Calendar calendar, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date currentTime_1 = calendar.getTime();
		return formatter.format(currentTime_1);
	}
	public static Calendar stringToCalendar (String strDate, String format){
		String data = strDate.substring(8, 10) + "/" + strDate.substring(5, 7) + "/" + strDate.substring(0, 4);
		strDate = data;
		while (strDate.indexOf("-") != -1) strDate = strDate.replace('-', '/');
		
		
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			Date date = formatter.parse(strDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

    public static String getHoraHM()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat(SHORT_TIME);
        Date date = calendar.getTime();
        String s = simpledateformat.format(date);
        return simpledateformat.format(date);
    }

}
