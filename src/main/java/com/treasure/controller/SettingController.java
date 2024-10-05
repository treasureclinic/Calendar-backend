package com.treasure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.treasure.dto.LoginForm;
import com.treasure.dto.TypeData;
import com.treasure.dto.UserData;
import com.treasure.model.User;
import com.treasure.repository.UserRepository;
import com.treasure.service.EmailService;
import com.treasure.service.LoginService;
import com.treasure.service.UserService;

@RestController
public class SettingController {

	private final Gson gson = new Gson();
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private LoginService lService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository uRepository;
	
	@PostMapping("/checkPwd")
	public boolean checkPwd(@RequestBody String data) {
		
		LoginForm form = gson.fromJson(data, LoginForm.class);
		
		User user = uRepository.findByUsername(form.getUsername());
		
		return lService.checkPassword(user, form.getPassword());
		
	}
	
	@PostMapping("/changePwd")
	public boolean changePwd(@RequestBody String data) {
		
		LoginForm form = gson.fromJson(data, LoginForm.class);
		
		User user = uRepository.findByUsername(form.getUsername());
		
		return uService.changeUserPassword(user, form.getPassword()) != null;
		
	}
	
	@PostMapping("/resetPwd")
	public Boolean resetPwd(@RequestBody String data) {
		
		UserData userData = gson.fromJson(data, UserData.class);
		
		User user = uRepository.findById(userData.getUserId()).get();
		
		String newPassword = uService.createNewPassword();
		
		return uService.changeUserPassword(user, newPassword) != null && emailService.sendNewPassword(userData.getEmail(), newPassword);
		
	}
	@PostMapping("/saveUserData")
	public boolean saveUserData(@RequestBody String data) {
		
		TypeData typeData = gson.fromJson(data, TypeData.class);
		
		User user = uRepository.findByUsername(typeData.getUsername());
		
		return uService.changeUserParam(user, typeData.getParam(), typeData.getType()) != null;
		
	}
	
	@PostMapping("/saveUser")
	public boolean saveUser(@RequestBody String data) {
		
		UserData userData = gson.fromJson(data, UserData.class);
		
		String newPassword = null;
		
		if (userData.getUserId() == null) {
			newPassword = uService.createNewPassword();
			emailService.sendNewPassword(userData.getEmail(), newPassword);
		}
		
		User user = uService.saveUser(userData, newPassword);
		
		return user != null;
		
	}
	
	@PostMapping("/deleteUser")
	public boolean deleteUser(@RequestBody String data) {
		
		UserData userData = gson.fromJson(data, UserData.class);
		
		try {
			
			uRepository.deleteById(userData.getUserId());
			
		} catch(IllegalArgumentException e) {
			
			return false;
		}
		
		
		return true;
		
	}
	
}
