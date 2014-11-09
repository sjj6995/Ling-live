package com.android.storemanage.entity;

/**
 * @author liujiao
 * 用户信息
 */
public class UserInforEntity {
	private String phonenumber;// 手机号码
	private String userwealth;// 用户财富
	private String regtime;// 注册时间
	private String useremail;// 邮箱
	private String userid;// 用户的id
	private String phoneimei;// 手机串号
	private String opptime;
	private String minDay;

	public String getMinDay() {
		return minDay;
	}

	public void setMinDay(String minDay) {
		this.minDay = minDay;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
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
