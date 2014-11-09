package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.ZhaoPinListAdapter;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.ZhaoPin;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.android.storemanage.webview.TestWebView;
import com.android.storemanage.webview.TestWebView.ScrollInterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ZhaoPinWebViewActivity1 extends BaseActivity{

	  private TestWebView mTestWebView;
	  private Button btn;
	  private String url,title,tel;
	  private int flag = 0;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zhaopin_activity_webview);
	        url = getIntent().getStringExtra("url");
	        title = getIntent().getStringExtra("title");
	        tel = getIntent().getStringExtra("tel");
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
	        mTestWebView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					  //WebView的总高度
		            float webViewContentHeight=mTestWebView.getContentHeight() * mTestWebView.getScale();
		            //WebView的现高度
		            float webViewCurrentHeight=(mTestWebView.getHeight());
		            if(webViewContentHeight <= webViewCurrentHeight){
		            	 btn.setText("联系人电话:"+tel.replace(tel.substring(3, 7), "****"));
		                 btn.setVisibility(View.VISIBLE);
		            }
		             
					return false;
				}
			});
	         
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
	                if ((webViewContentHeight-webViewCurrentHeight) <= 10) {
	                    System.out.println("WebView滑动到了底端");
	                    btn.setText("联系人电话:"+tel.replace(tel.substring(3, 7), "****"));
	                    btn.setVisibility(View.VISIBLE);
	                }else{
	                	btn.setVisibility(View.INVISIBLE);
	                }
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
		if(flag >=1){
			Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));
			startActivity(i);
		}else{
			if (CommonUtil.checkNetState(mContext)) {
				RequestParams params = new RequestParams();
				params.put("useremail", application.getUserEmail());
				params.put("userId", application.getUserId());
				params.put("lshToken", CommonUtil.getMD5(application.getUserEmail()+JFConfig.COMMON_MD5_STR));
				showProgressDialog(R.string.please_waiting);
				HttpClient.post(JFConfig.REDUCE, params, new AsyncHttpResponseHandler() {
	
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
							 btn.setText("联系人电话:"+tel);
							CRAlertDialog dialog = new CRAlertDialog(mContext);
							dialog.show("手机号查看，减去一财富值", 2000);
							flag++;
						} else {
							CRAlertDialog dialog = new CRAlertDialog(mContext);
							dialog.show(commonData.getCommonData().getMsg(), 2000);
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
	}
	
		
	    


}
