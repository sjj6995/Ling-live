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
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.service.UpdateService;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

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
		gotoCheckUpdate();// 先检查版本更新
	}

	/**
	 * 检查版本更新
	 */
	private void gotoCheckUpdate() {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("phonetype", "android");
			params.put("appversionNumber", CommonUtil.getVersion(mContext));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CHECK_UPDATE, params, new AsyncHttpResponseHandler() {
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
						chooseDifferentStatus(commonData);
					} else {
						if (!TextUtils.isEmpty(userId)) {
							gotoMain();
						} else {
							initData();// 看当前的用户号是否注册成功
						}
					}

				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					dismissProgressDialog();
					CommonUtil.onFailure(error, mContext);
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
			dialog.setConfirmText("更新");
			dialog.setCancelText("退出");
			dialog.setContent(commonData.getAppVersionData().getAppversionUpdateinfo());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
//						gotoUpdateService(commonData.getAppVersionData().getAppversionUpdateurl());
						gotoWebPage(commonData.getAppVersionData().getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						SplashActivity.this.finish();
						break;
//						dialog.dismiss();
//						if (!TextUtils.isEmpty(userId)) {
//							gotoMain();
//						} else {
//							initData();// 看当前的用户号是否注册成功
//						}
//						break;
					}
				}
			});
			dialog.show();
			break;
		case 1:// 可以更新
			dialog.setConfirmText("更新");
			dialog.setCancelText("取消");
			dialog.setContent(commonData.getAppVersionData().getAppversionUpdateinfo());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
//						gotoUpdateService(commonData.getAppVersionData().getAppversionUpdateurl());
						gotoWebPage(commonData.getAppVersionData().getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						if (!TextUtils.isEmpty(userId)) {
							gotoMain();
						} else {
							initData();// 看当前的用户号是否注册成功
						}
						break;
					}
				}
			});
			dialog.show();
			break;
		case 2:// 无需更新
			if (!TextUtils.isEmpty(userId)) {
				gotoMain();
			} else {
				initData();
			}
			break;
		default:
			break;
		}
	}
	
	private void gotoWebPage(String resultString){
		try {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(resultString);
			intent.setData(content_url);
			startActivity(intent);

		} catch (Exception e) {
			Intent intent = new Intent(SplashActivity.this,WealthDetailActivity.class);
			intent.putExtra("url", resultString);
			startActivity(intent);
		}
	}

	private void initData() {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("phoneimei",
					PhoneUtil.getDeviceId((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CHECK_ISORNOT_REGISTERED, params, new AsyncHttpResponseHandler() {
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
							userId = commonData.getCommonData().getUserId();
							application.setUserId(userId);
							gotoMain();
						} else {
							gotoRegister();
						}
					} else {
						Toast.makeText(getApplicationContext(), R.string.server_data_exception, Toast.LENGTH_SHORT)
								.show();
						finish();
					}

				}

				@Override
				public void onFailure(Throwable error, String arg1) {
					super.onFailure(error, arg1);
					dismissProgressDialog();
					CommonUtil.onFailure(error, mContext);
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
