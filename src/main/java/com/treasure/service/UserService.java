package com.treasure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treasure.config.SecurityConfig;
import com.treasure.dto.SignUpForm;
import com.treasure.model.User;
import com.treasure.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository uRepository;
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private SecurityConfig securityConfig;
	
	public void addNewUser(SignUpForm signUpForm) {
		
		String timestamp = timeService.getFormat2Timestamp();
		
		User user = new User();
		
		user.setUserId(newUserId(signUpForm.getDepartment()));
		user.setEmail(signUpForm.getEmail());
		user.setPasswordHash(securityConfig.encodePassword(signUpForm.getPassword()));
		user.setLevel(signUpForm.getLevel());
		user.setDepartment(signUpForm.getDepartment());
		user.setCreateDate(timestamp.substring(0, 8));
		user.setCreateTime(timestamp.substring(8, 14));
		user.setLastDate(timestamp.substring(0, 8));
		user.setLastTime(timestamp.substring(8, 14));
		user.setStatus("0");
		
		uRepository.save(user);
	}

	private String newUserId(String department) {
		
		List<User> userList = uRepository.findUsersByUserIdSubstring(department);
		
		String newUserId;
		
		if (userList.size() > 0) {
			
			String oldUserIdNum = userList.get(userList.size() - 1).getUserId().substring(3);
			
			newUserId = String.format(department + "%02d", Integer.parseInt(oldUserIdNum) + 1);
			
		} else {
			
			newUserId = department + "01";
		}
		
		return newUserId;
	}

}
