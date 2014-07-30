package com.android.storemanage.entity;

import com.android.storemanage.view.SlideView;

/**
 * @author unknow 用户奖品详情
 * 
 */
public class UserPrizeEntity {
	private String userprizeId;
	private String userprizeImgname;
	private String userprizeImgsize;
	private String userid;
	private String userprizeSfused;
	private String userprizeTitle;
	private int userprizeNeedwealth;
	private String userprizeImgpath;
	private String userprizeOpptime;
	private int userprizeExpirydate;
	private String userprizeImgid;
	private String userprizeDetail;
	private String userprizeImgdomain;
	private String userprizeDuihuantime;
	private int  userprizeCategory;
	private int userprizeValidity;

	/**
	 * @return the userprizeValidity
	 */
	public int getUserprizeValidity() {
		return userprizeValidity;
	}

	/**
	 * @param userprizeValidity the userprizeValidity to set
	 */
	public void setUserprizeValidity(int userprizeValidity) {
		this.userprizeValidity = userprizeValidity;
	}

	public int getUserprizeCategory() {
		return userprizeCategory;
	}

	public void setUserprizeCategory(int userprizeCategory) {
		this.userprizeCategory = userprizeCategory;
	}

	public String getUserprizeDuihuantime() {
		return userprizeDuihuantime;
	}

	public void setUserprizeDuihuantime(String userprizeDuihuantime) {
		this.userprizeDuihuantime = userprizeDuihuantime;
	}

	public SlideView slideView;

	public UserPrizeEntity() {
		super();
	}

	public String getUserprizeId() {
		return userprizeId;
	}

	public void setUserprizeId(String userprizeId) {
		this.userprizeId = userprizeId;
	}

	public String getUserprizeImgname() {
		return userprizeImgname;
	}

	public void setUserprizeImgname(String userprizeImgname) {
		this.userprizeImgname = userprizeImgname;
	}

	public String getUserprizeImgsize() {
		return userprizeImgsize;
	}

	public void setUserprizeImgsize(String userprizeImgsize) {
		this.userprizeImgsize = userprizeImgsize;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserprizeSfused() {
		return userprizeSfused;
	}

	public void setUserprizeSfused(String userprizeSfused) {
		this.userprizeSfused = userprizeSfused;
	}

	public String getUserprizeTitle() {
		return userprizeTitle;
	}

	public void setUserprizeTitle(String userprizeTitle) {
		this.userprizeTitle = userprizeTitle;
	}

	public int getUserprizeNeedwealth() {
		return userprizeNeedwealth;
	}

	public void setUserprizeNeedwealth(int userprizeNeedwealth) {
		this.userprizeNeedwealth = userprizeNeedwealth;
	}

	public String getUserprizeImgpath() {
		return userprizeImgpath;
	}

	public void setUserprizeImgpath(String userprizeImgpath) {
		this.userprizeImgpath = userprizeImgpath;
	}

	public String getUserprizeOpptime() {
		return userprizeOpptime;
	}

	public void setUserprizeOpptime(String userprizeOpptime) {
		this.userprizeOpptime = userprizeOpptime;
	}

	public int getUserprizeExpirydate() {
		return userprizeExpirydate;
	}

	public void setUserprizeExpirydate(int userprizeExpirydate) {
		this.userprizeExpirydate = userprizeExpirydate;
	}

	public String getUserprizeImgid() {
		return userprizeImgid;
	}

	public void setUserprizeImgid(String userprizeImgid) {
		this.userprizeImgid = userprizeImgid;
	}

	public String getUserprizeDetail() {
		return userprizeDetail;
	}

	public void setUserprizeDetail(String userprizeDetail) {
		this.userprizeDetail = userprizeDetail;
	}

	public String getUserprizeImgdomain() {
		return userprizeImgdomain;
	}

	public void setUserprizeImgdomain(String userprizeImgdomain) {
		this.userprizeImgdomain = userprizeImgdomain;
	}

}
