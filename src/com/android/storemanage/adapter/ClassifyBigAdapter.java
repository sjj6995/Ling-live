package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ClassifyBigAdapter extends BaseAdapter {

	private Context context;
	private List<CategoryEntity> entities;
	private int itemWidth;

	public ClassifyBigAdapter(Context context, List<CategoryEntity> entities, int itemWidth) {
		super();
		this.context = context;
		this.entities = entities;
		this.itemWidth = itemWidth;
	}

	public ClassifyBigAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return entities.size() > JFConfig.PAGE_COUNT ? JFConfig.PAGE_COUNT : entities.size();
	}

	@Override
	public Object getItem(int arg0) {
		return entities.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		CategoryEntity entity = entities.get(postion);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.classify_big_item, null);
			holder.ivImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.llLayout = (LinearLayout) convertView.findViewById(R.id.ll_category);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (null != entity) {
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.ivImageView.getLayoutParams();
			params.height = itemWidth;
			params.width = itemWidth;
			holder.ivImageView.setLayoutParams(params);
			Picasso.with(context).load(JFConfig.HOST_URL + entity.getCategoryBigimgpath())
					.placeholder(R.drawable.img_empty).into(holder.ivImageView);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView ivImageView;
		LinearLayout llLayout;
	}

}
