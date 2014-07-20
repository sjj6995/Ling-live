package com.android.storemanage.db;

import java.util.ArrayList;
import java.util.List;

import com.android.storemanage.entity.DataSaveEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlDataBase {
	private Context mContext;
	private DBHelper helper;
	private SQLiteDatabase db = null;

	public SqlDataBase(Context mContext) {
		super();
		this.mContext = mContext;
		this.helper = new DBHelper(mContext);
	}

	public List<DataSaveEntity> queryAll(String tableName) {
		List<DataSaveEntity> entitys = new ArrayList<DataSaveEntity>();
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + tableName, null);
		while (cursor.moveToNext()) {
			DataSaveEntity entity = new DataSaveEntity();
			entity.setId(cursor.getString(0)); // 获取第一列的值,第一列的索引从0开始
			entity.setTime(cursor.getString(1));// 获取第三列的值
			entitys.add(entity);
		}
		return entitys;
	}

	public void insertDataSaveEntity(String tableName, DataSaveEntity entity) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + tableName
				+ "where _id =?", new String[] { entity.getId() });
		if (cursor.moveToNext()) {
			db.execSQL("delete from where _id =? ",
					new String[] { entity.getId() });
		}
		ContentValues values = new ContentValues();
		values.put("_id", entity.getId());
		values.put("time", entity.getTime());
		db.insert(tableName, null, values);
	}

}
