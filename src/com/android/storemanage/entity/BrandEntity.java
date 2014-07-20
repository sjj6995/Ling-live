package com.android.storemanage.entity;

public class BrandEntity {
	private String cBrandImgid;
	private String categoryId;
	private int cBrandClicknumber = 0;
	private String cBrandSfnew;
	private String cBrandImgsize;
	private String cBrandId;
	private long cBrandOpptime;
	private String cBrandImgdomain;
	private String cBrandImgpath;//
	private String cBrangSite;
	private String cBrandTitle;
	private String cBrandImgname;
	private int cBrandWealth;
	private String cBrandSfhavedetail;
	
	
	
	/**
	 * "cBrandImgpath": "pages/FileUpload/images/categoryBrand/1401652733279_744068479816370045.jpg",
                            "cBrandSfhavedetail": "0",
                            "cBrangSite": "http://www.dayalihome.com",
                            "cBrandSfnew": "1",
                            "cBrandClicknumber": 25,
                            "categoryId": "4bf5bfb7b226496598d709e1d03a231a",
                            "cBrandTitle": "大鸭梨",
                            "cBrandId": "48c116d1ce104707b8f2bf892b2d65b0",
                            "cBrandWealth": 6
	 * @return
	 */
	
	
	public String getCBrandImgid() {
		return cBrandImgid;
	}
	public String getCBrandSfhavedetail() {
		return cBrandSfhavedetail;
	}
	public void setCBrandSfhavedetail(String cBrandSfhavedetail) {
		this.cBrandSfhavedetail = cBrandSfhavedetail;
	}
	public void setCBrandImgid(String cBrandImgid) {
		this.cBrandImgid = cBrandImgid;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public int getCBrandClicknumber() {
		return cBrandClicknumber;
	}
	public void setCBrandClicknumber(int cBrandClicknumber) {
		this.cBrandClicknumber = cBrandClicknumber;
	}
	public String getCBrandSfnew() {
		return cBrandSfnew;
	}
	public void setCBrandSfnew(String cBrandSfnew) {
		this.cBrandSfnew = cBrandSfnew;
	}
	public String getCBrandImgsize() {
		return cBrandImgsize;
	}
	public void setCBrandImgsize(String cBrandImgsize) {
		this.cBrandImgsize = cBrandImgsize;
	}
	public String getCBrandId() {
		return cBrandId;
	}
	public void setCBrandId(String cBrandId) {
		this.cBrandId = cBrandId;
	}
	public long getCBrandOpptime() {
		return cBrandOpptime;
	}
	public void setCBrandOpptime(long cBrandOpptime) {
		this.cBrandOpptime = cBrandOpptime;
	}
	public String getCBrandImgdomain() {
		return cBrandImgdomain;
	}
	public void setCBrandImgdomain(String cBrandImgdomain) {
		this.cBrandImgdomain = cBrandImgdomain;
	}
	public String getCBrandImgpath() {
		return cBrandImgpath;
	}
	public void setCBrandImgpath(String cBrandImgpath) {
		this.cBrandImgpath = cBrandImgpath;
	}
	public String getCBrangSite() {
		return cBrangSite;
	}
	public void setCBrangSite(String cBrangSite) {
		this.cBrangSite = cBrangSite;
	}
	public String getCBrandTitle() {
		return cBrandTitle;
	}
	public void setCBrandTitle(String cBrandTitle) {
		this.cBrandTitle = cBrandTitle;
	}
	public String getCBrandImgname() {
		return cBrandImgname;
	}
	public void setCBrandImgname(String cBrandImgname) {
		this.cBrandImgname = cBrandImgname;
	}
	public int getCBrandWealth() {
		return cBrandWealth;
	}
	public void setCBrandWealth(int cBrandWealth) {
		this.cBrandWealth = cBrandWealth;
	}
	public BrandEntity() {
		super();
	}
}
