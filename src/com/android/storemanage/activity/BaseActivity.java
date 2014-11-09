package com.android.storemanage.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.application.BaseApplication;
import com.android.storemanage.db.SqlDataBase;
import com.android.storemanage.utils.CommonLog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseActivity extends Activity {
	protected BaseApplication application;
	protected Context mContext;
	protected CommonLog log;
	protected static SharedPreferences sp;
	protected SqlDataBase db;
	protected Button register,changedevice; 
	protected TextView tv_title;
	protected ImageButton back_login;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		application = BaseApplication.getApplication();
		mContext = this;
		db = new SqlDataBase(mContext);
		sp = getSharedPreferences("userinfor", MODE_PRIVATE);
		log = CommonLog.getInstance();
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
