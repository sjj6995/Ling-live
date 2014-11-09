package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeDeviceActivity extends BaseActivity {

	private EditText device_email;
	private EditText device_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changdevice);
		init();
	}
	private void init(){
		register = (Button) findViewById(R.id.register);
		changedevice = (Button) findViewById(R.id.changedevice);
		tv_title = (TextView) findViewById(R.id.tv_title);
		device_email = (EditText) findViewById(R.id.device_email);
		device_password = (EditText) findViewById(R.id.device_password);
		//设置注册和更换设备 不可见
//		changedevice.setVisibility(View.INVISIBLE);
//		register.setVisibility(View.INVISIBLE);
		//更换头标题
		tv_title.setText("绑定设备");
		
	}
	public void onBackToLoginClicked(View v){
		Intent i = new Intent(this,LoginActivity.class);
		startActivity(i);
		finish();
	}
	public void gotoBind(View v){
		String email = device_email.getText().toString().trim();		
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
		String pwd = device_password.getText().toString().trim();
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
			params.put("phoneimei", deviceIdString == null ? "" : deviceIdString);
			params.put("useremail", email);
			params.put("userPassword", CommonUtil.getMD5(pwd+JFConfig.COMMON_MD5_STR));
			params.put("lshToken", CommonUtil.getMD5(email+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CHANGEDEVICE, params, new AsyncHttpResponseHandler() {
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
					String code = commonData.getCommonData().getCode();
					if("0001".equals(code)){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("账号未注册。。",2000);
					}
					if("0002".equals(code)){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("密码错误，请重试。。", 2000);
					}
					if("0003".equals(code)){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("每月只能更新一次设备。。", 2000);
					}
					if("0004".equals(code)){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("无需绑定..", 2000);
					}
					if("0005".equals(code)){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("更新失败请重试.。", 2000);
					}
					if("0007".equals(code)){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("该设备已被绑定.。", 2000);
					}
					if("0006".equals(code)){
						final RetryDialog dialog1 = new RetryDialog(mContext,"更新设备成功，请重新登录。");
						dialog1.setOnConfirmClick(new OnConfirmClick() {

							@Override
							public void onClick(View view) {
								switch (view.getId()) {
								case R.id.sureBtn:// 确定
									gotoLogin();
									break;
								}
							}
						});
						dialog1.show();
					}

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					final RetryDialog dialog = new RetryDialog(mContext,"网络异常");
					dialog.setOnConfirmClick(new OnConfirmClick() {

						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.sureBtn:// 确定
								finish();
								break;
							case R.id.cancelBtn:// 取消
								dialog.cancel();
								break;
							}
						}
					});
					dialog.show();

				}
			});
		}
	}
	public void gotoLogin(){
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
