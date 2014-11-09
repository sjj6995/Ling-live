package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.BrandEntity;
import com.android.storemanage.entity.ZhaoPin;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhaoPinListAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZhaoPin> zhaopinEntities;

	public ZhaoPinListAdapter() {
		super();
	}

	public ZhaoPinListAdapter(Context mContext, List<ZhaoPin> zhaopinEntities) {
		super();
		this.mContext = mContext;
		this.zhaopinEntities = zhaopinEntities;
	}

	/**
	 * @param brandEntities the brandEntities to set
	 */


	@Override
	public int getCount() {
		return null == zhaopinEntities ? 0 : zhaopinEntities.size();
	}

	public List<ZhaoPin> getZhaopinEntities() {
		return zhaopinEntities;
	}

	public void setZhaopinEntities(List<ZhaoPin> zhaopinEntities) {
		this.zhaopinEntities = zhaopinEntities;
	}

	@Override
	public Object getItem(int arg0) {
		return zhaopinEntities.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder holder = null;
		if (null == convertView) {
			convertView = View.inflate(mContext, R.layout.classify_listview_item, null);
			holder = new Holder();
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_branch_name);
			holder.tvClickTime = (TextView) convertView.findViewById(R.id.tv_click_times);
			holder.tvWealth = (TextView) convertView.findViewById(R.id.tv_fortune_price);
			holder.ivIsNew = (ImageView) convertView.findViewById(R.id.iv_is_new);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ZhaoPin entity = zhaopinEntities.get(position);
		if (null != entity) {
			Picasso.with(mContext).load(entity.getZpLogo())
					.placeholder(R.drawable.img_empty).into(holder.iv);

			if (!TextUtils.isEmpty(entity.getZpSfnew())) {
				if ("1".equals(entity.getZpSfnew())) {
//					holder.ivIsNew.setVisibility(View.VISIBLE);
					if (entity.getDbTime() == Long.parseLong(entity.getZpOpptime())) {
						holder.ivIsNew.setVisibility(View.INVISIBLE);
					} else {
						holder.ivIsNew.setVisibility(View.VISIBLE);
					}
				} else {
					holder.ivIsNew.setVisibility(View.INVISIBLE);
				}
			} else {
				holder.ivIsNew.setVisibility(View.INVISIBLE);
			}
//			holder.tvClickTime.setText("点击量：" + entity.getCBrandClicknumber());
			holder.tvTitle.setText(entity.getZpTitle());
//			holder.tvWealth.setText("财富值：" + entity.getCBrandWealth());

		}
		return convertView;
	}

	class Holder {
		ImageView iv;
		TextView tvTitle;
		TextView tvClickTime;
		TextView tvWealth;
		ImageView ivIsNew;
	}

}
