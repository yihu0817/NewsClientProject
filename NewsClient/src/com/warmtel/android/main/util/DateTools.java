package com.warmtel.android.main.util;

import java.util.Calendar;
import java.util.Date;

public class DateTools {

	public static final int WEEKDAYS = 7;
	  
	 public static String[] WEEK_EN= {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THRUSDAY","FRIDAY","SATURDAY"};
	 public static String[] WEEK_CH= {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	 
	 public static String date2week(Date date)
	 {
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			 int dayIndex = c.get(Calendar.DAY_OF_WEEK);  
			    if (dayIndex < 1 || dayIndex > WEEKDAYS) {  
			        return null;  
			    }  
			    return WEEK_CH[dayIndex - 1];  
	 }
	

}
