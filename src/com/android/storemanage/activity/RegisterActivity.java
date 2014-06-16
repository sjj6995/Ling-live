package com.android.storemanage.activity;


import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author gwj 注册
 * 
 */
public class RegisterActivity extends BaseActivity {
	private TextView tView;
	private EditText mPhonEditText;
	private EditText mEmailEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		tView = (TextView) findViewById(R.id.tv_title);
		mPhonEditText = (EditText) findViewById(R.id.et_phone);
		mEmailEditText = (EditText) findViewById(R.id.et_email);
		tView.setText("注册");
	}

	public void gotoRegister(View view) {
		String phoneString = mPhonEditText.getText().toString();
		CRAlertDialog dialog;
		if (TextUtils.isEmpty(phoneString)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.please_input_corret_phone), 1000);
		}
		String email = mEmailEditText.getText().toString();
		if (TextUtils.isEmpty(email)) {
			dialog = new CRAlertDialog(this);
			dialog.show(getString(R.string.please_input_corret_email), 1000);
		}
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("phoneimei", PhoneUtil
					.getDeviceId((TelephonyManager) mContext
							.getSystemService(Context.TELEPHONY_SERVICE)));
			params.put("phonenumber", phoneString);
			params.put("useremail", email);
			XDHttpClient.get(JFConfig.REGISTER, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							OuterData outerData = JSON.parseObject(content,OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData().get(0);
							log.i("commonData"+commonData.getCommonData().getMsg());
							
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
						}
					});
		} else {
			dialog = new CRAlertDialog(mContext);
			dialog.show(mContext.getString(R.string.pLease_check_network), 2000);
		}
	}

}
