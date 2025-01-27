package com.auro.beans;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_logs")
// this is updated table for user logs logging users activity
public class UserLogs {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int slno;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="eventType")
	private String eventType;
	
	@Column(name="eventDate")
	private Date eventDate;
	
	@Column(name = "eventTime")
	private Time eventTime;
	
	@Column(name ="remarks")
	private String remarks;
	
	@Column(name="ipaddress")
	private String ipAddress;

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Time getEventTime() {
		return eventTime;
	}

	public void setEventTime(Time eventTime) {
		this.eventTime = eventTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
