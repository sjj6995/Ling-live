package com.android.storemanage.entity;

/**
 * @author liujiao
 * 用户信息
 */
public class UserInforEntity {
	private String phonemumber;// 手机号码
	private String userwealth;// 用户财富
	private String regtime;// 注册时间
	private String usermail;// 邮箱
	private String userid;// 用户的id
	private String phoneimei;// 手机串号
	private String opptime;

	public String getPhonemumber() {
		return phonemumber;
	}

	public void setPhonemumber(String phonemumber) {
		this.phonemumber = phonemumber;
	}

	public String getUserwealth() {
		return userwealth;
	}

	public void setUserwealth(String userwealth) {
		this.userwealth = userwealth;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPhoneimei() {
		return phoneimei;
	}

	public void setPhoneimei(String phoneimei) {
		this.phoneimei = phoneimei;
	}

	public String getOpptime() {
		return opptime;
	}

	public void setOpptime(String opptime) {
		this.opptime = opptime;
	}

	public UserInforEntity() {
		super();
	}

}
