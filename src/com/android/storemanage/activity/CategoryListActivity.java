package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.ClassifyListAdapter;
import com.android.storemanage.entity.BrandEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.android.storemanage.view.PullToRefreshListView;
import com.android.storemanage.view.PullToRefreshListView.OnPullDownRefreshListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liujiao
 * 分类列表也即品牌列表
 *
 */
public class CategoryListActivity extends BaseActivity implements OnClickListener {
	private Button rbDefault, rbRankByWealth, rbRankByTime;
	private PullToRefreshListView listView;
	private String categoryIdString;
	private int sortType = 0;
	private TextView tView;
	private ImageView ivOrderByWealth;
	private ImageView ivOrderByTime;
	private ClassifyListAdapter adapter;
	private static final int defaultPageNum = 1;
	private int currentPageNum;
	private int totalPageNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		dm.widthPixels = dm.heightPixels;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_listview_page);
		rbDefault = (Button) findViewById(R.id.rb_default);
		rbRankByWealth = (Button) findViewById(R.id.rb_fortune);
		rbRankByWealth.setText("财富奖励");
//		rbRankByTime = (Button) findViewById(R.id.rb_update_time);
		ivOrderByWealth = (ImageView) findViewById(R.id.iv_order_by_wealth);
//		ivOrderByTime = (ImageView) findViewById(R.id.iv_order_by_time);
		rbDefault.setOnClickListener(this);
		rbRankByWealth.setOnClickListener(this);
