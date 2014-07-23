package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.WealthEntity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WealthAdapter extends BaseAdapter {
	private Context mContext;
	private List<WealthEntity> wealthEntities;
	private int[] colors = new int[] { R.color.color_3, R.color.color_1, R.color.color_2, R.color.color_3,
			R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7 };

	public WealthAdapter(Context mContext, List<WealthEntity> wealthEntities) {
		super();
		this.mContext = mContext;
		this.wealthEntities = wealthEntities;
	}

	public WealthAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return wealthEntities == null ? 0 : wealthEntities.size() > 8 ? 8 : wealthEntities.size();
	}

	@Override
	public Object getItem(int arg0) {
		return wealthEntities.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder hodler = null;
		if (null == convertView) {
			hodler = new Holder();
			convertView = View.inflate(mContext, R.layout.icon_menu_item, null);
			hodler.ivIsNew = (ImageView) convertView.findViewById(R.id.iv_is_new);
			hodler.tvTitle = (TextView) convertView.findViewById(R.id.description_textView);
			convertView.setTag(hodler);
		} else {
			hodler = (Holder) convertView.getTag();
		}
		WealthEntity entity = wealthEntities.get(position);
		if (null != entity) {
			String isNew = entity.getWealthareaSfnew();
			if (!TextUtils.isEmpty(isNew)) {
				if ("1".equals(isNew)) {
					if (entity.getDbOpptime() == entity.getWealthareaOpptime()) {
						hodler.ivIsNew.setVisibility(View.INVISIBLE);
					} else {
						hodler.ivIsNew.setVisibility(View.VISIBLE);
					}
				} else {
					hodler.ivIsNew.setVisibility(View.INVISIBLE);
				}
			} else {
				hodler.ivIsNew.setVisibility(View.INVISIBLE);
			}
			hodler.tvTitle.setText(entity.getWealthareaTitle());
			hodler.tvTitle.setTextColor(mContext.getResources().getColor(colors[position]));
		}
		return convertView;
	}

	class Holder {
		ImageView ivIsNew;
		TextView tvTitle;
	}

}
