package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.WealthPrizeDetailEntity;
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
 * @author liujiao 财富奖品详情页
 * 
 */
public class WealthPrizeDetailActivity extends BaseActivity {
	private ImageView iv;
	private TextView title;
	private TextView wealthTitle;
	private TextView wealthValue;
	private String wPrizeId;
	private Button btnGotoExchange;
	private TextView tvValidateTime;
	private TextView tvMessageDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wealth_pride_detail);
		iv = (ImageView) findViewById(R.id.iv_icon);
		btnGotoExchange = (Button) findViewById(R.id.btn_goto_change);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("奖品详情");
		tvValidateTime = (TextView) findViewById(R.id.tv_validate_time);
		wealthTitle = (TextView) findViewById(R.id.tv_pride_name);
		wealthValue = (TextView) findViewById(R.id.tv_wealth_value);
		wPrizeId = getIntent().getStringExtra("wPrizeId");
		tvMessageDesc = (TextView) findViewById(R.id.tv_message_desc);
		if (TextUtils.isEmpty(wPrizeId)) {
			Toast.makeText(mContext, R.string.server_data_exception, Toast.LENGTH_SHORT).show();
			WealthPrizeDetailActivity.this.finish();
		}
		initData(wPrizeId);
	}

	/**
	 * @param view
	 *            兑换
	 */
	public void gotoExchange(View view) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("wPrizeId", wPrizeId);
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.EXCHANGE_PRIZE, params, new AsyncHttpResponseHandler() {
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
						String isSuccess = commonData.getCommonData().getDuiHuanSuccess();
						if (!TextUtils.isEmpty(isSuccess) && "true".equals(isSuccess)) {
							if (commonData.getCommonData().getDuiHuanUserWealth() > 0) {
								CRAlertDialog dialog = new CRAlertDialog(mContext);
								dialog.show("兑换成功，你增加了" + commonData.getCommonData().getDuiHuanUserWealth() + "个财富值",
										1000);

							}
							btnGotoExchange.setText("已兑换");
							btnGotoExchange.setEnabled(false);
							btnGotoExchange.setVisibility(View.VISIBLE);
						}
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

	private void initData(final String wPrizeId) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("wPrizeId", wPrizeId);
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.GET_WEALTH_RRIZE_DETAILL, params, new AsyncHttpResponseHandler() {
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
						WealthPrizeDetailEntity entity = commonData.getPrizeDetailMap();
						fillData(entity);
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

	protected void fillData(WealthPrizeDetailEntity entity) {
		if (null != entity) {
			Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getWPrizeImgpath())
					.placeholder(R.drawable.img_empty).into(iv);
			tvValidateTime.setText("自领取之日起" + entity.getWPrizeExpirydate() + "日内有效");
			wealthTitle.setText(entity.getWPrizeTitle());
			wealthValue.setText(entity.getWPrizeNeedwealth() + "财富");
			tvMessageDesc.setText(entity.getWPrizeDetail());
			String isUsed = entity.getSfkDuiHuan();
			if (!TextUtils.isEmpty(isUsed)) {
				if ("1".equals(isUsed)) {
					btnGotoExchange.setText("兑换");
					btnGotoExchange.setEnabled(true);
				} else {
					btnGotoExchange.setText("已兑换");
					btnGotoExchange.setEnabled(false);
				}
				btnGotoExchange.setVisibility(View.VISIBLE);
			}
		}
	}
}
