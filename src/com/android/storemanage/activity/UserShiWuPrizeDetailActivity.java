package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserPrizeDetailEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author unknow 奖品使用详情
 * 
 */
public class UserShiWuPrizeDetailActivity extends BaseActivity {
	private TextView tvTitleTextView2;
	private String userPrizeId,isOrdered,uSwddId;
	private TextView tvTitleTextView;
	private ImageView ivImageView;
	private TextView tvValidateTextView;
	private Button btnUseButton,btnPrizeButton;
	private UserPrizeDetailEntity entity;
	private TextView tvDescTextView;
	private EditText user_name,user_number,user_postcode,user_address;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pride_shiwu_item);
		tvTitleTextView2 = (TextView) findViewById(R.id.tv_title);
		tvTitleTextView2.setText("奖品详情");
		userPrizeId = getIntent().getStringExtra("userprizeId");
		uSwddId = getIntent().getStringExtra("uSwddId");
		//isOrdered = getIntent().getStringExtra();
		tvDescTextView = (TextView) findViewById(R.id.tv_message_desc);
		if (TextUtils.isEmpty(userPrizeId)) {
			Toast.makeText(this, R.string.server_data_exception, Toast.LENGTH_SHORT).show();
			finish();
		} else {
			initViews();
			initData(userPrizeId);
		}

	}

	private void initViews() {
		tvTitleTextView = (TextView) findViewById(R.id.tv_pride_name);
		tvValidateTextView = (TextView) findViewById(R.id.tv_validate_time);
		ivImageView = (ImageView) findViewById(R.id.iv_icon);
		btnUseButton = (Button) findViewById(R.id.btn_use);
		btnPrizeButton = (Button)findViewById(R.id.btn_pride);
		user_name = (EditText) findViewById(R.id.user_name2);
		user_number = (EditText) findViewById(R.id.user_tel2);
		user_postcode =(EditText) findViewById(R.id.user_postcode2);
		user_address = (EditText) findViewById(R.id.user_address2);
		//if(isOrdered){btnPrizeButton.setText
//		user_name.setText(entity.getShrxm());
//		user_name.setEnabled(false);
//		user_address.setText(entity.getShrdz());
//		user_address.setEnabled(false);
//		user_postcode.setText(entity.getShryzbm());
//		user_postcode.setEnabled(false);
//		user_number.setText(entity.getShrlxdh());
//		user_number.setEnabled(false);
//		}else{ 
//			
//		}
	}

	/**
	 * @param view
	 *            使用
	 */
	public void gotoUse(View view) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userprizeId", userPrizeId);
			params.put("userId", application.getUserId());
			params.put("useremail", application.getUserEmail());
			params.put("uSwddId", uSwddId);
			params.put(JFConfig.LSH_TOKEN, CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			System.out.println(params+"----------------------------------------");
			showProgressDialog(R.string.please_waiting);
//			HttpClient.get(JFConfig.CONFIRM, params, new AsyncHttpResponseHandler(){
//				@Override
//				public void onSuccess(int statusCode, String content) {
//					log.i("content===" + content);
//					dismissProgressDialog();
//					if (TextUtils.isEmpty(content)) {
//						return;
//					}
//					OuterData outerData = JSON.parseObject(content, OuterData.class);
//					InnerData innderData = outerData.getData().get(0);
//					CollectionData commonData = innderData.getData().get(0);
//					log.i("commonData" + commonData.getCommonData().getMsg());
//					CRAlertDialog dialog = new CRAlertDialog(mContext);
//					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
//						if("true".equals(commonData.getCommonData().getStatus())){
//							dialog.show("收货成功", 2000);
//							new Thread(){
//								public void run() {
//									try{
//										sleep(2500);
//										UserShiWuPrizeDetailActivity.this.finish();
//									}catch(Exception e){
//										e.printStackTrace();
//									}
//									
//								};
//							}.start();
//						}
//						
//					} else {
//						dialog.show(commonData.getCommonData().getReason(), 2000);
//					}
//				}
//
//				@Override
//				public void onFailure(Throwable arg0, String arg1) {
//					super.onFailure(arg0, arg1);
//					dismissProgressDialog();
//					CommonUtil.onFailure(arg0, mContext);
//				}
//			});
			HttpClient.post(JFConfig.CONFIRM, params, new AsyncHttpResponseHandler() {
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
					log.i("commonData" + commonData.getCommonData().getMsg());
					CRAlertDialog dialog = new CRAlertDialog(mContext);
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						if("true".equals(commonData.getCommonData().getStatus())){
							dialog.show("收货成功", 2000);
							new Thread(){
								public void run() {
									try{
										sleep(2500);
										UserShiWuPrizeDetailActivity.this.finish();
									}catch(Exception e){
										e.printStackTrace();
									}
									
								};
							}.start();
						}
						
					} else {
						dialog.show(commonData.getCommonData().getReason(), 2000);
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					CommonUtil.onFailure(arg0, mContext);
				}

			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}
		initData(userPrizeId);

	}

	private void initData(String userPrizeId2) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			params.put("userprizeId", userPrizeId2);
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.GET_PRIZE_DETAIL, params, new AsyncHttpResponseHandler() {

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
					log.i("commonData" + commonData.getCommonData().getMsg());
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						entity = commonData.getUserprizeDetailMap();
						if (null != entity) {
							fillData(entity);
						}
					} else {
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show(commonData.getCommonData().getMsg(), 2000);
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					CommonUtil.onFailure(arg0, mContext);
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	protected void fillData(UserPrizeDetailEntity entity) {
		tvTitleTextView.setText(entity.getUserprizeTitle());
		Picasso.with(mContext).load(JFConfig.IMA_URL + entity.getUserprizeImgpath()).placeholder(R.drawable.img_empty)
				.into(ivImageView);
		tvDescTextView.setText(entity.getUserprizeDetail());
		String isUsed = entity.getUserprizeSfused();
		if (!TextUtils.isEmpty(isUsed)) {
			if ("yfh".equals(isUsed)) {
//				btnUseButton.setText("已使用");
				btnUseButton.setVisibility(View.VISIBLE);
				btnUseButton.setText("确认收货");
				tvValidateTextView.setText("已发货");
//				btnUseButton.setEnabled(false);
			} else if("chz".equals(isUsed)) {
				btnUseButton.setVisibility(View.INVISIBLE);
				btnUseButton.setText("");
				tvValidateTextView.setText("出货中");
			}else if("ysh".equals(isUsed)){
				btnUseButton.setVisibility(View.INVISIBLE);
				btnUseButton.setText("");
				tvValidateTextView.setText("已收货");
			}
			}
		}

	public void saveOrder(View v){
		CRAlertDialog dialog = null;
		if (TextUtils.isEmpty(user_name.getText().toString().trim())) {
			dialog = new CRAlertDialog(this);
			dialog.show("收件人为空", 1000);
			return;
		} 
		if (TextUtils.isEmpty(user_address.getText().toString().trim())) {
			dialog = new CRAlertDialog(this);
			dialog.show("地址为空", 1000);
			return;
		} 
		if (TextUtils.isEmpty(user_number.getText().toString().trim())) {
			dialog = new CRAlertDialog(this);
			dialog.show("电话为空", 1000);
			return;
		} 
		if (TextUtils.isEmpty(user_postcode.getText().toString().trim())) {
			dialog = new CRAlertDialog(this);
			dialog.show("邮编为空", 1000);
			return;
		} 
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userprizeId", userPrizeId);
			params.put("shrxm", user_name.getText().toString().trim());
			params.put("shrdz", user_address.getText().toString().trim());
			params.put("shrlxdh", user_number.getText().toString().trim());
			params.put("shryzbm", user_postcode.getText().toString().trim());
			params.put("useremail", application.getUserEmail());
			params.put(JFConfig.LSH_TOKEN, CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.SAVE_ORDER, params, new AsyncHttpResponseHandler() {
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
					log.i("commonData" + commonData.getCommonData().getMsg());
					CRAlertDialog dialog = new CRAlertDialog(mContext);
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						dialog.show("下单成功，等待发货", 2000);
						new Thread(){
							public void run() {
								try{
									sleep(2500);
									UserShiWuPrizeDetailActivity.this.finish();
								}catch(Exception e){
									e.printStackTrace();
								}
								
							};
						}.start();
						
						
					} else {
							dialog.show("下单失败", 2000);
					}
					
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					CommonUtil.onFailure(arg0, mContext);
				}

			});
		} else {
			CRAlertDialog dialog1 = new CRAlertDialog(mContext);
			dialog1.show(getString(R.string.pLease_check_network), 2000);
		}
	}
	private void gotoMainList(){
	
	}
}