//		rbRankByTime.setOnClickListener(this);
		categoryIdString = getIntent().getStringExtra("categoryId");
		listView = (PullToRefreshListView) findViewById(R.id.lv_sport);
		tView = (TextView) findViewById(R.id.tv_no_data);
		((TextView) findViewById(R.id.tv_title)).setText(getIntent().getStringExtra("categoryName"));
		listView.setEmptyView(tView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				BrandEntity entity = (BrandEntity) arg0.getItemAtPosition(arg2);
				if (null != entity) {
					//把数据保存到数据库中
					DataSaveEntity tempDataSaveEntity = new DataSaveEntity();
					tempDataSaveEntity.setId(entity.getCBrandId());
					tempDataSaveEntity.setTime(entity.getCBrandOpptime() + "");
					db.insertDataSaveEntity(JFConfig.BRAND_LIST, tempDataSaveEntity);
//					sendToServerGetUserWealth(entity.getCBrandTitle(),entity.getCBrandId(), entity.getCBrangSite(),entity.getCBrandSfhavedetail());
				    gotoDifferentPageByType(entity);
				    addClickNum(entity.getCBrandId());
				}
			}
		});
		listView.setonRefreshListener(new OnPullDownRefreshListener() {
			@Override
			public void onLoadMoring() {
				if(currentPageNum < totalPageNum){
					initData(categoryIdString, sortType, currentPageNum+1,true);
				}else{
					listView.onRefreshFinish();
				}
			}
		});
	}

	protected void gotoDifferentPageByType(BrandEntity entity) {
		if(!TextUtils.isEmpty(entity.getCBrandXtbpath())){
				Intent itt = new Intent(CategoryListActivity.this,WebViewActivity.class);
				itt.putExtra("url", entity.getCBrandFileoldpath());
				itt.putExtra("title",entity.getCBrandTitle());
				itt.putExtra("id",entity.getCBrandId());
				itt.putExtra("site", entity.getCBrandXtbpath());
				itt.putExtra("detail", entity.getCBrandSfhavedetail());
				itt.putExtra("todaySfAddWealth", entity.getTodaySfAddWealth());
				itt.putExtra("wealthValue", entity.getCBrandWealth()+"");
				itt.putExtra("downLoad", entity.getAppAndroid());
				startActivity(itt);
			
		}
	}

	@Override
	protected void onResume() {
		initData(categoryIdString, sortType,defaultPageNum,false);
		super.onResume();
	}

	public void addClickNum(String brandId){
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("cBrandId", brandId);
			params.put("userId", application.getUserId());
			params.put("useremail", application.getUserEmail());
			params.put(JFConfig.LSH_TOKEN, CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.ADD_NUM, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
					
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
	/**
	 * 用户点击某品牌后给用户增加相应的财富值
	 * 
	 * @param brandId
	 * @param string
	 */
	/*
	protected void sendToServerGetUserWealth(final String brandTitle,String brandId, final String imageUrl,final String type) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("cBrandId", brandId);
			params.put("userId", application.getUserId());
			params.put(JFConfig.LSH_TOKEN, CommonUtil.getMD5(brandId+JFConfig.COMMON_MD5_STR));
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
						Toast.makeText(mContext, "你增加了" + addValue + "财富值", Toast.LENGTH_SHORT).show();
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
*/
	private void initData(String categoryIdString2, int cBrandId2,int pageNum,final boolean loadMore) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("categoryId", categoryIdString2);
			params.put("sortType", cBrandId2 + "");
			params.put("userId", application.getUserId());
			params.put("pageNum", ""+pageNum);
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CATEGORY_BY_ID, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, String content) {
					log.i("content===" + content);
					System.out.println("content====" +content);
					dismissProgressDialog();
					if (TextUtils.isEmpty(content)) {
						return;
					}
					OuterData outerData = JSON.parseObject(content, OuterData.class);
					InnerData innderData = outerData.getData().get(0);
					CollectionData commonData = innderData.getData().get(0);
					log.i("commonData" + commonData.getCommonData().getMsg());
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						totalPageNum = commonData.getCommonData().getTotalPage();
						currentPageNum = commonData.getCommonData().getPageNum();
						List<BrandEntity> brandEntities = commonData.getBrandMapList();
						List<DataSaveEntity> tempEntities = db.queryAll(JFConfig.BRAND_LIST);
						fillData(brandEntities, tempEntities);
						if(null == adapter){
							adapter = new ClassifyListAdapter(mContext, brandEntities);
							listView.setAdapter(adapter);
						}else{
							if(loadMore){
								List<BrandEntity> tempBrandEntities = adapter.getBrandEntities();
								tempBrandEntities.addAll(brandEntities);
								adapter.setBrandEntities(tempBrandEntities);
							}else{
								adapter.setBrandEntities(brandEntities);
							}
							adapter.notifyDataSetChanged();
						}
						listView.onRefreshFinish();
					} else {
//						CRAlertDialog dialog = new CRAlertDialog(mContext);
//						dialog.show(commonData.getCommonData().getMsg(), 2000);
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
	
	protected void fillData(List<BrandEntity> brandEntities, List<DataSaveEntity> tempEntities) {
		if (null != tempEntities && tempEntities.size() > 0) {
			for (int i = 0; i < tempEntities.size(); i++) {
				DataSaveEntity temp = tempEntities.get(i);
				String tempId = temp.getId();
				for (int j = 0; j < brandEntities.size(); j++) {
					BrandEntity entity = brandEntities.get(j);
					String id = entity.getCBrandId();
					if (!TextUtils.isEmpty(tempId) && !TextUtils.isEmpty(id) && id.equals(tempId)) {
						entity.setDbOpptime(Long.parseLong(temp.getTime()));
					}
				}

			}
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rb_default:// 默认
//			ivOrderByTime.setVisibility(View.INVISIBLE);
//			ivOrderByWealth.setVisibility(View.INVISIBLE);
			sortType = 0;
			initData(categoryIdString, sortType,defaultPageNum,false);
			changeBtnColor(Color.WHITE, R.color.button_normal_color);
			changeBtnBackground(R.drawable.left_pressed, R.drawable.right_normal);
			break;
		case R.id.rb_fortune:// 财富排行
//			ivOrderByTime.setVisibility(View.INVISIBLE);
			changeBtnBackground(R.drawable.left_normal, R.drawable.right_pressed);
			changeBtnColor(R.color.button_normal_color, Color.WHITE);
			sortType = 1;
//			ivOrderByWealth.setVisibility(View.VISIBLE);
			initData(categoryIdString, sortType,defaultPageNum,false);
			break;
		default:
			break;

		}
	}

	public void changeBtnBackground(int defaultBg, int secondBg) {
		rbDefault.setBackgroundResource(defaultBg);
		rbRankByWealth.setBackgroundResource(secondBg);
//		rbRankByTime.setBackgroundResource(thridBg);
	}

	public void changeBtnColor(int defaultColor, int secondColor) {
		rbDefault.setTextColor(defaultColor);
		rbRankByWealth.setTextColor(secondColor);
//		rbRankByTime.setTextColor(thridColor);
	}
}
