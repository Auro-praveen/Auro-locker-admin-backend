package com.opDTO.beans;

import java.util.Date;

public class FollowUpTransactionDetails {

	private int slno;

	private String customerName;

	private String mobileNo;

	private Date date_of_open;

	private Date time_of_open;

	private String terminalid;

	private float no_of_hours;

	private float amount;

	private String status;


	private float excess_hours;

	private float excess_amount;

	private String lockNo;

	private String passcode;

//	private Date dateofbirth;

	private int balance = 0;
	private String itemsStored;
	private String browtype;
	private int partretamount;
	public FollowUpTransactionDetails(int slno, String customerName, String mobileNo, Date date_of_open,
			Date time_of_open, String terminalid, float no_of_hours, float amount, String status, float excess_hours,
			float excess_amount, String lockNo, String passcode, int balance, String itemsStored, String browtype,
			int partretamount) {
		super();
		this.slno = slno;
		this.customerName = customerName;
		this.mobileNo = mobileNo;
		this.date_of_open = date_of_open;
		this.time_of_open = time_of_open;
		this.terminalid = terminalid;
		this.no_of_hours = no_of_hours;
		this.amount = amount;
		this.status = status;
		this.excess_hours = excess_hours;
		this.excess_amount = excess_amount;
		this.lockNo = lockNo;
		this.passcode = passcode;
		this.balance = balance;
		this.itemsStored = itemsStored;
		this.browtype = browtype;
		this.partretamount = partretamount;
	}
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Date getDate_of_open() {
		return date_of_open;
	}
	public void setDate_of_open(Date date_of_open) {
		this.date_of_open = date_of_open;
	}
	public Date getTime_of_open() {
		return time_of_open;
	}
	public void setTime_of_open(Date time_of_open) {
		this.time_of_open = time_of_open;
	}
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	public float getNo_of_hours() {
		return no_of_hours;
	}
	public void setNo_of_hours(float no_of_hours) {
		this.no_of_hours = no_of_hours;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getExcess_hours() {
		return excess_hours;
	}
	public void setExcess_hours(float excess_hours) {
		this.excess_hours = excess_hours;
	}
	public float getExcess_amount() {
		return excess_amount;
	}
	public void setExcess_amount(float excess_amount) {
		this.excess_amount = excess_amount;
	}
	public String getLockNo() {
		return lockNo;
	}
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getItemsStored() {
		return itemsStored;
	}
	public void setItemsStored(String itemsStored) {
		this.itemsStored = itemsStored;
	}
	public String getBrowtype() {
		return browtype;
	}
	public void setBrowtype(String browtype) {
		this.browtype = browtype;
	}
	public int getPartretamount() {
		return partretamount;
	}
	public void setPartretamount(int partretamount) {
		this.partretamount = partretamount;
	}
	

}
