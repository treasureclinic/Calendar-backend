package com.treasure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserData {

	String userId;
	
	String username;
	
	String email;
	
	String department;
	
	String level;
	
	String rememberPwd;
	
	String sendEmail;
	
	String status;
}
