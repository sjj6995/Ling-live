package com.android.storemanage.entity;

import android.R.integer;

public class CommonDataEntity {
	private String code;//登陆注册 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String returnStatus;
	private String msg;
	private String pwd;// 用户密码by likai
	private String userExist;
	private String emailSent;
	private int userwealth;// 用户的财富
	private int userAmount;// 用户数量
	private int userAddWealthValue;// 用户增加的财富值
	private int DuiHuanUserWealth;// 兑换用户财富值
	private String DuiHuanSuccess;// 兑换是否成功
	private String useSuccess;// 使用是否成功
	private String reason;// 使用失败的原因
	private String feedbackSuccess;// 是否意见反馈成功
	private String registered;// true表示已注册，false为未注册
	private String deleteSuccess;// 是否删除成功
	private String registerState;// 是否需要注册 by likai
	private String loginState;// 登陆是否成功 by likai
	private String deviceState;// 设备状态 by likai
	private String passwordState;// 更改设备 密码验证 by lk
	private String deviceChanged;// 设备更换 by lk
	private String passwordChanged;// 密码更换 by lk
	private String deviceSfkChange;// 设备是否可以更新 by lk
	private int signCount;// 返回签到天数
	private String todayHaveSigned;// 今天是否签到
	private String signSuccess;
	private String wealthValueAdd;
	/**
	 * 二维码码接口
	 */
	private String qrcodeExist;// 二维码是否存在
	private String sfScan;// 是否扫描
	private String sfEnough;// 财富值是否充足
	private String addSuccess;// 增加是否成功
	private String sfHavePassword;// 是否有密码
	private int addWealth;// 增加财富值
	private String passwordSfRight;// 密碼是否正確
	// 第二版 增加财富值
	private int addWealthValue;
	// 下单状态
	private String status;
	// 所有品牌点击次数
	private String clicks;
	private String addWealthSuccess;

	public String getAddWealthSuccess() {
		return addWealthSuccess;
	}

	public void setAddWealthSuccess(String addWealthSuccess) {
		this.addWealthSuccess = addWealthSuccess;
	}

	

	public String getClicks() {
		return clicks;
	}

	public void setClicks(String clicks) {
		this.clicks = clicks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAddWealthValue() {
		return addWealthValue;
	}

	public void setAddWealthValue(int addWealthValue) {
		this.addWealthValue = addWealthValue;
	}

	public String getPasswordSfRight() {
		return passwordSfRight;
	}

	public void setPasswordSfRight(String passwordSfRight) {
		this.passwordSfRight = passwordSfRight;
	}

	public int getAddWealth() {
		return addWealth;
	}

	public void setAddWealth(int addWealth) {
		this.addWealth = addWealth;
	}

	public String getQrcodeExist() {
		return qrcodeExist;
	}

	public void setQrcodeExist(String qrcodeExist) {
		this.qrcodeExist = qrcodeExist;
	}

	public String getSfScan() {
		return sfScan;
	}

	public void setSfScan(String sfScan) {
		this.sfScan = sfScan;
	}

	public String getSfEnough() {
		return sfEnough;
	}

	public void setSfEnough(String sfEnough) {
		this.sfEnough = sfEnough;
	}

	public String getAddSuccess() {
		return addSuccess;
	}

	public void setAddSuccess(String addSuccess) {
		this.addSuccess = addSuccess;
	}

	public String getSfHavePassword() {
		return sfHavePassword;
	}

	public void setSfHavePassword(String sfHavePassword) {
		this.sfHavePassword = sfHavePassword;
	}

	public String getSignSuccess() {
		return signSuccess;
	}

	public void setSignSuccess(String signSuccess) {
		this.signSuccess = signSuccess;
	}

	public String getWealthValueAdd() {
		return wealthValueAdd;
	}

	public void setWealthValueAdd(String wealthValueAdd) {
		this.wealthValueAdd = wealthValueAdd;
	}

	public String getTodayHaveSigned() {
		return todayHaveSigned;
	}

	public void setTodayHaveSigned(String todayHaveSigned) {
		this.todayHaveSigned = todayHaveSigned;
	}

	public int getSignCount() {
		return signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}

	public String getDeviceSfkChange() {
		return deviceSfkChange;
	}

	public void setDeviceSfkChange(String deviceSfkChange) {
		this.deviceSfkChange = deviceSfkChange;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public String getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(String passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}

	public String getPasswordState() {
		return passwordState;
	}

	public void setPasswordState(String passwordState) {
		this.passwordState = passwordState;
	}

	public String getDeviceChanged() {
		return deviceChanged;
	}

	public void setDeviceChanged(String deviceChanged) {
		this.deviceChanged = deviceChanged;
	}

	private String registerSuccess;// true表示注册成功，false为失败，emailRegisted为邮箱重复，注册失败
	private int totalPage;// listview的总共页数
	private int pageNum;// 当前页码

	public String getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(String emailSent) {
		this.emailSent = emailSent;
	}

	public String getUserExist() {
		return userExist;
	}

	public void setUserExist(String userExist) {
		this.userExist = userExist;
	}

	public String getRegisterState() {
		return registerState;
	}

	public void setRegisterState(String registerState) {
		this.registerState = registerState;
	}

	public String getLoginState() {
		return loginState;
	}

	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getRegisterSuccess() {
		return registerSuccess;
	}

	public void setRegisterSuccess(String registerSuccess) {
		this.registerSuccess = registerSuccess;
	}

	public String getDeleteSuccess() {
		return deleteSuccess;
	}

	public void setDeleteSuccess(String deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
	}

	private String userId;// 用户的id

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the registered
	 */
	public String getRegistered() {
		return registered;
	}

	/**
	 * @param registered
	 *            the registered to set
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
	 * @param feedbackSuccess
	 *            the feedbackSuccess to set
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
