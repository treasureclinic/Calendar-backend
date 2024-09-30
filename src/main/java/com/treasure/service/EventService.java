package com.treasure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treasure.model.EventData;
import com.treasure.repository.EventDataRepository;
import com.treasure.repository.UserRepository;

@Service
public class EventService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventDataRepository eventRepository;
	
	@Autowired
	private TimeService timeService;
	
	
	public void saveEventData(EventData eventData) {
		
		String timestamp = timeService.getFormat2Timestamp();
		
		if (eventData.getEventId() == null) {
			
			eventData.setCreateDate(timestamp.substring(0, 8));
			eventData.setCreateTime(timestamp.substring(8, 14));
			
			eventData.setStatus("0");
		}
		
		eventData.setLastDate(timestamp.substring(0, 8));
		eventData.setLastTime(timestamp.substring(8, 14));
		
		this.eventRepository.save(eventData);
		
	}


	public void deleteById(Integer eventId) {
		
		String timestamp = timeService.getFormat2Timestamp();
		
		EventData eventData = eventRepository.findById(eventId).get();
		
		eventData.setLastDate(timestamp.substring(0, 8));
		eventData.setLastTime(timestamp.substring(8, 14));
		eventData.setStatus("9");
		
		eventRepository.save(eventData);
	}
	
	
	
}
