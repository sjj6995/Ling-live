package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.WealthPrizeAdapter;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.WealthPrizeEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liujiao 财富奖区
 * 
 */
public class WealthPrizeActivity extends BaseActivity implements OnClickListener {
	private TextView title;
	private ListView listview;
	private String wealthIdString;
	private String wealthTitleString;
	private Button rbDefault, rbRankByWealth, rbRankByTime;
	private int sortType = 0;
	private TextView tvNoDataTextView;
	private ImageView ivOrderByWealth;
	private ImageView ivOrderByTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_listview_page);
		title = (TextView) findViewById(R.id.tv_title);
		listview = (ListView) findViewById(R.id.lv_sport);
		wealthIdString = getIntent().getStringExtra("wealthId");
		wealthTitleString = getIntent().getStringExtra("wealthTitle");
		title.setText(wealthTitleString);
		rbDefault = (Button) findViewById(R.id.rb_default);
		rbRankByWealth = (Button) findViewById(R.id.rb_fortune);
		rbRankByWealth.setText("所需财富");
		rbRankByTime = (Button) findViewById(R.id.rb_update_time);
		ivOrderByWealth = (ImageView) findViewById(R.id.iv_order_by_wealth);
		ivOrderByTime = (ImageView) findViewById(R.id.iv_order_by_time);
		rbDefault.setOnClickListener(this);
		rbRankByWealth.setOnClickListener(this);
		rbRankByTime.setOnClickListener(this);
		tvNoDataTextView = (TextView) findViewById(R.id.tv_no_data);
		if (TextUtils.isEmpty(wealthIdString)) {
			Toast.makeText(mContext, R.string.server_data_exception, Toast.LENGTH_SHORT).show();
			WealthPrizeActivity.this.finish();
		}
		listview.setEmptyView(tvNoDataTextView);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				WealthPrizeEntity entity = (WealthPrizeEntity) arg0.getItemAtPosition(arg2);
				if (null != entity) {
					Intent itt = new Intent(WealthPrizeActivity.this, WealthPrizeDetailActivity.class);
					itt.putExtra("wPrizeId", entity.getWPrizeId());
					startActivity(itt);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData(wealthIdString, sortType);
	}

	private WealthPrizeAdapter adapter;
	private void initData(final String wealthId, final int sortType) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("wealthareaId", wealthId);
			params.put("sortType", sortType + "");
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.WEALTH_PRIZE, params, new AsyncHttpResponseHandler() {

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
						List<WealthPrizeEntity> msgEntity = commonData.getWealrhareaPrizeMapList();
						if(null == adapter){
							adapter = new WealthPrizeAdapter(mContext, msgEntity);
							listview.setAdapter(adapter);
						}else{
							adapter.setLists(msgEntity);
							adapter.notifyDataSetChanged();
						}
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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rb_default:// 默认
			ivOrderByTime.setVisibility(View.INVISIBLE);
			ivOrderByWealth.setVisibility(View.INVISIBLE);
			sortType = 0;
			initData(wealthIdString, sortType);
			changeBtnColor(Color.WHITE, R.color.button_normal_color, R.color.button_normal_color);
			changeBtnBackground(R.drawable.left_pressed, R.drawable.middle_normal, R.drawable.right_normal);
			break;
		case R.id.rb_fortune:// 财富排行
			ivOrderByTime.setVisibility(View.INVISIBLE);
			if (sortType == 1) {
				sortType = 2;
				ivOrderByWealth.setImageResource(R.drawable.jiantou_up);
			} else {
				sortType = 1;
				ivOrderByWealth.setImageResource(R.drawable.jiantou_down);
			}
			ivOrderByWealth.setVisibility(View.VISIBLE);
			initData(wealthIdString, sortType);
			changeBtnBackground(R.drawable.left_normal, R.drawable.middle_pressed, R.drawable.right_normal);
			changeBtnColor(R.color.button_normal_color, Color.WHITE, R.color.button_normal_color);
			break;
		case R.id.rb_update_time:// 更新时间
			ivOrderByWealth.setVisibility(View.INVISIBLE);
			if (sortType == 3) {
				sortType = 4;
				ivOrderByTime.setImageResource(R.drawable.jiantou_up);
			} else {
				sortType = 3;
				ivOrderByTime.setImageResource(R.drawable.jiantou_down);
			}
			initData(wealthIdString, sortType);
			ivOrderByTime.setVisibility(View.VISIBLE);
			changeBtnBackground(R.drawable.left_normal, R.drawable.middle_normal, R.drawable.right_pressed);
			changeBtnColor(R.color.button_normal_color, R.color.button_normal_color, Color.WHITE);
			break;
		default:
			break;

		}
	}

	public void changeBtnBackground(int defaultBg, int secondBg, int thridBg) {
		rbDefault.setBackgroundResource(defaultBg);
		rbRankByWealth.setBackgroundResource(secondBg);
		rbRankByTime.setBackgroundResource(thridBg);
	}

	public void changeBtnColor(int defaultColor, int secondColor, int thridColor) {
		rbDefault.setTextColor(defaultColor);
		rbRankByWealth.setTextColor(secondColor);
		rbRankByTime.setTextColor(thridColor);
	}
}
