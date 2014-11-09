package com.android.storemanage.activity;

import com.android.storemanage.R;
import com.android.storemanage.webview.TestWebView;
import com.android.storemanage.webview.TestWebView.ScrollInterface;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class WebViewActivity1 extends BaseActivity{

	  private TestWebView mTestWebView;
	  private Button btn;
	  private String url,title;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_webview1);
	        url = getIntent().getStringExtra("url");
	        title = getIntent().getStringExtra("title");
	        initWebView();
	    	
	    }
	     
	     
	     
	    //设置WebView
	    @SuppressLint("NewApi")
		private void initWebView() {
	    	btn = (Button) findViewById(R.id.btn);
	        mTestWebView = (TestWebView) findViewById(R.id.webview);
	        mTestWebView.setVerticalScrollBarEnabled(true);
	        mTestWebView.setHorizontalScrollBarEnabled(false);
	        mTestWebView.getSettings().setSupportZoom(true);
	        mTestWebView.getSettings().setBuiltInZoomControls(true);
	        mTestWebView.getSettings().setJavaScriptEnabled(true);
	         
	        mTestWebView.getSettings().setDomStorageEnabled(true);
	        mTestWebView.getSettings().setPluginsEnabled(true);
	        mTestWebView.requestFocus();
	         
	        mTestWebView.getSettings().setUseWideViewPort(true);
	        mTestWebView.getSettings().setLoadWithOverviewMode(true);
	        mTestWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	         
	        mTestWebView.loadUrl(url);
	        mTestWebView.setWebViewClient(new TestWebViewClient());
	        webViewScroolChangeListener();
	        tv_title = (TextView) findViewById(R.id.tv_title);
	        tv_title.setText(title);
	        btn.setVisibility(View.INVISIBLE);
	         
	    }
	     
	    //核心代码
	    private void webViewScroolChangeListener() {
	        mTestWebView.setOnCustomScroolChangeListener(new ScrollInterface() {
	            @Override
	            public void onSChanged(int l, int t, int oldl, int oldt) {
	                //WebView的总高度
	                float webViewContentHeight=mTestWebView.getContentHeight() * mTestWebView.getScale();
	                //WebView的现高度
	                float webViewCurrentHeight=(mTestWebView.getHeight() + mTestWebView.getScrollY());
	                System.out.println("webViewContentHeight="+webViewContentHeight);
	                System.out.println("webViewCurrentHeight="+webViewCurrentHeight);
//	                if ((webViewContentHeight-webViewCurrentHeight) <= 10) {
//	                    System.out.println("WebView滑动到了底端");
//	                    btn.setVisibility(View.VISIBLE);
//	                }else{
//	                	btn.setVisibility(View.INVISIBLE);
//	                }
	            }
	        });
	    }
	     
	 
	    private class TestWebViewClient extends WebViewClient{
	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	            super.onPageStarted(view, url, favicon);
	        }
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return true;
	        }
	        @Override
	        public void onPageFinished(WebView view, String url) {
	            super.onPageFinished(view, url);
	             
	        }
	        @Override
	        public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
	            super.onReceivedError(view, errorCode, description, failingUrl);
	        }
	    }
	    


	public void addWealth(View v){
		
	}
	
		
	    


}
