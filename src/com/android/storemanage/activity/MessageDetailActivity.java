package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.MessageDetailAdapter;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.MessageDetailEntity;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 消息详情
 * 
 */
public class MessageDetailActivity extends BaseActivity {
	private String messageIdString;
	private TextView titleTextView;
	private ListView listView;
	private TextView tView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_message);
		messageIdString = getIntent().getStringExtra("messageId");
		tView = (TextView) findViewById(R.id.tv_no_data);
		initViews();
		if (!TextUtils.isEmpty(messageIdString)) {
			initData(messageIdString);
		}
	}

	private void initViews() {
		titleTextView = (TextView) findViewById(R.id.tv_title);
		titleTextView.setText("消息详情");
		listView = (ListView) findViewById(R.id.lv_message);
		listView.setEmptyView(tView);
	}

	private MessageDetailAdapter adapter;
	private void initData(final String messageIdString2) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			showProgressDialog(R.string.please_waiting);
			params.put("messageId", messageIdString2);
			HttpClient.post(JFConfig.MESSAGE_DETAIL, params, new AsyncHttpResponseHandler() {

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
						List<MessageDetailEntity> msgEntity =commonData.getMessageDetailMapList();
						if(null == adapter){
							adapter = new MessageDetailAdapter(mContext, msgEntity);
							listView.setAdapter(new MessageDetailAdapter(mContext, msgEntity));
						}else{
							adapter.setLists(msgEntity);
							adapter.notifyDataSetChanged();
						}
					}else {
//						CRAlertDialog dialog = new CRAlertDialog(mContext);
//						dialog.show(commonData.getCommonData().getMsg(), 2000);
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
}
