package com.android.storemanage.entity;

public class CommonDataEntity {
	private String returnStatus;
	private String msg;
	private int userwealth;//用户的财富
	private int userAmount;//用户数量
	private int userAddWealthValue;//用户增加的财富值

	public int getUserAddWealthValue() {
		return userAddWealthValue;
	}

	public void setUserAddWealthValue(int userAddWealthValue) {
		this.userAddWealthValue = userAddWealthValue;
	}

	public int getUserwealth() {
		return userwealth;
	}

	public void setUserwealth(int userwealth) {
		this.userwealth = userwealth;
	}

	public int getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(int userAmount) {
		this.userAmount = userAmount;
	}

	public CommonDataEntity(String returnStatus, String msg) {
		super();
		this.returnStatus = returnStatus;
		this.msg = msg;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public CommonDataEntity() {
		super();
	}

}
