package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.SignAdapter;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.SignDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.dialog.SignDialog.OnConfirmClick1;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.CommonDataEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.SignCount;
import com.android.storemanage.entity.SignEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.service.TipMessageService;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity1 extends BaseActivity {

	private GridView gridview;
	private String wealthValue;
	private Button btn_sign;
	private TipMessageService tip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin1);
		gridview = (GridView) findViewById(R.id.gridview_sgin);
		btn_sign= (Button) findViewById(R.id.btn_sign);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("签到");
	}

	@Override
	protected void onStart() {
		super.onStart();
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	private void initData() {
		getSignCount(null);
	}

	public void gotoMain(View v) {
		visitSignInterface();
		
	}
	
	private void visitSignInterface(){
		if(CommonUtil.checkNetState(mContext)){
			RequestParams params = new RequestParams();
			params.put("userId",application.getUserId());
			params.put("useremail", application.getUserEmail());
			params.put("lshToken", CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.get(JFConfig.USER_SIGN, params, new AsyncHttpResponseHandler() {
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
					CommonDataEntity sign = commonData.getCommonData();
					if("true".equals(sign.getTodayHaveSigned())){
//						CRAlertDialog dialog = new CRAlertDialog(mContext);
//						dialog.show("亲，今天已经签到了。。", 1000);
						Toast.makeText(mContext, "亲，今天已经签到了。。", 2000);
						btn_sign.setEnabled(false);
					}else{
						wealthValue = sign.getWealthValueAdd();
						final SignDialog dialog1 = new SignDialog(mContext,"增加财富值:"+wealthValue);
						dialog1.setOnConfirmClick(new OnConfirmClick1() {
							
							@Override
							public void onClick(View view) {
								dialog1.dismiss();
								
							}
						});
						
						dialog1.show();
						
					}

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					RetryDialog dialog = new RetryDialog(mContext,"签到异常");
					dialog.setOnConfirmClick(new OnConfirmClick() {

						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.sureBtn:// 确定
								break;
							case R.id.cancelBtn:// 取消
								finish();
								break;
							}
						}
					});
					dialog.show();

				}
			});
		}
	}
	
	public void getSignCount(final String flag){

		if(CommonUtil.checkNetState(mContext)){
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			params.put("useremail",application.getUserEmail());
			params.put("lshToken", CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.get(JFConfig.SIGN_COUNT, params, new AsyncHttpResponseHandler() {
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
					SignCount signCount = new SignCount();
					signCount.setSignCount(commonData.getCommonData().getSignCount());
					if("refresh".equals(flag)){
						gridview.setAdapter(new SignAdapter(mContext, signCount,btn_sign));
					}else{
						if(!"true".equals(commonData.getCommonData().getReturnStatus())){
							CRAlertDialog dialog = new CRAlertDialog(mContext);
							dialog.show("签到异常", 3000);
						}else{
							gridview.setAdapter(new SignAdapter(mContext, signCount,btn_sign));
							if(!"true".equals(commonData.getCommonData().getTodayHaveSigned())){
								Toast.makeText(mContext, "你今天还没有签到。。", 2000).show();
								
							}else{
								Toast.makeText(mContext, "你今天已经签到。。", 2000).show();
								btn_sign.setEnabled(false);
							}
						
						}
					}
					

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					RetryDialog dialog = new RetryDialog(mContext,"签到异常");
					dialog.setOnConfirmClick(new OnConfirmClick() {

						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.sureBtn:// 确定
								break;
							case R.id.cancelBtn:// 取消
								finish();
								break;
							}
						}
					});
					dialog.show();

				}
			});
		}
	
	}

}
