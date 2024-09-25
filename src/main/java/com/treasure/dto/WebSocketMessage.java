package com.treasure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebSocketMessage {
	
	String mode;
	
	String message;
	
	String time;
	
}
