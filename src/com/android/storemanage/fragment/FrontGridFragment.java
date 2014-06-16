package com.android.storemanage.fragment;

import java.util.ArrayList;

import com.android.storemanage.R;
import com.android.storemanage.activity.CategoryDetailActivity;
import com.android.storemanage.adapter.CategoryAdapter;
import com.android.storemanage.entity.CategoryEntity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FrontGridFragment extends Fragment implements OnItemClickListener {
	private static final String ARG_TEXT = "text";
	private static final String ARG_PAGER = "pager";

	public static FrontGridFragment newInstance(ArrayList<String> text,
			int pager) {
		FrontGridFragment f = new FrontGridFragment();

		Bundle args = new Bundle();
		args.putStringArrayList(ARG_TEXT, text);
		args.putInt(ARG_PAGER, pager);
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.viewpager_item, container, false);
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		ArrayList<CategoryEntity> list = (ArrayList<CategoryEntity>) getArguments()
				.getSerializable(ARG_TEXT);
		gridview.setAdapter(new CategoryAdapter(getActivity(), list));
		gridview.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		CategoryEntity entity = (CategoryEntity) arg0.getItemAtPosition(arg2);
		if (null != entity) {
			Intent intent = new Intent(getActivity(),
					CategoryDetailActivity.class);
			intent.putExtra("categoryId", entity.getCategoryId());
			intent.putExtra("categoryName", entity.getCategoryTitle());
			startActivity(intent);
		}
	}

}
