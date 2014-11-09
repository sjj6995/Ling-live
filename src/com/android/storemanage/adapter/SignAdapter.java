package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.SignCount;
import com.android.storemanage.entity.SignEntity;
import com.android.storemanage.entity.WealthEntity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SignAdapter extends BaseAdapter {
	private Context mContext;
	private SignCount sign;
	private Button btn;
	private int[] icon = new int[] { R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon, R.drawable.icon, R.drawable.icon};
	private String[] wealth = new String[] { "+1", "+2", "+3", "+4", "+5", "+6" };

	public SignAdapter(Context mContext, SignCount sign,Button btn) {
		super();
		this.mContext = mContext;
		this.sign = sign;
		this.btn = btn;
		btn.setVisibility(View.VISIBLE);
	} 

	public SignAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return icon.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
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
			convertView = View.inflate(mContext, R.layout.icon_sign_item, null);
			hodler.tvTitle = (TextView) convertView
					.findViewById(R.id.sign_textView);
			hodler.tvTitle.setText("第"+(position+1)+"天签到");
			convertView.setTag(hodler);
		} else {
			hodler = (Holder) convertView.getTag();
		}
		if (null != sign) {
			int dayCount = sign.getSignCount();
//			int value = Integer.parseInt(dayCount);
			if (position < dayCount) {
				hodler.tvTitle.setBackgroundResource(R.drawable.signed_text_border_xml);
				
			}
			
		}
		return convertView;
	}

	class Holder {
		ImageView ivIsNew;
		TextView tvTitle;
	}

}
