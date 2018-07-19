package cn.edu.zucc.g4.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


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
	
	public Timestamp getExamTimeByBlock(int block, String startTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar   calendar = new GregorianCalendar(); 
		Date resultDate = null;
		if(block%3 == 1) {
			try {
				startTime = startTime + " 08:00:00";
				resultDate = format.parse(startTime);
				calendar.setTime(resultDate); 
				calendar.add(calendar.DATE,block/3); 
				resultDate=calendar.getTime();
				startTime = format.format(resultDate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Timestamp.valueOf(startTime);
		}else if(block%3 == 2){
			try {
				startTime = startTime + " 10:40:00";
				resultDate = format.parse(startTime);
				calendar.setTime(resultDate); 
				calendar.add(calendar.DATE,block/3); 
				resultDate=calendar.getTime();
				startTime = format.format(resultDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Timestamp.valueOf(startTime);
		}else if(block%3 == 0) {
			try {
				startTime = startTime + " 14:00:00";
				resultDate = format.parse(startTime);
				calendar.setTime(resultDate); 
				calendar.add(calendar.DATE,block/3); 
				resultDate=calendar.getTime();
				startTime = format.format(resultDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Timestamp.valueOf(startTime);
		}
		return null;
	}
}
