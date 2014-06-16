package com.android.storemanage.entity;

import java.util.List;

public class OuterData {
	private List<InnerData> data;

	public OuterData() {
		super();
	}

	public List<InnerData> getData() {
		return data;
	}

	public void setData(List<InnerData> data) {
		this.data = data;
	}
	
}
