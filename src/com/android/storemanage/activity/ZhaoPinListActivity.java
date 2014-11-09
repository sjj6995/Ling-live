package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.ClassifyListAdapter;
import com.android.storemanage.adapter.ZhaoPinListAdapter;
import com.android.storemanage.entity.BrandEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.ZhaoPin;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liujiao
 * 分类列表也即品牌列表
 *
 */
public class ZhaoPinListActivity extends BaseActivity implements OnClickListener {
	private Button rbDefault, rbRankByWealth, rbRankByTime;
	private PullToRefreshListView listView;
	private String categoryIdString;
	private int sortType = 0;
	private TextView tView;
	private ImageView ivOrderByWealth;
	private ImageView ivOrderByTime;
	private ZhaoPinListAdapter adapter;
	private static final int defaultPageNum = 1;
	private int currentPageNum;
	private int totalPageNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhaopin_listview_page);
		listView = (PullToRefreshListView) findViewById(R.id.zp_sport);
		tView = (TextView) findViewById(R.id.zp_no_data);
		((TextView) findViewById(R.id.tv_title)).setText(getIntent().getStringExtra("categoryName"));
		listView.setEmptyView(tView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ZhaoPin entity = (ZhaoPin) arg0.getItemAtPosition(arg2);
				if (null != entity) {
					//把数据保存到数据库中
					DataSaveEntity tempDataSaveEntity = new DataSaveEntity();
					tempDataSaveEntity.setId(entity.getId());
					tempDataSaveEntity.setTime(entity.getZpOpptime() + "");
					db.insertDataSaveEntity(JFConfig.ZHAOPIN_LIST, tempDataSaveEntity);
//					sendToServerGetUserWealth(entity.getCBrandTitle(),entity.getCBrandId(), entity.getCBrangSite(),entity.getCBrandSfhavedetail());
				    gotoDifferentPageByType(entity);
				}
			}
		});
		listView.setonRefreshListener(new OnPullDownRefreshListener() {
			@Override
			public void onLoadMoring() {
				if(currentPageNum < totalPageNum){
					initData( currentPageNum+1,true);
				}else{
					listView.onRefreshFinish();
				}
			}
		});
	}

	protected void gotoDifferentPageByType(ZhaoPin entity) {
		if(!TextUtils.isEmpty(entity.getWebpath())){
				Intent itt = new Intent(ZhaoPinListActivity.this,ZhaoPinWebViewActivity1.class);
				itt.putExtra("url", entity.getWebpath());
				itt.putExtra("tel", entity.getZpLxdh());
				startActivity(itt);
			
		}
	}

	@Override
	protected void onResume() {
		initData(defaultPageNum,false);
		super.onResume();
	}

	private void initData(int pageNum,final boolean loadMore) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("useremail", application.getUserEmail());
			params.put("userId", application.getUserId());
			params.put("pageNum", ""+pageNum);
			params.put("lshToken", CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.ZHAOPIN_BY_ID, params, new AsyncHttpResponseHandler() {

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
						List<ZhaoPin> zhaopinEntities = commonData.getRecruitMapList();
						List<DataSaveEntity> tempEntities = db.queryAll(JFConfig.ZHAOPIN_LIST);
						fillData(zhaopinEntities, tempEntities);
						if(null == adapter){
							adapter = new ZhaoPinListAdapter(mContext, zhaopinEntities);
							listView.setAdapter(adapter);
						}else{
							if(loadMore){
								List<ZhaoPin> tempBrandEntities = adapter.getZhaopinEntities();
								tempBrandEntities.addAll(zhaopinEntities);
								adapter.setZhaopinEntities(tempBrandEntities);
							}else{
								adapter.setZhaopinEntities(zhaopinEntities);
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
	
	protected void fillData(List<ZhaoPin> brandEntities, List<DataSaveEntity> tempEntities) {
		if (null != tempEntities && tempEntities.size() > 0) {
			for (int i = 0; i < tempEntities.size(); i++) {
				DataSaveEntity temp = tempEntities.get(i);
				String tempId = temp.getId();
				for (int j = 0; j < brandEntities.size(); j++) {
					ZhaoPin entity = brandEntities.get(j);
					String id = entity.getId();
					if (!TextUtils.isEmpty(tempId) && !TextUtils.isEmpty(id) && id.equals(tempId)) {
						entity.setDbTime(Long.parseLong(temp.getTime()));
					}
				}

			}
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
