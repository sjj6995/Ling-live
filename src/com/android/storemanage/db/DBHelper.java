package com.android.storemanage.db;

import com.android.storemanage.utils.JFConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "storemanage.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + JFConfig.BRAND_LIST + " (_id TEXT primary key,time TEXT)");// 品牌列表
		db.execSQL("create table " + JFConfig.FRONT_PAGE + " (_id TEXT primary key,time TEXT)");// 首页
		db.execSQL("create table " + JFConfig.MESSAGE_LIST + " (_id TEXT primary key,time TEXT)");// 消息列表
		db.execSQL("create table " + JFConfig.PRIZE_LIST + " (_id TEXT primary key,time TEXT)");//
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
