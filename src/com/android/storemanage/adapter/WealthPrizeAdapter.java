package com.android.storemanage.adapter;

import java.text.ParseException;
import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.MessageDetailEntity;
import com.android.storemanage.entity.MessageEntity;
import com.android.storemanage.entity.WealthPrizeEntity;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author unknow 消息详情内容填充
 */
public class WealthPrizeAdapter extends BaseAdapter {
	private Context mContext;
	private List<WealthPrizeEntity> lists;

	public WealthPrizeAdapter() {
		super();
	}

	public WealthPrizeAdapter(Context mContext, List<WealthPrizeEntity> lists) {
		super();
		this.mContext = mContext;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return null != lists ? lists.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = View.inflate(mContext, R.layout.wealth_prize_item, null);
			holder = new ViewHolder();
			holder.ivImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvFromTimeTextView = (TextView) convertView.findViewById(R.id.tv_from_time);
			holder.tvWealthTextView = (TextView) convertView.findViewById(R.id.tv_need_fortune);
			holder.totalCounTextView = (TextView) convertView.findViewById(R.id.tv_total);
			holder.leftCountTextView = (TextView) convertView.findViewById(R.id.tv_left);
			holder.tvToTimeTextView = (TextView) convertView.findViewById(R.id.tv_to_time);
			holder.tvTitleTextView = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		WealthPrizeEntity entity = lists.get(position);
		if (null != entity) {
			try {
				holder.tvFromTimeTextView.setText(CommonUtil.longToString(entity.getWPrizeStarttime(), "yyyy年MM月dd日"));
				holder.tvToTimeTextView.setText(CommonUtil.longToString(entity.getWPrizeEndtime(), "yyyy年MM月dd日"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			holder.leftCountTextView.setText(entity.getWPrizeRemainnumber() + "个");
			holder.totalCounTextView.setText(entity.getWPrizeTotalnumber() + "个");
			holder.tvWealthTextView.setText(entity.getWPrizeNeedwealth() + "");
			Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getWPrizeImgpath())
					.placeholder(R.drawable.img_empty).into(holder.ivImageView);
			holder.tvTitleTextView.setText(entity.getWPrizeTitle());
		}
		return convertView;
	}

	class ViewHolder {
		ImageView ivImageView;
		TextView tvTitleTextView;
		TextView totalCounTextView;
		TextView leftCountTextView;
		TextView tvWealthTextView;
		TextView tvFromTimeTextView;
		TextView tvToTimeTextView;

	}

}
