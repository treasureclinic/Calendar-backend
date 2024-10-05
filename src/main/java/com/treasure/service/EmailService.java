package com.treasure.service;

import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.treasure.model.EventData;
import com.treasure.model.User;
import com.treasure.repository.UserRepository;


@Service
public class EmailService {
	
    private JavaMailSender mailSender;
    
    @Value("${app.client.url}")
    private String clientUrl;
    
    @Value("${server.port}")
    private String port;
    
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Autowired
    public TimeService timeService;
    
    @Autowired
    public UserRepository uRepository;
    
	public Boolean sendEventDataEmail(User consultant, EventData eventData) {
			
		ArrayList<User> userList = new ArrayList<User>();
		userList.add(consultant);
		
		// 確認醫師、護士的emailSend Flg
		User doctor = uRepository.findByUsername(eventData.getDoctor());
		
		if (doctor != null && 
			doctor.getSendEmail() != null && 
			doctor.getSendEmail().equals("1") &&
			doctor.getEmail() != null
			) {
			userList.add(doctor);
		}
		
		User nurse = uRepository.findByUsername(eventData.getNurse());
		
		if (nurse != null && 
			nurse.getSendEmail() != null && 
			nurse.getSendEmail().equals("1") &&
			nurse.getEmail() != null
			) {
			userList.add(nurse);
		}
		
		// 寄送email
		for (User user: userList) {
			try {
				
				MimeMessage message = mailSender.createMimeMessage();
	    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	    		
	    		helper.setFrom("treasurecliniccalendar@gmail.com");
	    		helper.setTo(user.getEmail());
	    		helper.setSubject("Treasure Clinic Reservation");
	            helper.setText("<html>" +
		                "<body>" +
		                "<h2>通知</h2>" +
		                "<h3>預約日期： "+ timeService.dateString1(eventData.getReservationDate()) + "</h3>" +
		                "<h3>預約時間： " + timeService.timeString1(eventData.getReservationStartTime()) + " ~ "  + 
		                timeService.timeString1(eventData.getReservationEndTime()) + "</h3>" + 
		                "<h3>客戶姓名： " + (eventData.getClientname() == null ? "" : eventData.getClientname()) + "</h3>" + 
		                "<h3>醫　　師： " + (eventData.getDoctor() == null ? "" : eventData.getDoctor()) + "</h3>" + 
		                "<h3>療　　程： " + (eventData.getTreatment() == null ? "" : eventData.getTreatment()) + "</h3>" + 
		                "<h3>CMSlim/美療： " + (eventData.getCmslime() == null ? "" : eventData.getCmslime()) + "</h3>" + 
		                "<h3>儀器確認： " + (eventData.getInstrumentCheck() == null ? "" : eventData.getInstrumentCheck()) + "</h3>" + 
		                "<h3>Nurse/刷手： " + (eventData.getNurse() == null ? "" : eventData.getNurse()) + "</h3>" + 
		                "<h3>備　　註： " + (eventData.getRemark() == null ? "" : eventData.getRemark()) + "</h3>" + 
		                "<h5>---此為系統自動通知，請勿回覆。---</h5>" + 
		                "</body>" +
		                "</html>", true); 
	            
	            mailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
	    		
	    	}
	    	
		}
		
		return true;
	}

	public Boolean sendNewPassword(String email, String newPassword) {
		try {
			
			MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    		
    		helper.setFrom("treasurecliniccalendar@gmail.com");
    		helper.setTo(email);
    		helper.setSubject("Treasure Clinic NewPassword");
            helper.setText("<html>" +
	                "<body>" +
	                "<h3>密　　碼： " + newPassword +"</h3>" +
	                "<h5>---提醒您，登入後請立即修改密碼。此為系統自動通知，請勿回覆。---</h5>" + 
	                "</body>" +
	                "</html>", true); 
            
            mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
    		
    	}
		return true;
		
	}
            
    	
    
}
