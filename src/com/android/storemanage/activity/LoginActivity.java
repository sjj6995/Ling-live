package com.android.storemanage.activity;



import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserInfo;

import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class LoginActivity extends BaseActivity {
	private EditText login_email,login_password;
	private TextView login_tip,forget;
	private Editor editor;
	private String email;
	private UserInfo user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
	}
	private void init(){
		back_login = (ImageButton) findViewById(R.id.back_login);
		login_email = (EditText) findViewById(R.id.login_email);
		login_password = (EditText) findViewById(R.id.login_password);

		forget = (TextView) findViewById(R.id.forget);
		back_login.setVisibility(View.INVISIBLE);
//		 if(!application.getUserId().equals("")){
////		    	Intent intent = new Intent(mContext, MainTabActivity.class);
//			 	Intent intent = new Intent(mContext,SignInActivity.class);
//		    	intent.putExtra("userId", application.getUserId());
//				startActivity(intent);
//				application.addActivity1(this);
////				finish();
//		}
		//写入数据库中
		 user = db.queryUser(JFConfig.T_USER);
		 if(user.getUserId()!=null){
			 	application.setUserId(user.getUserId());
			 	application.setUserEmail(user.getUserPwd());
//		    	Intent intent = new Intent(mContext, MainTabActivity.class);
			 	Intent intent = new Intent(mContext,SignInActivity.class);
		    	intent.putExtra("userId", user.getUserId());
				startActivity(intent);
				application.addActivity1(this);
//				finish();
		}
			/**
			 * 正式需要注释掉
			 
		    editor = sp.edit();
		    editor.putString("userId", "b3bd7cc02a1c41e490a0fc16cd16edb7");
		    editor.commit();
		    */
	}
	public void gotoMain(View v){
		//文件获取用户记住的用户名和密码
//		login_email.setText(userEmail);
		email = login_email.getText().toString().trim();		
		CRAlertDialog dialog;
		if (TextUtils.isEmpty(email)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.error_email), 1000);
			return;
		} 
		if(!CommonUtil.checkEmail(email)){
			dialog = new CRAlertDialog(this);
			dialog.show("用户名格式不对，应为邮箱地址", 1000);
			return;
		}
		String pwd = login_password.getText().toString().trim();
		if (TextUtils.isEmpty(pwd)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.error_pwd), 1000);
			return;
		} 
		if(!CommonUtil.checkPassword(pwd)){
			dialog = new CRAlertDialog(this);
			dialog.show("密码格式不对", 1000);
			return;
		}
		if(CommonUtil.checkNetState(mContext)){
			RequestParams params = new RequestParams();
			String deviceIdString = PhoneUtil.getDeviceId((TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE));
			params.put("phonetype", "android");
			//测试账号串号
			params.put("phoneimei", deviceIdString == null ? "" : deviceIdString);
//			params.put("phoneimei", "860157010491279");
			params.put("useremail", email);
			params.put("userPassword", CommonUtil.getMD5(pwd+JFConfig.COMMON_MD5_STR));
			params.put("lshToken", CommonUtil.getMD5(email+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			System.out.println(JFConfig.LOGIN);
			HttpClient.get(JFConfig.LOGIN, params, new AsyncHttpResponseHandler() {
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
					if(!"true".equals(commonData.getCommonData().getRegisterState())){
						final RetryDialog dialog1 = new RetryDialog(mContext,"您还没注册，是否注册？");
						dialog1.setOnConfirmClick(new OnConfirmClick() {

							@Override
							public void onClick(View view) {
								switch (view.getId()) {
								case R.id.sureBtn:// 确定
									gotoRegister();
									break;
								case R.id.cancelBtn:// 取消
									dialog1.dismiss();
									break;
								}
							}
						});
						dialog1.show();
					}else if(!"true".equals(commonData.getCommonData().getDeviceState())){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("设备没绑定，请绑定设备。。", 2000);
					}else if(!"true".equals(commonData.getCommonData().getLoginState())){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("密码错误，请重试。。", 2000);
					}else{
						//存储在应用中
						if(application.getUserId().equals("")){
							application.setUserId(commonData.getCommonData().getUserId());
							application.setUserEmail(email);
						}
						//存储在数据库中
						if(user.getUserId() == null){
							UserInfo u = new UserInfo();
							u.setUserId(commonData.getCommonData().getUserId());
							u.setUserPwd(email);
							db.inserIntoUserInfo(JFConfig.T_USER, u);
						}
						application.setUserId(commonData.getCommonData().getUserId());
//						Intent intent = new Intent(mContext, MainTabActivity.class);
						Intent intent = new Intent(mContext,SignInActivity.class);
						intent.putExtra("userId", application.getUserId());
						startActivity(intent);
						application.addActivity1(LoginActivity.this);
//						finish();
					}

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					RetryDialog dialog = new RetryDialog(mContext,"登陆失败");
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
	public void onRegisterClicked(View v){
		gotoRegister();
	}
	public void  gotoRegister(){
		Intent itt = new Intent(this, RegisterActivity.class);
		startActivity(itt);
		finish();
	}
	public void onChangeClicked(View v){
		Intent device  = new Intent(this,ChangeDeviceActivity.class);
		startActivity(device);
	}
	public void forgetpwd(View v){
		String email = login_email.getText().toString().trim();		
		CRAlertDialog dialog;
		if (TextUtils.isEmpty(email)) {
			dialog = new CRAlertDialog(this);
			dialog.show("邮箱不可为空！", 1000);
			return;
		} 
		RequestParams params = new RequestParams();
		params.put("useremail", email);
		params.put("lshToken", CommonUtil.getMD5(email+JFConfig.COMMON_MD5_STR));
		HttpClient.post(JFConfig.FORGET, params, new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				log.i("content===" + content);
				dismissProgressDialog();
				if (TextUtils.isEmpty(content)) {
					return;
				}
				OuterData outerData = JSON.parseObject(content, OuterData.class);
				InnerData innderData = outerData.getData().get(0);
				CollectionData commonData = innderData.getData().get(0);
				if(!"true".equals(commonData.getCommonData().getUserExist())){
					CRAlertDialog dialog = new CRAlertDialog(mContext);
					dialog.show("用户不存在", 2000);
				}else{ 
					if("true".equals(commonData.getCommonData().getEmailSent())){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("随机密码已发送到邮箱！", 2000);
					}else{
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("请重试！", 2000);
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
				CRAlertDialog dialog = new CRAlertDialog(mContext);
				dialog.show("服务器异常", 2000);
			}
			
		});
	}
	
}
