package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.MessageDetailAdapter;
import com.android.storemanage.adapter.WealthPrizeAdapter;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.MessageDetailEntity;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.WealthPrizeEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author liujiao 财富奖区
 * 
 */
public class WealthPrizeActivity extends BaseActivity implements
		OnCheckedChangeListener {
	private TextView title;
	private ListView listview;
	private String wealthIdString;
	private String wealthTitleString;
	private RadioButton rbDefault, rbRankByWealth, rbRankByTime;
	private int sortType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_listview_page);
		title = (TextView) findViewById(R.id.tv_title);
		listview = (ListView) findViewById(R.id.lv_sport);
		wealthIdString = getIntent().getStringExtra("wealthId");
		wealthTitleString = getIntent().getStringExtra("wealthTitle");
		title.setText(wealthTitleString);
		rbDefault = (RadioButton) findViewById(R.id.rb_default);
		rbRankByWealth = (RadioButton) findViewById(R.id.rb_fortune);
		rbRankByTime = (RadioButton) findViewById(R.id.rb_update_time);
		rbDefault.setOnCheckedChangeListener(this);
		rbRankByWealth.setOnCheckedChangeListener(this);
		rbRankByTime.setOnCheckedChangeListener(this);
		if (TextUtils.isEmpty(wealthIdString)) {
			return;
		}
		initData(wealthIdString, sortType);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
	}

	private void initData(final String wealthId, final int sortType) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("wealthareaId", wealthId);
			params.put("sortType", sortType + "");
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.WEALTH_PRIZE, params,
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
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {
								List<WealthPrizeEntity> msgEntity = commonData
										.getWealrhareaPrizeMapList();
								listview.setAdapter(new WealthPrizeAdapter(
										mContext, msgEntity));
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (!isChecked) {
			return;
		}
		switch (buttonView.getId()) {
		case R.id.rb_default:// 默认
			sortType = 0;
			initData(wealthIdString, sortType);
			break;
		case R.id.rb_fortune:// 财富排行
			if (sortType == 1) {
				sortType = 2;
			} else {
				sortType = 1;
			}
			initData(wealthIdString, sortType);
			break;
		case R.id.rb_update_time:// 更新时间
			if (sortType == 3) {
				sortType = 4;
			} else {
				sortType = 3;
			}
			initData(wealthIdString, sortType);
			break;

		default:
			break;
		}
	}
}
