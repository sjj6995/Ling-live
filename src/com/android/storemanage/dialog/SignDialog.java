package com.android.storemanage.dialog;

import com.android.storemanage.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SignDialog extends Dialog {
	private OnConfirmClick1 confirmClick1;
	private TextView content_sign;
	private String tip = "";
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public SignDialog(Context context,String tip) {
		super(context);
		this.tip = tip;
		init(context);
	}
	public SignDialog(Context context) {
		super(context);
		init(context);
	}

	public SignDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	public SignDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	private void init(Context context) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.signdialog);
		content_sign = (TextView) findViewById(R.id.content_sign);
		if(!"".equals(tip)){
			content_sign.setText(tip.toString());
		}
		findViewById(R.id.sureBtn_sign).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (confirmClick1 != null) {
					confirmClick1.onClick(v);
				}
				dismiss();
			}

		});

	}

	public void setConfirmText(String text) {
		Button btn = (Button) findViewById(R.id.sureBtn_sign);
		if (!TextUtils.isEmpty(text)) {
			btn.setText(text);
		}
	}

	public void setContent(String content) {
		TextView tv = (TextView) findViewById(R.id.content_sign);
		if (!TextUtils.isEmpty(content))
			tv.setText(content);
	}

	public void setOnConfirmClick(OnConfirmClick1 confirmClick) {
		this.confirmClick1 = confirmClick;
	}

	public interface OnConfirmClick1 {
		void onClick(View view);
	}

}
