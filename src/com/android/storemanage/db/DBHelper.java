package com.android.storemanage.db;

import com.android.storemanage.utils.JFConfig;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "storemanage.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + JFConfig.T_USER + " (user_id TEXT primary key,user_pwd TEXT)");
		db.execSQL("create table " + JFConfig.BRAND_LIST + " (_id TEXT primary key,time TEXT)");// 品牌列表
		db.execSQL("create table " + JFConfig.CATEGORY_LIST + " (_id TEXT primary key,time TEXT)");// 分类列表
		db.execSQL("create table " + JFConfig.ZHAOPIN_LIST + " (_id TEXT primary key,time TEXT)");// 招聘列表
		db.execSQL("create table " + JFConfig.FRONT_PAGE + " (_id TEXT primary key,time TEXT)");// 首页
		db.execSQL("create table " + JFConfig.MESSAGE_LIST + " (_id TEXT primary key,time TEXT)");// 消息列表
		db.execSQL("create table " + JFConfig.PRIZE_LIST + " (_id TEXT primary key,time TEXT)");//
		db.execSQL("create table " + JFConfig.PWD_LIMIT + " (_id TEXT primary key , num INTEGER,time TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 try {
			 db.execSQL("drop table if exists "+JFConfig.BRAND_LIST);
			 db.execSQL("drop table if exists "+JFConfig.CATEGORY_LIST);
			 db.execSQL("drop table if exists "+JFConfig.ZHAOPIN_LIST);
			 db.execSQL("drop table if exists "+JFConfig.FRONT_PAGE);
			 db.execSQL("drop table if exists "+JFConfig.MESSAGE_LIST);
			 db.execSQL("drop table if exists "+JFConfig.PRIZE_LIST);
			 db.execSQL("drop table if exists "+JFConfig.PWD_LIMIT);
			 db.execSQL("drop table if exists "+JFConfig.T_USER);
			 onCreate(db);
			 } catch (SQLException e) {
			 e.printStackTrace();
			 } 
	}
	

}
