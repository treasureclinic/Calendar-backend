package com.treasure.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.treasure.model.EventData;
import com.treasure.model.User;
import com.treasure.repository.EventDataRepository;
import com.treasure.repository.UserRepository;
import com.treasure.service.EmailService;
import com.treasure.service.EventService;

@RestController
public class CalendarController {
	
	private final Gson gson = new Gson();
	
	@Autowired
	private EventService eService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EventDataRepository eRepository;
	
	@Autowired
	private UserRepository uRepository;
	
	@PostMapping("/saveEventData")
	public EventData saveEventData(@RequestBody String data) {
		
		EventData eventData = gson.fromJson(data, EventData.class);
		
		try {
			// 儲存
			eService.saveEventData(eventData);
			
			// 發送email通知
			User consultant = uRepository.findByUsername(eventData.getConsultant());
			
			if (consultant.getSendEmail().equals("1")) {
				emailService.sendEventDataEmail(consultant, eventData);
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
		return eventData;
	}
	
	@GetMapping("/getEventDatas")
	public ArrayList<EventData> getEventDatas() {
		
		try {
			
			ArrayList<EventData> eventDatas = eRepository.findEventDatas();			
			
			return eventDatas;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
		} 
	}
	
	@PostMapping("/getEventDataById")
	public EventData getEventDataById(@RequestBody Integer eventId) {
		
		try {
			
			Optional<EventData> data = eRepository.findById(eventId);		
			
			return data.get();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
		} 
	}
	
	@PostMapping("/deleteEventData")
	public void deleteEventData(@RequestBody Integer eventId) {
		
		eService.deleteById(eventId);
		
			
	}
}
