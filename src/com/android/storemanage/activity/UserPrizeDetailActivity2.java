package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserPrizeDetailEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author unknow 奖品使用详情
 * 
 */
public class UserPrizeDetailActivity2 extends BaseActivity {
	private TextView tvTitleTextView2;
	private String userPrizeId;
	private TextView tvTitleTextView;
	private ImageView ivImageView;
	private UserPrizeDetailEntity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_prize_detail);
		tvTitleTextView2 = (TextView) findViewById(R.id.tv_title);
		tvTitleTextView2.setText("奖品详情");
		userPrizeId = getIntent().getStringExtra("userprizeId");
		if (TextUtils.isEmpty(userPrizeId)) {
			Toast.makeText(this, R.string.server_data_exception, Toast.LENGTH_SHORT).show();
			finish();
		} else {
			initViews();
			initData(userPrizeId);
		}

	}

	private void initViews() {
		tvTitleTextView = (TextView) findViewById(R.id.tv_chongzhi);
		ivImageView = (ImageView) findViewById(R.id.iv_icon);
	}

	private void initData(String userPrizeId2) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			params.put("userprizeId", userPrizeId2);
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.GET_PRIZE_DETAIL, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, String content) {
					log.i("content===" + content);
					dismissProgressDialog();
					if (TextUtils.isEmpty(content)) {
						return;
					}
					OuterData outerData = JSON.parseObject(content, OuterData.class);
					InnerData innderData = outerData.getData().get(0);
					CollectionData commonData = innderData.getData().get(0);
					log.i("commonData" + commonData.getCommonData().getMsg());
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						entity = commonData.getUserprizeDetailMap();
						if (null != entity) {
							fillData(entity);
						}
					} else {
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show(commonData.getCommonData().getMsg(), 2000);
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					CommonUtil.onFailure(arg0, mContext);
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	protected void fillData(UserPrizeDetailEntity entity) {
		tvTitleTextView.setText(entity.getUserprizeTitle());
		Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getUserprizeImgpath()).placeholder(R.drawable.img_empty)
				.into(ivImageView);
	}
}
