package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.MessageEntity;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	private Context mContext;
	private List<MessageEntity> lists;

	public MessageAdapter() {
		super();
	}

	public MessageAdapter(Context mContext, List<MessageEntity> lists) {
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
			convertView = View.inflate(mContext, R.layout.message_item, null);
			holder = new ViewHolder();
			holder.ivImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvDateTextView = (TextView) convertView.findViewById(R.id.tv_datetime);
			holder.tvMessageDescTextView = (TextView) convertView.findViewById(R.id.tv_message_desc);
			holder.tvMessageNameTextView = (TextView) convertView.findViewById(R.id.tv_message_name);
			holder.ivNewTextView = (ImageView) convertView.findViewById(R.id.iv_new);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MessageEntity entity = lists.get(position);
		if (null != entity) {
			holder.tvDateTextView.setText(entity.getMessagePubdate());
//			try {
//				holder.tvDateTextView.setText(CommonUtil.longToString(Long.parseLong(entity.getMessagePubdate()),
//						"yyyy-MM-dd HH:mm"));
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
			holder.tvMessageNameTextView.setText(entity.getMessageTitle());
			holder.tvMessageDescTextView.setText(entity.getMessageDetail());
			if (!TextUtils.isEmpty(entity.getMessageSfnew()) && "1".equals(entity.getMessageSfnew())) {
				holder.ivNewTextView.setVisibility(View.VISIBLE);
			} else {
				holder.ivNewTextView.setVisibility(View.INVISIBLE);
			}
			Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getMessageImgpath())
					.placeholder(R.drawable.img_empty).into(holder.ivImageView);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView ivImageView;
		TextView tvMessageNameTextView;
		TextView tvDateTextView;
		ImageView ivNewTextView;
		TextView tvMessageDescTextView;

	}

}
