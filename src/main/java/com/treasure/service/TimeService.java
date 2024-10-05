package com.treasure.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

	private static final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
			.withZone(ZoneId.systemDefault());

	private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
			.withZone(ZoneId.systemDefault());

	public long getCurrentTimestamp() {
		return Instant.now().toEpochMilli();
	}

	public String getFormat1Timestamp() {
		return FORMATTER1.format(Instant.ofEpochMilli(getCurrentTimestamp()));
	}

	public String getFormat2Timestamp() {
		return FORMATTER2.format(Instant.ofEpochMilli(getCurrentTimestamp()));
	}

	public String getTomorrowFormat1Timestamp() {

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime tomorrow = now.plusDays(1);

		return FORMATTER1.format(tomorrow.atZone(ZoneId.systemDefault()));
	}

	public String getTomorrowFormat2Timestamp() {

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime tomorrow = now.plusDays(1);

		return FORMATTER2.format(tomorrow.atZone(ZoneId.systemDefault()));
	}

	public boolean compareDates(String dateStr1, String dateStr2) {
		
		LocalDateTime dateTime1 = LocalDateTime.parse(dateStr1, FORMATTER2);
        
        LocalDateTime dateTime2 = LocalDateTime.parse(dateStr2, FORMATTER2);

        // 比较两个日期
        
        return dateTime1.isBefore(dateTime2);
	}
      
	public String dateString1(String dateString) {
		
		return dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6);
	}
	
	public String timeString1(String timeString) {
		
		return timeString.substring(0, 2) + ":" + timeString.substring(2, 4);
	}
}