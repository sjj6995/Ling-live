package com.android.storemanage.entity;

import java.util.ArrayList;
import java.util.List;

public class CollectionData {
	private CommonDataEntity commonData;
	private List<MessageEntity> messageMapList;// 消息中心集合
	private List<MessageDetailEntity> messageDetailMapList;// 消息详情集合
	private ArrayList<CategoryEntity> categoryMapList;// 分类集合
	private UserInforEntity userInfoMap;//用户信息
	private List<WealthEntity> wealthareaMapList;//财富专区
	private List<WealthPrizeEntity> wealrhareaPrizeMapList;//财富奖区列表
	private List<BrandEntity> brandMapList;//分类详情
	private List<UserPrizeEntity> userPrizeMapList;//用户奖品集合
	private WealthPrizeEntity prizeDetailMap;//财富奖区详情
	private AppVersionEntity appVersionData;//版本信息
	private UserPrizeDetailEntity userprizeDetailMap;//用户奖品详情
	
	public UserPrizeDetailEntity getUserprizeDetailMap() {
		return userprizeDetailMap;
	}

	public void setUserprizeDetailMap(UserPrizeDetailEntity userprizeDetailMap) {
		this.userprizeDetailMap = userprizeDetailMap;
	}

	/**
	 * @return the appVersionData
	 */
	public AppVersionEntity getAppVersionData() {
		return appVersionData;
	}

	/**
	 * @param appVersionData the appVersionData to set
	 */
	public void setAppVersionData(AppVersionEntity appVersionData) {
		this.appVersionData = appVersionData;
	}

	public WealthPrizeEntity getPrizeDetailMap() {
		return prizeDetailMap;
	}

	public void setPrizeDetailMap(WealthPrizeEntity prizeDetailMap) {
		this.prizeDetailMap = prizeDetailMap;
	}

	public List<UserPrizeEntity> getUserPrizeMapList() {
		return userPrizeMapList;
	}

	public void setUserPrizeMapList(List<UserPrizeEntity> userPrizeMapList) {
		this.userPrizeMapList = userPrizeMapList;
	}

	public List<BrandEntity> getBrandMapList() {
		return brandMapList;
	}

	public void setBrandMapList(List<BrandEntity> brandMapList) {
		this.brandMapList = brandMapList;
	}

	public List<WealthPrizeEntity> getWealrhareaPrizeMapList() {
		return wealrhareaPrizeMapList;
	}

	public void setWealrhareaPrizeMapList(List<WealthPrizeEntity> wealrhareaPrizeMapList) {
		this.wealrhareaPrizeMapList = wealrhareaPrizeMapList;
	}

	public List<WealthEntity> getWealthareaMapList() {
		return wealthareaMapList;
	}

	public void setWealthareaMapList(List<WealthEntity> wealthareaMapList) {
		this.wealthareaMapList = wealthareaMapList;
	}

	public UserInforEntity getUserInfoMap() {
		return userInfoMap;
	}

	public void setUserInfoMap(UserInforEntity userInfoMap) {
		this.userInfoMap = userInfoMap;
	}

	public ArrayList<CategoryEntity> getCategoryMapList() {
		return categoryMapList;
	}

	public void setCategoryMapList(ArrayList<CategoryEntity> categoryMapList) {
		this.categoryMapList = categoryMapList;
	}

	public List<MessageDetailEntity> getMessageDetailMapList() {
		return messageDetailMapList;
	}

	public void setMessageDetailMapList(
			List<MessageDetailEntity> messageDetailMapList) {
		this.messageDetailMapList = messageDetailMapList;
	}

	public List<MessageEntity> getMessageMapList() {
		return messageMapList;
	}

	public void setMessageMapList(List<MessageEntity> messageMapList) {
		this.messageMapList = messageMapList;
	}

	public CommonDataEntity getCommonData() {
		return commonData;
	}

	public void setCommonData(CommonDataEntity commonData) {
		this.commonData = commonData;
	}

}
