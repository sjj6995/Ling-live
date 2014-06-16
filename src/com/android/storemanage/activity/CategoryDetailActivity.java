package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class CategoryDetailActivity extends BaseActivity implements
		OnCheckedChangeListener {
	private RadioButton rbDefault, rbRankByWealth, rbRankByTime;
	private ListView listView;
	private String categoryIdString;
	private int cBrandId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sport_page);
		rbDefault = (RadioButton) findViewById(R.id.rb_default);
		rbRankByWealth = (RadioButton) findViewById(R.id.rb_fortune);
		rbRankByTime = (RadioButton) findViewById(R.id.rb_update_time);
		rbDefault.setOnCheckedChangeListener(this);
		rbRankByWealth.setOnCheckedChangeListener(this);
		rbRankByTime.setOnCheckedChangeListener(this);
		categoryIdString = getIntent().getStringExtra("categoryId");
		listView = (ListView) findViewById(R.id.lv_sport);
		((TextView) findViewById(R.id.tv_title)).setText(getIntent()
				.getStringExtra("categoryName"));
		initData(categoryIdString, cBrandId);
	}

	private void initData(String categoryIdString2, int cBrandId2) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("categoryId", categoryIdString2);
			params.put("sortType", cBrandId2 + "");
			XDHttpClient.get(JFConfig.CATEGORY_BY_ID, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							OuterData outerData = JSON.parseObject(content,
									OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData()
									.get(0);
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							// List<CategoryEntity> msgEntity = innderData
							// .getData().get(0).getCategoryMapList();
							// listView.setAdapter(new ClassifyAdapter(
							// getActivity(), msgEntity));
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
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
			cBrandId = 0;
			initData(categoryIdString, cBrandId);
			break;
		case R.id.rb_fortune:// 财富排行
			if (cBrandId == 1) {
				cBrandId = 2;
			} else {
				cBrandId = 1;
			}
			initData(categoryIdString, cBrandId);
			break;
		case R.id.rb_update_time:// 更新时间
			if (cBrandId == 3) {
				cBrandId = 4;
			} else {
				cBrandId = 3;
			}
			initData(categoryIdString, cBrandId);
			break;

		default:
			break;
		}

	}
}
