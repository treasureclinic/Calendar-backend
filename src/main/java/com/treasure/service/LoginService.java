package com.treasure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treasure.config.SecurityConfig;
import com.treasure.model.User;
import com.treasure.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private SecurityConfig securityConfig;

	public User loginUser(String username, String password) {
		
	    User user = userRepository.findByUsername(username);
	    
	    if (user == null) {
	    	
	    	return null; // 查無userId
	    	
	    } else if (securityConfig.matches(password, user.getPasswordHash())) {
	    	// 登入後改寫登入時間
	    	String timestamp = timeService.getTomorrowFormat2Timestamp();
	    	
	    	user.setSignInDate(timestamp.substring(0, 8));
	    	user.setSignInTime(timestamp.substring(8, 14));
	    	
	    	userRepository.save(user);
	    	return user; // 登入成功
	    }
	    
	    return null; // 密碼不符
	}
	
	public String signOut(String username) {
		
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			return "0";
		}
		
		String timestamp = timeService.getTomorrowFormat2Timestamp();
		
		user.setSignOutDate(timestamp.substring(0, 8));
		user.setSignOutTime(timestamp.substring(8, 14));
		
		userRepository.save(user);
		
		return "1";
	}
}
