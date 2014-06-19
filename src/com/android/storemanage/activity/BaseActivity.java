package com.android.storemanage.activity;

import com.android.storemanage.R;
import com.android.storemanage.application.BaseApplication;
import com.android.storemanage.utils.CommonLog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class BaseActivity extends Activity {
	protected BaseApplication application;
	protected Context mContext;
	protected CommonLog log;
	protected DisplayImageOptions options;
	protected SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		application = BaseApplication.getApplication();
		mContext = this;
		sp = getSharedPreferences(RegisterActivity.class.getSimpleName(), MODE_PRIVATE);
		log = CommonLog.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_empty)
				.showImageForEmptyUri(R.drawable.img_empty).showImageOnFail(R.drawable.img_empty).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(20)).build();
	}

	private Dialog progressDialog;

	public final void showProgressDialog(int msgId) {
		showProgressDialog(msgId, false);
	}

	public final void showProgressDialog(final int msgId, final boolean cancelable) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog == null || !progressDialog.isShowing()) {
					progressDialog = new Dialog(BaseActivity.this);
					progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					progressDialog.setContentView(R.layout.progress_dialog);
					progressDialog.setCancelable(cancelable);
					progressDialog.setCanceledOnTouchOutside(false);
					if (cancelable) {
						progressDialog.setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
							}
						});
					}
				}
				progressDialog.show();
			}
		});
	}

	public final void showProgressDialog(final int msgId, final boolean cancelable, final int theme) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog == null || !progressDialog.isShowing()) {
					progressDialog = new Dialog(BaseActivity.this);
					progressDialog.setContentView(R.layout.progress_dialog);
					TextView content = (TextView) progressDialog.findViewById(R.id.content);
					content.setText(msgId);
					progressDialog.setCancelable(cancelable);
					progressDialog.setCanceledOnTouchOutside(false);
					if (cancelable) {
						progressDialog.setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
							}
						});
					}
				}
			}
		});
	}

	public final void dismissProgressDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}
		});
	}

	public void onBackClicked(View view) {
		finish();
	}
}
