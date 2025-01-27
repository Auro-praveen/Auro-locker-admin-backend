package com.auro.beans;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "customer_events")
public class CustomerEvents {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int slno;
	private String customerName;
	private String eventType;
	private Date eventDate;
	private Time eventTime;
	private String lockerNo;
	private String terminalId;
	private String eventTriggeredUser;
	private String remarks;
	private String mobileNo;
	private String state;
	private String city;
	
//	public CustomerEvents() {
//	}

//	public CustomerEvents(int slno, String customerName, String eventType, Date eventDate, Time eventTime,
//			String lockerNo, String terminalId, String eventTriggeredUser, String remarks, String mobileNo,
//			String state, String city) {
//		super();
//		this.slno = slno;
//		this.customerName = customerName;
//		this.eventType = eventType;
//		this.eventDate = eventDate;
//		this.eventTime = eventTime;
//		this.lockerNo = lockerNo;
//		this.terminalId = terminalId;
//		this.eventTriggeredUser = eventTriggeredUser;
//		this.remarks = remarks;
//		this.mobileNo = mobileNo;
//		this.state = state;
//		this.city = city;
//	}

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getLockerNo() {
		return lockerNo;
	}

	public void setLockerNo(String lockerNo) {
		this.lockerNo = lockerNo;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getEventTriggeredUser() {
		return eventTriggeredUser;
	}

	public void setEventTriggeredUser(String eventTriggeredUser) {
		this.eventTriggeredUser = eventTriggeredUser;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
