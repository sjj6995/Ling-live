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
import android.widget.EditText;
import android.widget.TextView;

public class ModifyPasswordActivity extends BaseActivity {

	private EditText email,oldPassword,newPassword,confirmPassword;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpassword);
		init();
	}
	private void init(){
		title= (TextView) findViewById(R.id.tv_title);
		email = (EditText) findViewById(R.id.fix_login_email);
		oldPassword = (EditText) findViewById(R.id.fix_login_password);
		newPassword = (EditText) findViewById(R.id.new_login_password);
		confirmPassword = (EditText) findViewById(R.id.new_login_confirm);
		title.setText("零生活");
	}
	public void onBackClicked(View v){
		this.finish();
	}
	public void fixPassword(View v){
		String fix_email = email.getText().toString().trim();		
		CRAlertDialog dialog;
		if (TextUtils.isEmpty(fix_email)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.error_email), 1000);
			return;
		} 
		if(!CommonUtil.checkEmail(fix_email)){
			dialog = new CRAlertDialog(this);
			dialog.show("请填写正确的邮箱", 1000);
			return;
		}
		String oldPwd = oldPassword.getText().toString().trim();
		if (TextUtils.isEmpty(oldPwd)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.error_pwd), 1000);
			return;
		} 
		if(!CommonUtil.checkEmail(oldPwd)){
			dialog = new CRAlertDialog(this);
			dialog.show("请填写正确密码", 1000);
			return;
		}
		String newPwd = newPassword.getText().toString().trim();
		if (TextUtils.isEmpty(newPwd)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.error_pwd), 1000);
			return;
		} 
		if(!CommonUtil.checkEmail(newPwd)){
			dialog = new CRAlertDialog(this);
			dialog.show("请填写正确密码", 1000);
			return;
		}
		String confirm = confirmPassword.getText().toString().trim();
		if (TextUtils.isEmpty(confirm)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.error_pwd), 1000);
			return;
		} 
		if(!confirm.equals(newPwd)){
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.notsame), 1000);
			return;
		}
		
		if(CommonUtil.checkNetState(mContext)){
			RequestParams params = new RequestParams();
			String deviceIdString = PhoneUtil.getDeviceId((TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE));
			params.put("userNewPassword",newPwd);
			params.put("useremail", fix_email);
			params.put("userPassword", CommonUtil.getMD5(oldPwd+JFConfig.COMMON_MD5_STR));
			params.put("lshToken", CommonUtil.getMD5(fix_email+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.FIXPWD, params, new AsyncHttpResponseHandler() {
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
					if(!"true".equals(commonData.getCommonData().getPasswordState())){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("原密码不对，请重输入", 2000);
					}else if(!"true".equals(commonData.getCommonData().getPasswordChanged())){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("修改密码失败，请重试！", 2000);
					}else{
						application.setUserId(commonData.getCommonData().getUserId());
						Intent intent = new Intent(mContext, LoginActivity.class);
						startActivity(intent);
						finish();
					}

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					RetryDialog dialog = new RetryDialog(mContext,"修改失败");
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
