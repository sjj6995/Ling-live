package com.android.storemanage.activity;

import com.android.storemanage.R;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {
	private TextView title;//868856010904114

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("关于我们");
	}
}
