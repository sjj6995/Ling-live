package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.MessageDetailEntity;
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

/**
 * @author unknow 消息详情内容填充
 */
public class MessageDetailAdapter extends BaseAdapter {
	private Context mContext;
	private List<MessageDetailEntity> lists;

	public MessageDetailAdapter() {
		super();
	}

	public MessageDetailAdapter(Context mContext,
			List<MessageDetailEntity> lists) {
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
			convertView = View.inflate(mContext, R.layout.message_detail_item,
					null);
			holder = new ViewHolder();
			holder.ivImageView = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			holder.tvFromTimeTextView = (TextView) convertView
					.findViewById(R.id.tv_from_time);
			holder.tvMessageDescTextView = (TextView) convertView
					.findViewById(R.id.tv_message_desc);
			holder.tvMessageNameTextView = (TextView) convertView
					.findViewById(R.id.tv_message_name);
			holder.tvToTimeTextView = (TextView) convertView
					.findViewById(R.id.tv_to_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MessageDetailEntity entity = lists.get(position);
		if (null != entity) {
			holder.tvFromTimeTextView
					.setText("起：" + entity.getMDetailPubdate());
			holder.tvToTimeTextView.setText("止：" + entity.getMDetailOpptime());
			holder.tvMessageNameTextView.setText(entity.getMDetailTitle());
			String detail = entity.getMDetailDetail();
			if (TextUtils.isEmpty(detail)) {
				holder.tvMessageDescTextView.setVisibility(View.GONE);
			} else {
				holder.tvMessageDescTextView.setText(detail);
				holder.tvMessageDescTextView.setVisibility(View.VISIBLE);
			}
			// holder.tvNewTextView.setText(entity.getMessageSfnew());
			Picasso.with(mContext)
					.load(JFConfig.HOST_URL + entity.getMDetailImgpath())
					.placeholder(R.drawable.img_empty).into(holder.ivImageView);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView ivImageView;
		TextView tvMessageNameTextView;
		TextView tvFromTimeTextView;
		TextView tvToTimeTextView;
		TextView tvMessageDescTextView;

	}

}
