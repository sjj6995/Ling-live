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
	private List<WealthPrizeEntity> wealrhareaPrizeMapList;//财富奖区
	
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
