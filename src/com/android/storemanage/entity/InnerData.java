package com.android.storemanage.entity;

import java.util.List;


public class InnerData {
	private List<CollectionData> data;
	private String dataName;

	public List<CollectionData> getData() {
		return data;
	}

	public void setData(List<CollectionData> data) {
		this.data = data;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public InnerData() {
		super();
	}

}
