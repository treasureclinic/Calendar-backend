package com.treasure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "User")
public class User {
	
	@Id
	private String userId;
	
	private String username;
	
	private String email;
	
	private String passwordHash;
	
	private String rememberPwd;
	
	private String sendEmail;
	
	private String level;
	
	private String department;
	
	private String createDate;
	
	private String createTime;
	
	private String lastDate;
	
	private String lastTime;
	
	private String signInDate;
	
	private String signInTime;
	
	private String signOutDate;
	
	private String signOutTime;
	
	private String status;
}
