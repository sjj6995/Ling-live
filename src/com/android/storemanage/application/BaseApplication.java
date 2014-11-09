package com.android.storemanage.application;

import java.util.ArrayList;
import java.util.List;

import com.android.storemanage.utils.ExceptionHandler;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class BaseApplication extends Application {
	private static BaseApplication application;
	private SharedPreferences sp;
	private String userId = "";
	private String userEmail = "";
	public static List<Activity> list;

	public String getUserId() {
		if(TextUtils.isEmpty(userId)){
			sp = getSharedPreferences("userinfor", MODE_PRIVATE);
			userId = sp.getString("userId", "");
		}
		return userId;
	}
	
	public String getUserEmail() {
		if(TextUtils.isEmpty(userEmail)){
			sp = getSharedPreferences("userinfor", MODE_PRIVATE);
			userEmail = sp.getString("email", "");
		}
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
		list = new ArrayList<Activity>();
		sp = getSharedPreferences("userinfor", MODE_PRIVATE);
		userId = sp.getString("userId", "");
		userEmail = sp.getString("email", "");
		setUserId(userId);
		setUserEmail(userEmail);

	}


	public static BaseApplication getApplication() {
		return application;
	}
	public void addActivity1(Activity a){
		list.add(a);
	}
}
