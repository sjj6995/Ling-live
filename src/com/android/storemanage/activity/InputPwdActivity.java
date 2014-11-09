package com.android.storemanage.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.dialog.SignDialog;
import com.android.storemanage.dialog.SignDialog.OnConfirmClick1;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataLimit;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

public class InputPwdActivity extends BaseActivity{

	private EditText input_pwd;
	private String url;
	private static int flag = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputpwd);
		url = getIntent().getStringExtra("url");
		input_pwd = (EditText) findViewById(R.id.input_pwd);
	}
	public void gotoOtherView(View v){
		if(flag!=1){
			sendMessageToServer(url);
		}else{
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show("密码输错三次了", 2000);
		}
	}
	private void sendMessageToServer(final String resultString) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			params.put("qrcodeInfo", resultString);
			params.put("qrcodePassword", input_pwd.getText().toString().trim());
			params.put("useremail",application.getUserEmail());
			params.put(JFConfig.LSH_TOKEN, CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.SCAN_VERFY, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							dismissProgressDialog();
							if (TextUtils.isEmpty(content)) {
								return;
							}
							OuterData outerData = JSON.parseObject(content,
									OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData()
									.get(0);
							CRAlertDialog dialog = new CRAlertDialog(
									mContext);
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							if (!"true".equals(commonData.getCommonData()
									.getPasswordSfRight())) {
								
									dialog.show("密碼不正確，請重試",
											2000);
									flag=saoMiaoLimit(resultString);
							}else if("true".equals(commonData.getCommonData().getSfScan())){
								dialog.show("您已经扫描过了，不能重複掃描！",
										2000);
							}else if(!"true".equals(commonData.getCommonData().getSfEnough())){
								dialog.show("商家 财富值不足！",
										2000);
							}else if(!"true".equals(commonData.getCommonData().getAddSuccess())){
								dialog.show("失败，请重试！",
										2000);
							}else {
								int userAddValue = commonData.getCommonData()
										.getAddWealth();
			
//										dialog.show("您增加了" + userAddValue + "个财富值",
//												2000);
								final SignDialog wealthDialog = new SignDialog(mContext,"增加财富值:"+userAddValue);
								wealthDialog.setOnConfirmClick(new OnConfirmClick1() {
									
									@Override
									public void onClick(View view) {
										wealthDialog.dismiss();
										InputPwdActivity.this.finish();
									}
								});
								wealthDialog.show();
							}
							
//							openView(resultString);
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
							CommonUtil.onFailure(arg0, mContext);
							CRAlertDialog dialog = new CRAlertDialog(
									mContext);
							dialog.show("網絡異常",
									2000);
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}
	}
	/*
	 * 0代表
	 * 1代表超过三次了
	 * 2成功添加  
	 */
	public int saoMiaoLimit(String resultString){
		int value=0;
		int num =1;
		DataLimit limit = new DataLimit();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String now = f.format(date);
		if(resultString == null){
			return 0;
		}
		try {
		limit.setId(resultString);
		DataLimit tmp = db.query(JFConfig.PWD_LIMIT, limit);
		if(tmp.getId()==null){
			limit.setNum(num+1);
			limit.setTime(now);
			db.insertZXing(JFConfig.PWD_LIMIT, limit);
			value=2;
		}else if(tmp.getNum()<3){
			DataLimit d = new DataLimit();
			d.setId(tmp.getId());
			d.setNum(tmp.getNum()+1);
			d.setTime(tmp.getTime());
			db.insertZXing(JFConfig.PWD_LIMIT, d);
			value = 2;
		} else {
			if(!tmp.getTime().equals(now)){
				limit.setNum(0);
				limit.setTime(now);
				db.insertZXing(JFConfig.PWD_LIMIT, limit);
				value = 2;
			}else{
				value =1;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return value;
}
}
