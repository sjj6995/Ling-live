package com.android.storemanage.adapter;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.application.BaseApplication;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserPrizeEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
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
	private BaseApplication application;
	private CommonLog log = CommonLog.getInstance();

	public UserPrizeAdapter() {
		super();
	}

	public UserPrizeAdapter(Context mContext, List<UserPrizeEntity> lists) {
		super();
		this.mContext = mContext;
		this.lists = lists;
		application = BaseApplication.getApplication();
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
			convertView = View.inflate(mContext, R.layout.package_row, null);
			holder = new ViewHolder();
			holder.ivImageView = (ImageView) convertView
					.findViewById(R.id.iv_has_used_or_not);
			holder.ivIconImageView = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			holder.tvValidateTimeTextView = (TextView) convertView
					.findViewById(R.id.tv_validate_time);
			holder.tvNameTextView = (TextView) convertView
					.findViewById(R.id.tv_name);
			holder.tvWealthTextView = (TextView) convertView
					.findViewById(R.id.tv_wealth_value);
			holder.deleteBtn = (Button) convertView
					.findViewById(R.id.example_row_b_action_3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final UserPrizeEntity entity = lists.get(position);
		if (null != entity) {
			holder.tvNameTextView.setText(entity.getUserprizeTitle());
			holder.tvValidateTimeTextView.setText("有效期剩余"
					+ entity.getUserprizeOpptime());
			Picasso.with(mContext)
					.load(JFConfig.HOST_URL + entity.getUserprizeImgpath())
					.placeholder(R.drawable.img_empty)
					.into(holder.ivIconImageView);
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
			holder.deleteBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					initData(entity.getUserprizeId());
				}
			});
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

	private void initData(String prizeId) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			params.put("userprizeId", prizeId);
			// showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.GET_MY_PRIZE, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							// dismissProgressDialog();
							if (TextUtils.isEmpty(content)) {
								return;
							}
							OuterData outerData = JSON.parseObject(content,
									OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData()
									.get(0);
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {

							} else {
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								dialog.show(
										commonData.getCommonData().getMsg(),
										2000);
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							// dismissProgressDialog();
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(mContext.getString(R.string.pLease_check_network), 2000);
		}

	}

}
