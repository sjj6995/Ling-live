package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.UserPrizeEntity;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.SlideView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author unknow 消息详情内容填充
 */
public class UserPrizeAdapter extends BaseAdapter {
	private Context mContext;
	private List<UserPrizeEntity> lists;

	public UserPrizeAdapter() {
		super();
	}

	public UserPrizeAdapter(Context mContext, List<UserPrizeEntity> lists) {
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
			convertView = View.inflate(mContext, R.layout.my_prize_item, null);
			holder = new ViewHolder();
			holder.ivImageView = (ImageView) convertView.findViewById(R.id.iv_has_used_or_not);
			holder.ivIconImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvValidateTimeTextView = (TextView) convertView.findViewById(R.id.tv_validate_time);
			holder.tvNameTextView = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvWealthTextView = (TextView) convertView.findViewById(R.id.tv_wealth_value);
			holder.deleteBtn = (Button) convertView.findViewById(R.id.example_row_b_action_3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		UserPrizeEntity entity = lists.get(position);
		if (null != entity) {
			holder.tvNameTextView.setText(entity.getUserprizeTitle());
			holder.tvValidateTimeTextView.setText("有效期剩余" + entity.getUserprizeOpptime());
			Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getUserprizeImgpath())
					.placeholder(R.drawable.img_empty).into(holder.ivIconImageView);
			String isUsedOrNotString = entity.getUserprizeSfused();
			if (!TextUtils.isEmpty(isUsedOrNotString)) {
				if ("1".equals(isUsedOrNotString)) {
					holder.ivImageView.setImageResource(R.drawable.btn_hasused);
				} else {
					holder.ivImageView.setImageResource(R.drawable.btn_unused);
				}
			} else {
				holder.ivImageView.setImageResource(R.drawable.btn_unused);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView ivImageView;
		ImageView ivIconImageView;
		TextView tvNameTextView;
		TextView tvValidateTimeTextView;
		TextView tvWealthTextView;
		Button deleteBtn;

	}

}
