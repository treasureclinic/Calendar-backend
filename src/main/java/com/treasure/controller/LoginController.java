package com.treasure.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.treasure.dto.LoginForm;
import com.treasure.dto.UserData;
import com.treasure.model.User;
import com.treasure.repository.UserRepository;
//import com.treasure.service.EmailService;
import com.treasure.service.LoginService;

@RestController
public class LoginController {

	private final Gson gson = new Gson();
	
	@Autowired
	private LoginService lService;
	
	@Autowired
	private UserRepository uRepository;
	
	@PostMapping("/loginCheckout")
	public UserData loginCheckout(@RequestBody String data) {
		
		try {
			
			LoginForm loginForm = gson.fromJson(data, LoginForm.class);
			
			User user = lService.loginUser(loginForm.getUsername(), loginForm.getPassword());
			
			return new UserData(
					user.getUserId(), 
					user.getUsername(), 
					user.getEmail(), 
					user.getDepartment(), 
					user.getLevel(), 
					user.getRememberPwd(),
					user.getSendEmail(),
					user.getStatus()
					);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
		
		return null;
	}
	
	@PostMapping("/signOut")
	public String signOut(@RequestBody String data) {
		
		String result;
		
		try {
			
			String userId = gson.fromJson(data, String.class);
			
			result = lService.signOut(userId);
			
		} catch (Exception e) {
			
			result = "9";
			
			e.printStackTrace();
			
		} 
		
		return result;
	}
	
	@GetMapping("/getUserNameList")
	public ArrayList<String> getUserNameList() {
		
		ArrayList<User> userList = uRepository.findUsers();
		
		ArrayList<String> usernameList = new ArrayList<String>();
		
		for (User user: userList) {
			usernameList.add(user.getUsername());
		}
		
		return usernameList;
	}
	
	
	@GetMapping("/getUserList")
	public ArrayList<UserData> getUserList() {
		
		ArrayList<User> userList = uRepository.findUsers();
		
		ArrayList<UserData> dataList = new ArrayList<UserData>();
		
		for (User user: userList) {
			dataList.add(
					new UserData(
					user.getUserId(),
					user.getUsername(),
					user.getEmail(),
					user.getDepartment(),
					user.getLevel(),
					user.getRememberPwd(),
					user.getSendEmail(),
					user.getStatus()
							)
					);
		}
		
		return dataList;
	}
	
	@PostMapping("/getUserByUsername")
	public UserData getUserByUsername(@RequestBody String data) {
		
		String username = gson.fromJson(data, String.class);
		
		User user = uRepository.findByUsername(username);
		
		return new UserData(
				user.getUserId(), 
				user.getUsername(), 
				user.getEmail(), 
				user.getDepartment(), 
				user.getLevel(), 
				user.getRememberPwd(),
				user.getSendEmail(),
				user.getStatus()
				);
		
	}
	
}
