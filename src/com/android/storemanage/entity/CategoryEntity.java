package com.android.storemanage.entity;

import java.io.Serializable;

public class CategoryEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryBigimgpath;
	private String categoryBigimgid;
	private String categoryTitle;
	private String categoryBigimgname;
	private String categoryId;
	private String categoryLittleimgpath;
	private String categoryImgdomain;
	private String categoryBigimgsize;
	private String categoryLittleimgname;
	private String categoryLittleimgsize;
	private String categoryLittleimgid;
	private String categoryOpptime;
	private String categorySfnew;
	private long dbOpptime = 0;
	public String getCategorySfnew() {
		return categorySfnew;
	}

	public long getDbOpptime() {
		return dbOpptime;
	}

	public void setDbOpptime(long dbOpptime) {
		this.dbOpptime = dbOpptime;
	}

	public void setCategorySfnew(String categorySfnew) {
		this.categorySfnew = categorySfnew;
	}

	public String getCategoryBigimgpath() {
		return categoryBigimgpath;
	}

	public void setCategoryBigimgpath(String categoryBigimgpath) {
		this.categoryBigimgpath = categoryBigimgpath;
	}

	public String getCategoryBigimgid() {
		return categoryBigimgid;
	}

	public void setCategoryBigimgid(String categoryBigimgid) {
		this.categoryBigimgid = categoryBigimgid;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryBigimgname() {
		return categoryBigimgname;
	}

	public void setCategoryBigimgname(String categoryBigimgname) {
		this.categoryBigimgname = categoryBigimgname;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryLittleimgpath() {
		return categoryLittleimgpath;
	}

	public void setCategoryLittleimgpath(String categoryLittleimgpath) {
		this.categoryLittleimgpath = categoryLittleimgpath;
	}

	public String getCategoryImgdomain() {
		return categoryImgdomain;
	}

	public void setCategoryImgdomain(String categoryImgdomain) {
		this.categoryImgdomain = categoryImgdomain;
	}

	public String getCategoryBigimgsize() {
		return categoryBigimgsize;
	}

	public void setCategoryBigimgsize(String categoryBigimgsize) {
		this.categoryBigimgsize = categoryBigimgsize;
	}

	public String getCategoryLittleimgname() {
		return categoryLittleimgname;
	}

	public void setCategoryLittleimgname(String categoryLittleimgname) {
		this.categoryLittleimgname = categoryLittleimgname;
	}

	public String getCategoryLittleimgsize() {
		return categoryLittleimgsize;
	}

	public void setCategoryLittleimgsize(String categoryLittleimgsize) {
		this.categoryLittleimgsize = categoryLittleimgsize;
	}

	public String getCategoryLittleimgid() {
		return categoryLittleimgid;
	}

	public void setCategoryLittleimgid(String categoryLittleimgid) {
		this.categoryLittleimgid = categoryLittleimgid;
	}

	public String getCategoryOpptime() {
		return categoryOpptime;
	}

	public void setCategoryOpptime(String categoryOpptime) {
		this.categoryOpptime = categoryOpptime;
	}

	public CategoryEntity() {
		super();
	}

}
