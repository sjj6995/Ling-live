package com.android.storemanage.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.fragment.FrontGridFragment;
import com.android.storemanage.utils.JFConfig;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FrontFotruneAdapter extends FragmentStatePagerAdapter {

	private ArrayList<CategoryEntity> lists;

	public FrontFotruneAdapter(FragmentManager fm) {
		super(fm);
	}

	public FrontFotruneAdapter(FragmentManager fm,
			ArrayList<CategoryEntity> temp) {
		super(fm);
		this.lists = temp;
	}

	@Override
	public Fragment getItem(int page) {
		FrontGridFragment fragment = new FrontGridFragment();
		Bundle bundle = new Bundle();
		List<CategoryEntity> temp;
		if (page < getCount() - 1) {
			temp = lists.subList(page, (page + 1) * JFConfig.PAGE_COUNT);
		} else {
			temp = lists.subList(page * JFConfig.PAGE_COUNT, lists.size() - 1);
		}
		ArrayList<CategoryEntity> str = new ArrayList<CategoryEntity>();
		str.addAll(temp);
		// bundle.putStringArrayList("text",str);
		bundle.putSerializable("text", str);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return lists.size() % JFConfig.PAGE_COUNT == 0 ? lists.size() / JFConfig.PAGE_COUNT
				: lists.size() / JFConfig.PAGE_COUNT + 1;
	}

}
