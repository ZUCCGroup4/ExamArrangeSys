package cn.edu.zucc.g4.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public long getDay(String startTimeStr, String endTimeStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		Date startTime;
		Date endTime;
		
		try {
			startTime = format.parse(startTimeStr);
			endTime= format.parse(endTimeStr); 
			day = (endTime.getTime()-startTime.getTime())/(24*60*60*1000);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return day+1;  
	}
}
