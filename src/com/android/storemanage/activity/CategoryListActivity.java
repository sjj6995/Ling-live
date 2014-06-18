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
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class CategoryListActivity extends BaseActivity implements OnCheckedChangeListener {
	private RadioButton rbDefault, rbRankByWealth, rbRankByTime;
	private ListView listView;
	private String categoryIdString;
	private int cBrandId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_listview_page);
		rbDefault = (RadioButton) findViewById(R.id.rb_default);
		rbRankByWealth = (RadioButton) findViewById(R.id.rb_fortune);
		rbRankByTime = (RadioButton) findViewById(R.id.rb_update_time);
		rbDefault.setOnCheckedChangeListener(this);
		rbRankByWealth.setOnCheckedChangeListener(this);
		rbRankByTime.setOnCheckedChangeListener(this);
		categoryIdString = getIntent().getStringExtra("categoryId");
		listView = (ListView) findViewById(R.id.lv_sport);
		((TextView) findViewById(R.id.tv_title)).setText(getIntent().getStringExtra("categoryName"));
//		rbDefault.setChecked(true);
		initData(categoryIdString, cBrandId);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                 BrandEntity entity = (BrandEntity) arg0.getItemAtPosition(arg2);
                 if(null != entity){
                	 sendToServerGetUserWealth(entity.getcBrandId(),"");
                 }
			}
		});
	}

	/**
	 * 用户点击某品牌后给用户增加相应的财富值
	 * @param categoryId
	 * @param string
	 */
	protected void sendToServerGetUserWealth(String categoryId, String string) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("cBrandId", categoryId);
			params.put("userId", "11111");
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.GET_WEALTH_BY_CLICK_BRANCH, params, new AsyncHttpResponseHandler() {
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
						int addValue= commonData.getCommonData().getUserAddWealthValue();
						dialog.show("恭喜，你获得了"+addValue+"个财富值", 2000);
					} else {
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

	private void initData(String categoryIdString2, int cBrandId2) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("categoryId", categoryIdString2);
			params.put("sortType", cBrandId2 + "");
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.CATEGORY_BY_ID, params, new AsyncHttpResponseHandler() {
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
