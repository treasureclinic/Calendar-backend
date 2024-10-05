package com.treasure.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treasure.config.SecurityConfig;
import com.treasure.dto.UserData;
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
	
	public User saveUser(UserData userData, String newPassword) {
		
		String timestamp = timeService.getFormat2Timestamp();
		
		User user;
		
		if (newPassword != null) {
			
			user = new User();
			user.setUserId(newUserId(userData.getDepartment()));
			user.setUsername(userData.getUsername());
			user.setPasswordHash(securityConfig.encodePassword(newPassword));
			user.setEmail(userData.getEmail());
			user.setRememberPwd(userData.getRememberPwd());
			user.setSendEmail((userData.getSendEmail()));
			user.setCreateDate(timestamp.substring(0, 8));
			user.setCreateTime(timestamp.substring(8, 14));
			user.setStatus("0");
			
		} else {
			
			user = uRepository.findById(userData.getUserId()).get();
			
		}
		
		user.setLevel(userData.getLevel());
		user.setDepartment(userData.getDepartment());
		user.setLastDate(timestamp.substring(0, 8));
		user.setLastTime(timestamp.substring(8, 14));
		
		return uRepository.save(user);
	}

	public User changeUserPassword(User user, String password) {
		
		user.setPasswordHash(securityConfig.encodePassword(password));
		
		return uRepository.save(user);
	}
	
	public User changeUserParam(User user, String param, String type) {
		
		switch (type) {
			case "username":
				user.setUsername(param);
				break;
			case "email":
				user.setEmail(param);
				break;
			case "rememberPwd":
				user.setRememberPwd(param);
				break;
			case "sendEmail":
				user.setSendEmail(param);
				break;
			case "status":
				user.setStatus(param);
				break;
		}
			
		return uRepository.save(user);
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
	
	public String createNewPassword() {
        // 定義字母和數字的字元範圍
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String allChars = letters + numbers;

        // 創建一個隨機數生成器
        Random random = new Random();

        // 至少包含一個字母和一個數字
        StringBuilder password = new StringBuilder();

        // 隨機添加一個字母
        password.append(letters.charAt(random.nextInt(letters.length())));

        // 隨機添加一個數字
        password.append(numbers.charAt(random.nextInt(numbers.length())));

        // 隨機生成其他字符直到總長度為8位
        for (int i = 2; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // 將生成的字符進行打亂以避免字母和數字總是出現在同樣位置
        return shuffleString(password.toString());
    }

    // 用於打亂字符串的函數
    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        Random random = new Random();

        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }

        return new String(characters);
    }
	
}
