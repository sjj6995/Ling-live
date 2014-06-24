package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.UserPrizeAdapter;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserPrizeDetailEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
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

/**
 * @author unknow 奖品使用详情
 * 
 */
public class UserPrizeDetailActivity extends BaseActivity {
	private TextView tvTitleTextView2;
	private String userPrizeId;
	private TextView tvTitleTextView;
	private ImageView ivImageView;
	private TextView tvValidateTextView;
	private Button btnUseButton;
	private Boolean isUsed;
	private UserPrizeDetailEntity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pride_detail_item);
		tvTitleTextView2 = (TextView) findViewById(R.id.tv_title);
		tvTitleTextView2.setText("奖品详情");
		userPrizeId = getIntent().getStringExtra("userprizeId");
		isUsed = getIntent().getBooleanExtra("isused", false);
		if (TextUtils.isEmpty(userPrizeId)) {
			CRAlertDialog dialog = new CRAlertDialog(this);
			dialog.show("服务器数据异常", 1500);
			finish();
		} else {
			initViews();
			initData(userPrizeId, "");
		}

	}

	private void initViews() {
		tvTitleTextView = (TextView) findViewById(R.id.tv_pride_name);
		tvValidateTextView = (TextView) findViewById(R.id.tv_validate_time);
		ivImageView = (ImageView) findViewById(R.id.iv_icon);
		btnUseButton = (Button) findViewById(R.id.btn_use);
		if (isUsed) {
			btnUseButton.setEnabled(false);
			btnUseButton.setText("已使用");
		}
	}

	/**
	 * @param view
	 *            使用
	 */
	public void gotoUse(View view) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userprizeId", userPrizeId);
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.post(JFConfig.USE_PRIZE, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							dismissProgressDialog();
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
							CRAlertDialog dialog = new CRAlertDialog(mContext);
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {
								String isSuccess = commonData.getCommonData()
										.getUseSuccess();
								if (!TextUtils.isEmpty(isSuccess)
										&& "true".equals(isSuccess)) {
									dialog.show("使用成功", 2000);
									btnUseButton.setEnabled(false);
									btnUseButton.setText("已使用");
								} else {
									dialog.show(commonData.getCommonData()
											.getReason(), 2000);
								}
							} else {
								dialog.show(commonData.getCommonData().getReason(), 2000);
							}
						} 
						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
						}

				});
			} else {
				CRAlertDialog dialog = new CRAlertDialog(mContext);
				dialog.show(getString(R.string.pLease_check_network), 2000);
			}

	}

	private void initData(String userPrizeId2, String string) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userId", "11111");
			params.put("userprizeId", userPrizeId2);
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.post(JFConfig.GET_PRIZE_DETAIL, params, new AsyncHttpResponseHandler() {

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
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	protected void fillData(UserPrizeDetailEntity entity) {
		tvTitleTextView.setText(entity.getUserprizeTitle());
		tvValidateTextView.setText("有效期剩余" + entity.getUserprizeValidity() + "天");
		Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getUserprizeImgpath()).placeholder(R.drawable.img_empty)
				.into(ivImageView);
		// String isUsed = entity.getUserprizeSfused();
		// if (!TextUtils.isEmpty(isUsed)) {
		// if ("1".equals(isUsed)) {
		// btnUse.setText("已使用");
		// btnUse.setEnabled(false);
		// } else {
		// btnUse.setText("使用");
		// btnUse.setEnabled(true);
		// }
		// }
	}
}
