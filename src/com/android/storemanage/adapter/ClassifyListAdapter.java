package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.BrandEntity;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassifyListAdapter extends BaseAdapter {
	private Context mContext;
	private List<BrandEntity> brandEntities;

	public ClassifyListAdapter() {
		super();
	}

	public ClassifyListAdapter(Context mContext, List<BrandEntity> brandEntities) {
		super();
		this.mContext = mContext;
		this.brandEntities = brandEntities;
	}

	/**
	 * @param brandEntities the brandEntities to set
	 */
	public void setBrandEntities(List<BrandEntity> brandEntities) {
		this.brandEntities = brandEntities;
	}

	public List<BrandEntity> getBrandEntities() {
		return brandEntities;
	}

	@Override
	public int getCount() {
		return null == brandEntities ? 0 : brandEntities.size();
	}

	@Override
	public Object getItem(int arg0) {
		return brandEntities.get(arg0);
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
			holder.tv_branch_content = (TextView) convertView.findViewById(R.id.tv_branch_content);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		BrandEntity entity = brandEntities.get(position);
		if (null != entity) {
			Picasso.with(mContext).load(entity.getCBrandXtbpath())
					.placeholder(R.drawable.img_empty).into(holder.iv);
			holder.tv_branch_content.setText(entity.getCBrandFbt());

			if (!TextUtils.isEmpty(entity.getCBrandSfnew())) {
				if ("1".equals(entity.getCBrandSfnew())) {
//					holder.ivIsNew.setVisibility(View.VISIBLE);
					if (entity.getDbOpptime() == entity.getCBrandOpptime()) {
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
			holder.tvClickTime.setText("点击量：" + entity.getCBrandClicknumber());
			holder.tvTitle.setText(entity.getCBrandTitle());
			holder.tvWealth.setText("财富值：" + entity.getCBrandWealth());

		}
		return convertView;
	}

	class Holder {
		ImageView iv;
		TextView tvTitle;
		TextView tvClickTime;
		TextView tvWealth;
		TextView tv_branch_content;
		ImageView ivIsNew;
	}

}
