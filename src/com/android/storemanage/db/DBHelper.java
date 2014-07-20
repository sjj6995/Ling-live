package com.android.storemanage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "storemanage.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table  BrandList(_id TEXT primary key,time TEXT");
		db.execSQL("create table  FrontPage(_id TEXT NOT NULL,time TEXT");
		db.execSQL("create table  MessageList(_id TEXT NOT NULL,time TEXT");
		db.execSQL("create table  PrizeList(_id TEXT NOT NULL,time TEXT");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
