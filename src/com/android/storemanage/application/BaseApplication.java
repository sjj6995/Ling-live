package com.android.storemanage.application;

import com.android.storemanage.utils.ExceptionHandler;

import android.app.Application;

public class BaseApplication extends Application {
	private static BaseApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		ExceptionHandler handler = ExceptionHandler.getInstance();
		handler.init(getApplicationContext());
	}

	public static BaseApplication getApplication() {
		return application;
	}
}
