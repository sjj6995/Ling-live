package com.android.storemanage.activity;

import com.android.storemanage.R;

import android.os.Bundle;
import android.webkit.WebView;

/**
 * @author unknow 财富详情
 * 
 */
public class WealthDetailActivity extends BaseActivity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		webView = (WebView) findViewById(R.id.webview);
	}
}
