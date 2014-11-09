package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserInfo;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;
import com.android.storemanage.webview.TestWebView;
import com.android.storemanage.webview.TestWebView.ScrollInterface;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author likai 分类页面 详情
 * 
 */
@SuppressLint("NewApi")
public class WebViewActivity extends BaseActivity {

	private TestWebView mTestWebView;
	private Button btn, btn_down;
	private LinearLayout layout_btn;
	private String url, title, id, detail, site, todaySfAddWealth, wealthValue,
			downLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		id = getIntent().getStringExtra("id");
		site = getIntent().getStringExtra("site");
		detail = getIntent().getStringExtra("detail");
		wealthValue = getIntent().getStringExtra("wealthValue");
		todaySfAddWealth = getIntent().getStringExtra("todaySfAddWealth");
		downLoad = getIntent().getStringExtra("downLoad");
		initWebView();

	}

	public void addWealth(View v) {
		sendToServerGetUserWealth(id);
	}

	// 设置WebView
	@SuppressLint("NewApi")
	private void initWebView() {
		btn = (Button) findViewById(R.id.btn);
		mTestWebView = (TestWebView) findViewById(R.id.webview);
		mTestWebView.setVerticalScrollBarEnabled(true);
		mTestWebView.setHorizontalScrollBarEnabled(false);
		mTestWebView.getSettings().setSupportZoom(false);
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
		layout_btn = (LinearLayout) findViewById(R.id.layout_btn);
		btn.setText("增加" + wealthValue + "值");
		if ("".equals(downLoad)) {
			layout_btn.setVisibility(View.GONE);
		}
		mTestWebView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float webViewContentHeight = mTestWebView.getContentHeight()
						* mTestWebView.getScale();
				// WebView的现高度
				float webViewCurrentHeight = (mTestWebView.getHeight() + mTestWebView
						.getScrollY());
				if (webViewContentHeight == webViewCurrentHeight) {
					btn.setVisibility(View.VISIBLE);
					if ("true".equals(todaySfAddWealth)) {
						btn.setText("成功增加" + wealthValue + "财富值");
						btn.setEnabled(false);
						
						Log.v("TouchME", webViewContentHeight+","+webViewCurrentHeight);
					}

				}
				return false;
			}
		});

	}

	// 核心代码
	private void webViewScroolChangeListener() {
		mTestWebView.setOnCustomScroolChangeListener(new ScrollInterface() {
			@Override
			public void onSChanged(int l, int t, int oldl, int oldt) {
				// WebView的总高度
				float webViewContentHeight = mTestWebView.getContentHeight()
						* mTestWebView.getScale();
				// WebView的现高度
				float webViewCurrentHeight = (mTestWebView.getHeight() + mTestWebView
						.getScrollY());
				System.out.println("webViewContentHeight="
						+ webViewContentHeight);
				System.out.println("webViewCurrentHeight="
						+ webViewCurrentHeight);
				if ((webViewContentHeight - webViewCurrentHeight)<=10) {
					System.out.println("WebView滑动到了底端");
					btn.setVisibility(View.VISIBLE);
					if ("true".equals(todaySfAddWealth)) {
						btn.setEnabled(false);
						btn.setText("成功增加" + wealthValue + "财富值");
						Log.v("Scroller", webViewContentHeight+","+webViewCurrentHeight);
					} else {
						btn.setEnabled(true);
					}
				} else {
					btn.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	private class TestWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onPageFinished(WebView view, String url) {
			float i = view.getContentHeight() * view.getScale();
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}

	protected void sendToServerGetUserWealth(String brandId) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("cBrandId", brandId);
			params.put("userId", application.getUserId());
			params.put("useremail", application.getUserEmail());
			params.put(
					JFConfig.LSH_TOKEN,
					CommonUtil.getMD5(application.getUserEmail()
							+ JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.ADD_WEALTH, params,
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
							int addValue = commonData.getCommonData()
									.getAddWealthValue();
							if (!"true".equals(commonData.getCommonData()
									.getReturnStatus()) && addValue > 0) {
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								dialog.show("系统错误请提示", 2000);
							} else if (!"true".equals(commonData
									.getCommonData().getSfEnough())) {
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								dialog.show("商家财富值不足", 2000);
							} else if (!"true".equals(commonData
									.getCommonData().getClicks())) {
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								dialog.show("您今天点击加财富次数已用完", 2000);
							} else if (!"true".equals(commonData
									.getCommonData().getAddWealthSuccess())) {
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								dialog.show("财富值增加失败", 2000);
							} else {
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								dialog.show("您增加了" + addValue + "财富值", 2000);
								btn.setEnabled(false);
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

	@Override
	public void onBackClicked(View view) {

		super.onBackClicked(view);
	}

	public void downLoad(View v) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("useremail", application.getUserEmail());
			params.put("phonetype", "android");
			params.put("cBrandId", id);
			params.put(
					JFConfig.LSH_TOKEN,
					CommonUtil.getMD5(application.getUserEmail()
							+ JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.DOWN, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							dismissProgressDialog();
							if (TextUtils.isEmpty(content)) {
								return;
							}

						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
						}
					});
			Uri url = Uri.parse(downLoad);
			Intent t = new Intent(Intent.ACTION_VIEW, url);
			startActivity(t);
		}

	}

}
