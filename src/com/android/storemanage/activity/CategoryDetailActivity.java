package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.BrandDetailEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryDetailActivity extends BaseActivity {
	private ImageView iView;
	private TextView title;
	private TextView downloadTextView;
	private TextView tvDescTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_detail);
		String brandIdString = getIntent().getStringExtra("id");
		if (TextUtils.isEmpty(brandIdString)) {
			Toast.makeText(this, R.string.server_data_exception, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		iView = (ImageView) findViewById(R.id.iv_icon);
		title = (TextView) findViewById(R.id.tv_category_name);
		downloadTextView = (TextView) findViewById(R.id.tv_download);
		tvDescTextView = (TextView) findViewById(R.id.tv_message_desc);
		((TextView) (findViewById(R.id.tv_title))).setText("品牌详情");
		initData(brandIdString);
	}

	private void initData(String cBrandId) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("cBrandId", cBrandId);
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CATEGORY_DETAIL, params,
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
							BrandDetailEntity entity = commonData
									.getBrandDetailMap();
							fillData(entity);
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

	protected void fillData(final BrandDetailEntity entity) {
		if (null != entity) {
			Picasso.with(mContext)
					.load(JFConfig.HOST_URL + entity.getFileImgpath())
					.placeholder(R.drawable.img_empty).into(iView);

			title.setText(entity.getCBrandTitle());
			if (TextUtils.isEmpty(entity.getCBrandXzdz())) {
				downloadTextView.setVisibility(View.INVISIBLE);
			} else {
				downloadTextView.setVisibility(View.VISIBLE);
				downloadTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						Uri content_url = Uri.parse(entity.getCBrandXzdz());
						intent.setData(content_url);
						startActivity(intent);
					}
				});
			}
			tvDescTextView.setText(entity.getCBrandDetail());
		}

	}

}
