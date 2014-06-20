package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
import com.android.storemanage.service.UpdateService;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

/**
 * @author liujiao 启动页
 * 
 */
public class SplashActivity extends BaseActivity {
	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		userId = application.getUserId();
//		gotoCheckUpdate();
		if (TextUtils.isEmpty(userId)) {
			gotoRegister();
		} else {
			initData();
		}
	}

	/**
	 * 检查版本更新
	 */
	private void gotoCheckUpdate() {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("phonetype", "android");
			params.put("appversionNumber", CommonUtil.getVersion(mContext));
//			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.CHECK_ISORNOT_REGISTERED, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
					log.i("content===" + content);
//					dismissProgressDialog();
					if (TextUtils.isEmpty(content)) {
						return;
					}
					OuterData outerData = JSON.parseObject(content, OuterData.class);
					InnerData innderData = outerData.getData().get(0);
					CollectionData commonData = innderData.getData().get(0);
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						chooseDifferentStatus(commonData);
					} else {
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("服务器内部错误", 2000);
						finish();
					}

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
//					dismissProgressDialog();
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(mContext.getString(R.string.pLease_check_network), 2000);
		}
	}

	private void chooseDifferentStatus(final CollectionData commonData) {
		int appversionNeedUpdate = commonData.getAppVersionData().getSfNeedUpdate();
		final RetryDialog dialog = new RetryDialog(mContext);
		switch (appversionNeedUpdate) {
		case 0:// 必须更新
			dialog.setConfirmText("必须更新");
			dialog.setContent(commonData.getAppVersionData().getUpdateExplain());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
						gotoUpdateService(commonData.getAppVersionData().getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						SplashActivity.this.finish();
						break;
					}
				}
			});
			break;
		case 1:// 可以更新
			dialog.setConfirmText("可以更新");
			dialog.setContent(commonData.getAppVersionData().getUpdateExplain());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
						gotoUpdateService(commonData.getAppVersionData().getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						gotoMain();
						break;
					}
				}
			});
			break;
		case 2:// 无需更新
			gotoMain();
			break;
		default:
			break;
		}
	}

	private void initData() {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("phoneimei",
					PhoneUtil.getDeviceId((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)));
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.CHECK_ISORNOT_REGISTERED, params, new AsyncHttpResponseHandler() {
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
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						String isRegistered = commonData.getCommonData().getRegistered();
						if (!TextUtils.isEmpty(isRegistered) && "true".equals(isRegistered)) {// 已经注册成功
							// gotoMain();
							gotoCheckUpdate();
						} else {
							gotoRegister();
						}
					} else {
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("服务器内部错误", 2000);
						finish();
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
			dialog.show(mContext.getString(R.string.pLease_check_network), 2000);
		}
	}

	private void gotoRegister() {
		Intent itt = new Intent(this, RegisterActivity.class);
		startActivity(itt);
		finish();
	}
	
	private void gotoUpdateService(String url) {
		Intent itt = new Intent(this, UpdateService.class);
		itt.putExtra("url", url);
		startService(itt);
	}

	private void gotoMain() {
		Intent itt = new Intent(this, MainTabActivity.class);
		startActivity(itt);
		finish();
	}
}
