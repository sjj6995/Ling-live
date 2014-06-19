package com.android.storemanage.dialog;

import com.android.storemanage.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class RetryDialog extends Dialog {
	private OnConfirmClick confirmClick;

	public RetryDialog(Context context) {
		super(context);
		init(context);
	}

	public RetryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	public RetryDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	private void init(Context context) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.retry);
		findViewById(R.id.sureBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (confirmClick != null) {
					confirmClick.onClick(v);
				}
				dismiss();
			}

		});

		findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (confirmClick != null) {
					confirmClick.onClick(v);
				}
				dismiss();

			}
		});
	}

	public void setConfirmText(String text) {
		Button btn = (Button) findViewById(R.id.sureBtn);
		if (!TextUtils.isEmpty(text)) {
			btn.setText(text);
		}
	}

	public void setContent(String content) {
		TextView tv = (TextView) findViewById(R.id.content);
		if (!TextUtils.isEmpty(content))
			tv.setText(content);
	}

	public void setCancelText(String text) {
		Button btn = (Button) findViewById(R.id.cancelBtn);
		if (!TextUtils.isEmpty(text)) {
			btn.setText(text);
		}
	}

	public void setOnConfirmClick(OnConfirmClick confirmClick) {
		this.confirmClick = confirmClick;
	}

	public interface OnConfirmClick {
		void onClick(View view);
	}

}
