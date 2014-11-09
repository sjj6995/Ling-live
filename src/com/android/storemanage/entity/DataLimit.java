package com.android.storemanage.entity;

import java.io.Serializable;

public class DataLimit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private int num;
	private String time;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
