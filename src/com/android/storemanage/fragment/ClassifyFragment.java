package com.android.storemanage.fragment;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.activity.CategoryListActivity;
import com.android.storemanage.adapter.ClassifyLittleAdapter;
import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author liujiao 类别
 * 
 */
public class ClassifyFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	private ImageButton imageButton;
	private TextView titleTextView;
	private CommonLog log = CommonLog.getInstance();
	private ListView listView;
	private ClassifyLittleAdapter adapter;
	private TextView tvNoData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_classify, null);
		initViews(view);
		return view;
	}

	@Override
	public void onResume() {
		initData();
		super.onResume();
	}
	
	private void initData() {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CLASSIFY_LIST, params, new AsyncHttpResponseHandler() {

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
						List<CategoryEntity> msgEntity = innderData.getData().get(0).getCategoryMapList();
						if(null == adapter){
							adapter = new ClassifyLittleAdapter(getActivity(), msgEntity);
							listView.setAdapter(adapter);
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
					CommonUtil.onFailure(arg0, getActivity());
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(getActivity());
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	private void initViews(View view) {
		imageButton = (ImageButton) view.findViewById(R.id.ib_back);
		imageButton.setVisibility(View.INVISIBLE);
		titleTextView = (TextView) view.findViewById(R.id.tv_title);
		titleTextView.setText("分类");
		tvNoData = (TextView) view.findViewById(R.id.tv_no_data);
		listView = (ListView) view.findViewById(R.id.lv_classify);
		listView.setEmptyView(tvNoData);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		CategoryEntity entity = (CategoryEntity) arg0.getItemAtPosition(arg2);
		if (null != entity) {
			Intent intent = new Intent(getActivity(), CategoryListActivity.class);
			intent.putExtra("categoryId", entity.getCategoryId());
			intent.putExtra("categoryName", entity.getCategoryTitle());
			startActivity(intent);
		}
	}
}