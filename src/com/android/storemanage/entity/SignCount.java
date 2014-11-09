package com.android.storemanage.entity;

public class SignCount {
	private int signCount;
	private String returnStatus;
	private String todayHaveSigned;
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
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
}
