package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.ClassifyListAdapter;
import com.android.storemanage.entity.BrandEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
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

public class CategoryListActivity extends BaseActivity implements OnClickListener {
	private Button rbDefault, rbRankByWealth, rbRankByTime;
	private ListView listView;
	private String categoryIdString;
	private int cBrandId = 0;
	private TextView tView;
	private ImageView ivOrderByWealth;
	private ImageView ivOrderByTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_listview_page);
		rbDefault = (Button) findViewById(R.id.rb_default);
		rbRankByWealth = (Button) findViewById(R.id.rb_fortune);
		rbRankByTime = (Button) findViewById(R.id.rb_update_time);
		ivOrderByWealth = (ImageView) findViewById(R.id.iv_order_by_wealth);
		ivOrderByTime = (ImageView) findViewById(R.id.iv_order_by_time);
		rbDefault.setOnClickListener(this);
		rbRankByWealth.setOnClickListener(this);
		rbRankByTime.setOnClickListener(this);
		categoryIdString = getIntent().getStringExtra("categoryId");
		listView = (ListView) findViewById(R.id.lv_sport);
		tView = (TextView) findViewById(R.id.tv_no_data);
		((TextView) findViewById(R.id.tv_title)).setText(getIntent().getStringExtra("categoryName"));
		listView.setEmptyView(tView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				BrandEntity entity = (BrandEntity) arg0.getItemAtPosition(arg2);
				if (null != entity) {
					sendToServerGetUserWealth(entity.getCBrandId(), entity.getCBrangSite());
				}
			}
		});
	}

	@Override
	protected void onResume() {
		initData(categoryIdString, cBrandId);
		super.onResume();
	}

	/**
	 * 用户点击某品牌后给用户增加相应的财富值
	 * 
	 * @param categoryId
	 * @param string
	 */
	protected void sendToServerGetUserWealth(String categoryId, final String imageUrl) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("cBrandId", categoryId);
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.GET_WEALTH_BY_CLICK_BRANCH, params, new AsyncHttpResponseHandler() {
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
					int addValue = commonData.getCommonData().getUserAddWealthValue();
					if ("true".equals(commonData.getCommonData().getReturnStatus()) && addValue > 0) {
						Toast.makeText(mContext, "恭喜，你获得了" + addValue + "个财富值", Toast.LENGTH_SHORT).show();
					}
					Intent itt = new Intent(CategoryListActivity.this, WealthDetailActivity.class);
					itt.putExtra("url", imageUrl);
					startActivity(itt);
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					Intent itt = new Intent(CategoryListActivity.this, WealthDetailActivity.class);
					itt.putExtra("url", imageUrl);
					startActivity(itt);
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}
	}

	private void initData(String categoryIdString2, int cBrandId2) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("categoryId", categoryIdString2);
			params.put("sortType", cBrandId2 + "");
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CATEGORY_BY_ID, params, new AsyncHttpResponseHandler() {
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
						List<BrandEntity> brandEntities = commonData.getBrandMapList();
						listView.setAdapter(new ClassifyListAdapter(mContext, brandEntities));
					} else {
						CRAlertDialog dialog = new CRAlertDialog(mContext);
						dialog.show(commonData.getCommonData().getMsg(), 2000);
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
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rb_default:// 默认
			ivOrderByTime.setVisibility(View.INVISIBLE);
			ivOrderByWealth.setVisibility(View.INVISIBLE);
			cBrandId = 0;
			initData(categoryIdString, cBrandId);
			changeBtnColor(Color.WHITE, R.color.button_normal_color, R.color.button_normal_color);
			changeBtnBackground(R.drawable.left_pressed, R.drawable.middle_normal, R.drawable.right_normal);
			break;
		case R.id.rb_fortune:// 财富排行
			ivOrderByTime.setVisibility(View.INVISIBLE);
			changeBtnBackground(R.drawable.left_normal, R.drawable.middle_pressed, R.drawable.right_normal);
			changeBtnColor(R.color.button_normal_color, Color.WHITE, R.color.button_normal_color);
			if (cBrandId == 1) {
				cBrandId = 2;
				ivOrderByWealth.setImageResource(R.drawable.jiantou_up);
			} else {
				cBrandId = 1;
				ivOrderByWealth.setImageResource(R.drawable.jiantou_down);
			}
			ivOrderByWealth.setVisibility(View.VISIBLE);
			initData(categoryIdString, cBrandId);
			break;
		case R.id.rb_update_time:// 更新时间
			ivOrderByWealth.setVisibility(View.INVISIBLE);
			if (cBrandId == 3) {
				cBrandId = 4;
				ivOrderByTime.setImageResource(R.drawable.jiantou_up);
			} else {
				cBrandId = 3;
				ivOrderByTime.setImageResource(R.drawable.jiantou_down);
			}
			ivOrderByTime.setVisibility(View.VISIBLE);
			initData(categoryIdString, cBrandId);
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
