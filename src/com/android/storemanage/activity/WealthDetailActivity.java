package com.android.storemanage.activity;

import com.android.storemanage.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

	}

}
