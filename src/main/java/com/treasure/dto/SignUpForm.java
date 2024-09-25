/**
 * 
 */
package com.treasure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpForm {
	
	String email;
	
	String department;
	
	String level;
	
	String password;
	
	String confirmPassword;
}
