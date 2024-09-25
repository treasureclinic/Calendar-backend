package com.treasure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.treasure.dto.WebSocketMessage;

@Service
public class WebSocketService {
		
		private final Gson gson = new Gson();
		
		@Autowired
		private TimeService timeService;
		
	    @Autowired
	    private SimpMessagingTemplate messagingTemplate;

	    public void sendSuccessMessage(String message) {
	        messagingTemplate.convertAndSend("/topic/notifications", gson.toJson(new WebSocketMessage("1", message, timeService.getFormat1Timestamp())));
	    }
	    
	    public void sendFailedMessage(String message) {
	        messagingTemplate.convertAndSend("/topic/notifications", gson.toJson(new WebSocketMessage("2", message, timeService.getFormat1Timestamp())));
	    }
	    
	    public void sendVerifyRecordMessage(String message) {
	    	messagingTemplate.convertAndSend("/topic/notifications", gson.toJson(new WebSocketMessage("3", message, timeService.getFormat1Timestamp())));
	    }
	    
}
