package com.android.storemanage.activity;

import com.android.storemanage.R;
import com.android.storemanage.utils.CommonUtil;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {
	private TextView title;//868856010904114
	private TextView version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		title = (TextView) findViewById(R.id.tv_title);
		version = (TextView) findViewById(R.id.version);
		title.setText("关于");
		version.setText(getResources().getString(R.string.app_name)+" v"+CommonUtil.getVersion(mContext));
	}
}
