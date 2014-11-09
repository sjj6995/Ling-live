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
import android.widget.Toast;

public class SignInActivity extends BaseActivity {

	private GridView gridview;
	private String wealthValue;
	private Button btn_sign;
	private TipMessageService tip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		gridview = (GridView) findViewById(R.id.gridview_sgin);
		btn_sign= (Button) findViewById(R.id.btn_sign);
		initData();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
	}

	private void initData() {
//		if ("noId".equals(sp.getString("userId", "noId"))) {
//			Intent i = new Intent(SignInActivity.this, LoginActivity.class);
//			startActivity(i);
//			this.finish();
//		}
		getSignCount();
	}

	public void gotoMain(View v) {
		visitSignInterface();
		
	}
	private void turnToMain(){
		Intent i = new Intent(SignInActivity.this, MainTabActivity.class);
		i.putExtra("userId", application.getUserId());
		startActivity(i);
		if(application.list.size() !=0){
			for (Activity a : application.list) {
				a.finish();
			}
			
		}
		finish();
//		waitFinish(1500);
	
	}
	//开启消息接受
	public  void getMessageFromServer(){
		Intent i = new Intent(this,TipMessageService.class);
		bindService(i, connection,BIND_AUTO_CREATE);
//		startService(i);
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
						turnToMain();
					}else{
						wealthValue = sign.getWealthValueAdd();
						SignDialog dialog1 = new SignDialog(mContext,"增加财富值:"+wealthValue);
						dialog1.setOnConfirmClick(new OnConfirmClick1() {
							
							@Override
							public void onClick(View view) {
								turnToMain();
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
	
	public void getSignCount(){

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
					if(!"true".equals(commonData.getCommonData().getReturnStatus())){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("签到异常", 3000);
					}else{
						if("true".equals(commonData.getCommonData().getTodayHaveSigned())){
							gridview.setAdapter(new SignAdapter(mContext, signCount,btn_sign));
							turnToMain();
						}else{
							gridview.setAdapter(new SignAdapter(mContext, signCount,btn_sign));
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
	public void waitFinish(final int time){
		new Thread(){
			public void run() {
				try {
					sleep(time);
					SignInActivity.this.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	 ServiceConnection connection = new ServiceConnection() {  
	       
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				tip = ((TipMessageService.ServiceBinder) service).getService();
				tip.getMessage();
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}  
	    };  

}
