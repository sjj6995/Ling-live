package com.android.storemanage.entity;

/**
 * @author unknow 财富
 * 
 */
public class WealthEntity {
	private long wealthareaOpptime = 0;
	private String wealthareaSfnew;
	private String wealthareaTitle;
	private String wealthareaId;
	private long dbOpptime = 0;

	/**
	 * @return the dbOpptime
	 */
	public long getDbOpptime() {
		return dbOpptime;
	}

	/**
	 * @param dbOpptime
	 *            the dbOpptime to set
	 */
	public void setDbOpptime(long dbOpptime) {
		this.dbOpptime = dbOpptime;
	}

	public long getWealthareaOpptime() {
		return wealthareaOpptime;
	}

	public void setWealthareaOpptime(long wealthareaOpptime) {
		this.wealthareaOpptime = wealthareaOpptime;
	}

	public String getWealthareaSfnew() {
		return wealthareaSfnew;
	}

	public void setWealthareaSfnew(String wealthareaSfnew) {
		this.wealthareaSfnew = wealthareaSfnew;
	}

	public String getWealthareaTitle() {
		return wealthareaTitle;
	}

	public void setWealthareaTitle(String wealthareaTitle) {
		this.wealthareaTitle = wealthareaTitle;
	}

	public String getWealthareaId() {
		return wealthareaId;
	}

	public void setWealthareaId(String wealthareaId) {
		this.wealthareaId = wealthareaId;
	}

	public WealthEntity() {
		super();
	}

}
