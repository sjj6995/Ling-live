package com.android.storemanage.activity;

import com.android.storemanage.R;

import android.os.Bundle;
import android.widget.TextView;

public class CommentActivity extends BaseActivity {
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("意见反馈");
	}
}
