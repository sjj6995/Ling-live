package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.InputAddress;
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

public class InputAddressActivity extends BaseActivity {

	private EditText user_name,user_tel,user_address,user_postcode;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_address);
		init();
	}
	private void init(){
		title= (TextView) findViewById(R.id.tv_title);
		user_name = (EditText) findViewById(R.id.user_name);
		user_tel = (EditText) findViewById(R.id.user_tel);
		user_address = (EditText) findViewById(R.id.user_address);
		user_postcode = (EditText) findViewById(R.id.user_postcode);
		title.setText("零生活");
	}
	public void onBackClicked(View v){
		this.finish();
	}
	public void saveAddress(View v){
		String userName = user_name.getText().toString().trim();		
		String userTel = user_tel.getText().toString().trim();
		String userAddress = user_address.getText().toString().trim();
		String userPostCode = user_postcode.getText().toString().trim();
		if(TextUtils.isEmpty(userName)){
			new CRAlertDialog(this).show("用户名不能为空", 1000);
			return;
		}
		if(TextUtils.isEmpty(userTel)){
			new CRAlertDialog(this).show("电弧不能为空", 1000);
			return;
		}
		if(TextUtils.isEmpty(userAddress)){
			new CRAlertDialog(this).show("地址不可为空", 1000);
			return;
		}
		if(TextUtils.isEmpty(userPostCode)){
			new CRAlertDialog(this).show("邮政编码不可空", 1000);
			return;
		}
		if(CommonUtil.checkNetState(mContext)){
			RequestParams params = new RequestParams();
			String deviceIdString = PhoneUtil.getDeviceId((TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE));
			params.put("useremail",userName);
			params.put("", userTel);
			params.put("", userAddress);
			params.put("", userPostCode);
			params.put("lshToken", CommonUtil.getMD5(userName+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.SAVEADDERSS, params, new AsyncHttpResponseHandler() {
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
					InputAddress orderAddress = commonData.getInputAddress();
					if(!"true".equals("s") ){
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("原密码不对，请重输入", 2000);
					}else if(!"true".equals("s")) {
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show("修改密码失败，请重试！", 2000);
					}else{
						application.setUserId(orderAddress.getUserId());
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
