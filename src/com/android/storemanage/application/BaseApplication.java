package com.android.storemanage.application;

import com.android.storemanage.utils.ExceptionHandler;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class BaseApplication extends Application {
	private static BaseApplication application;
	private SharedPreferences sp;
	private String userId = "";
	

	public String getUserId() {
		if(TextUtils.isEmpty(userId)){
			sp = getSharedPreferences("userinfor", MODE_PRIVATE);
			userId = sp.getString("userId", "");
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		ExceptionHandler handler = ExceptionHandler.getInstance();
		handler.init(getApplicationContext());
		sp = getSharedPreferences("userinfor", MODE_PRIVATE);
		userId = sp.getString("userId", "");
		setUserId(userId);

	}


	public static BaseApplication getApplication() {
		return application;
	}
}
