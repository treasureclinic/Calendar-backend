package com.treasure.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "EventData")
public class EventData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer eventId;
	
	String reservationStartTime;
	
	String reservationEndTime;
	
	String username;
	
	String clientname;
	
	String consumeType;
	
	String treatment;
	
	String cmslime;
	
	String consultant;
	
	String instrumentCheck;
	
	String nurse;
	
	String remark;
	
	String createDate;
	
	String createTime;
	
	String lastDate;
	
	String lastTime;
	
	String status;
}
