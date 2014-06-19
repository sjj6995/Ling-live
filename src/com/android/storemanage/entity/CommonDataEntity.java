package com.android.storemanage.entity;

public class CommonDataEntity {
	private String returnStatus;
	private String msg;
	private int userwealth;// 用户的财富
	private int userAmount;// 用户数量
	private int userAddWealthValue;// 用户增加的财富值
	private int DuiHuanUserWealth;// 兑换用户财富值
	private String DuiHuanSuccess;// 兑换是否成功
	private String useSuccess;// 使用是否成功
	private String reason;// 使用失败的原因
	private String feedbackSuccess;//是否意见反馈成功
	private String registered;//true表示已注册，false为未注册
	
	
	

	/**
	 * @return the registered
	 */
	public String getRegistered() {
		return registered;
	}

	/**
	 * @param registered the registered to set
	 */
	public void setRegistered(String registered) {
		this.registered = registered;
	}

	/**
	 * @return the feedbackSuccess
	 */
	public String getFeedbackSuccess() {
		return feedbackSuccess;
	}

	/**
	 * @param feedbackSuccess the feedbackSuccess to set
	 */
	public void setFeedbackSuccess(String feedbackSuccess) {
		this.feedbackSuccess = feedbackSuccess;
	}

	public String getUseSuccess() {
		return useSuccess;
	}

	public void setUseSuccess(String useSuccess) {
		this.useSuccess = useSuccess;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getDuiHuanUserWealth() {
		return DuiHuanUserWealth;
	}

	public void setDuiHuanUserWealth(int duiHuanUserWealth) {
		DuiHuanUserWealth = duiHuanUserWealth;
	}

	public String getDuiHuanSuccess() {
		return DuiHuanSuccess;
	}

	public void setDuiHuanSuccess(String duiHuanSuccess) {
		DuiHuanSuccess = duiHuanSuccess;
	}

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
