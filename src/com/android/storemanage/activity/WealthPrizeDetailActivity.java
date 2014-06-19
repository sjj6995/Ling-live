package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.WealthPrizeEntity;
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
import android.widget.ImageView;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wealth_pride_detail);
		iv = (ImageView) findViewById(R.id.iv_icon);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("奖品详情");
		wealthTitle = (TextView) findViewById(R.id.tv_pride_name);
		wealthValue = (TextView) findViewById(R.id.tv_wealth_value);
		wPrizeId = getIntent().getStringExtra("wPrizeId");
		if (TextUtils.isEmpty(wPrizeId)) {
			return;
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
			XDHttpClient.get(JFConfig.EXCHANGE_PRIZE, params, new AsyncHttpResponseHandler() {
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
						if (!TextUtils.isEmpty(isSuccess) && "true".equals(isSuccess)
								&& commonData.getCommonData().getDuiHuanUserWealth() > 0) {
							CRAlertDialog dialog = new CRAlertDialog(mContext);
							dialog.show("兑换成功，你增加了" + commonData.getCommonData().getDuiHuanUserWealth() + "个财富值", 1000);
						}
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

	private void initData(final String wPrizeId) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("wPrizeId", wPrizeId);
			params.put("userId", "111");
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.GET_PRIZE_DETAIL, params, new AsyncHttpResponseHandler() {
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
						WealthPrizeEntity entity = commonData.getPrizeDetailMap();
						fillData(entity);
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

	protected void fillData(WealthPrizeEntity entity) {
		if (null != entity) {
			Picasso.with(mContext).load(JFConfig.HOST_URL + entity.getWPrizeImgpath())
					.placeholder(R.drawable.img_empty).into(iv);

			wealthTitle.setText(entity.getWPrizeTitle());
			wealthValue.setText(entity.getWPrizeTotalnumber());
		}
	}
}
