package com.android.storemanage.entity;

public class AppVersionEntity {
	private int sfNeedUpdate;//0必须更新1可以更新2无需更新
	private String appversionUpdateurl;//APP更新地址
	private String appversionId;
	private String appversionOpptime;
	private String UpdateExplain;
	private String appversionNumber;
	/**
	 * @return the sfNeedUpdate
	 */
	public int getSfNeedUpdate() {
		return sfNeedUpdate;
	}
	/**
	 * @param sfNeedUpdate the sfNeedUpdate to set
	 */
	public void setSfNeedUpdate(int sfNeedUpdate) {
		this.sfNeedUpdate = sfNeedUpdate;
	}
	/**
	 * @return the appversionUpdateurl
	 */
	public String getAppversionUpdateurl() {
		return appversionUpdateurl;
	}
	/**
	 * @param appversionUpdateurl the appversionUpdateurl to set
	 */
	public void setAppversionUpdateurl(String appversionUpdateurl) {
		this.appversionUpdateurl = appversionUpdateurl;
	}
	/**
	 * @return the appversionId
	 */
	public String getAppversionId() {
		return appversionId;
	}
	/**
	 * @param appversionId the appversionId to set
	 */
	public void setAppversionId(String appversionId) {
		this.appversionId = appversionId;
	}
	/**
	 * @return the appversionOpptime
	 */
	public String getAppversionOpptime() {
		return appversionOpptime;
	}
	/**
	 * @param appversionOpptime the appversionOpptime to set
	 */
	public void setAppversionOpptime(String appversionOpptime) {
		this.appversionOpptime = appversionOpptime;
	}
	/**
	 * @return the updateExplain
	 */
	public String getUpdateExplain() {
		return UpdateExplain;
	}
	/**
	 * @param updateExplain the updateExplain to set
	 */
	public void setUpdateExplain(String updateExplain) {
		UpdateExplain = updateExplain;
	}
	/**
	 * @return the appversionNumber
	 */
	public String getAppversionNumber() {
		return appversionNumber;
	}
	/**
	 * @param appversionNumber the appversionNumber to set
	 */
	public void setAppversionNumber(String appversionNumber) {
		this.appversionNumber = appversionNumber;
	}
	public AppVersionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
