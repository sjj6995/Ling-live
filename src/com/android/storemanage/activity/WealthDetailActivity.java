package com.android.storemanage.activity;

import com.android.storemanage.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * @author unknow 财富详情
 * 
 */
public class WealthDetailActivity extends BaseActivity {
	private WebView webView;
	private TextView tView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		tView = (TextView) findViewById(R.id.tv_title);
		tView.setText(getIntent().getStringExtra("title"));
		webView = (WebView) findViewById(R.id.webview);
		String url = getIntent().getStringExtra("url");
		if (!TextUtils.isEmpty(url)) {
			webView.loadUrl(url);
		}

	}

}
