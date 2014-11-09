package com.android.storemanage.view;

import com.android.storemanage.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;


/**
 * 警告对话框
 * @author xindaoapp
 *
 */
public class CRAlertDialog extends Dialog{
	/**
	 * 对话框显示时间，短
	 */
	public static final int SHORT = 2000;
	/**
	 * 对话框显示时间，长
	 */
	public static final int LONG = 4000;
	/**
	 * 永久
	 */
	public static final int FOR_EVER = Integer.MAX_VALUE;
	
	private SpecialTextView msgTv;
	private String msg = new String();

	private int time;
	
	public CRAlertDialog(Context context) {
		super(context, R.style.cr_dialog);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cr_dialog_alert);
		
		msgTv = (SpecialTextView) findViewById(R.id.tv_alert_msg);
		msgTv.setText(msg);
		
		Window window = getWindow();
		window.setWindowAnimations(R.style.anim_dialog_alpha);
		
		new Thread(){
			public void run() {
				try {
					sleep(time);
					dismiss();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 显示对话框
	 * @param txt 对话框文本
	 * @param time 显示时间
	 */
	public void show(String txt, int time){
		this.msg = txt;
		this.time = time;
		show();
	}
}
