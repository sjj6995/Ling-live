package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.entity.WealthEntity;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {

	private Context context;
	private List<CategoryEntity> entities;
	private int[] colors = new int[] { R.color.gridview_bg_0,
			R.color.gridview_bg_1, R.color.gridview_bg_2,
			R.color.gridview_bg_3, R.color.gridview_bg_4,
			R.color.gridview_bg_5, R.color.gridview_bg_6, R.color.gridview_bg_7 };

	public CategoryAdapter(Context context, List<CategoryEntity> entities) {
		super();
		this.context = context;
		this.entities = entities;
	}

	public CategoryAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return entities.size() > JFConfig.PAGE_COUNT ? JFConfig.PAGE_COUNT : entities
				.size();
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
			convertView = View.inflate(context, R.layout.category_item, null);
			holder.ivImageView = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			holder.tView = (TextView) convertView.findViewById(R.id.text);
			holder.llLayout = (LinearLayout) convertView
					.findViewById(R.id.ll_category);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (null != entity) {
			holder.tView.setText(entity.getCategoryTitle());
			Picasso.with(context)
					.load(JFConfig.HOST_URL + entity.getCategoryBigimgpath())
					.placeholder(R.drawable.img_empty).into(holder.ivImageView);
			holder.llLayout.setBackgroundResource(colors[postion]);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView ivImageView;
		TextView tView;
		LinearLayout llLayout;
	}

}
