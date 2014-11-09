package com.android.storemanage.db;

import java.util.ArrayList;
import java.util.List;

import com.android.storemanage.entity.DataLimit;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.UserInfo;

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
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + tableName, null);
		while (cursor.moveToNext()) {
			DataSaveEntity entity = new DataSaveEntity();
			entity.setId(cursor.getString(0)); // 获取第一列的值,第一列的索引从0开始
			entity.setTime(cursor.getString(1));// 获取第三列的值
			entitys.add(entity);
		}
		cursor.close();
		return entitys;
	}

	public void insertDataSaveEntity(String tableName, DataSaveEntity entity) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + tableName + " where _id =?", new String[] { entity.getId() });
		if (cursor.moveToNext()) {
			// db.execSQL("delete from where _id =? ", new String[] {
			// entity.getId() });
			db.execSQL("update " + tableName + " set time = ? where _id =? ",
					new String[] { entity.getTime(), entity.getId() });
		} else {
			ContentValues values = new ContentValues();
			values.put("_id", entity.getId());
			values.put("time", entity.getTime());
			db.insert(tableName, null, values);
		}
		cursor.close();
	}
	public DataLimit query(String tableName,DataLimit limit){
		DataLimit temp = new DataLimit();
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " +tableName +" where _id = ?", new String[] {limit.getId()});
		while (cursor.moveToNext()){
			temp.setId(cursor.getString(0));
			temp.setNum(Integer.parseInt(cursor.getString(1)));
			temp.setTime(cursor.getString(2));
			
		}
		cursor.close();
		return temp;
	}
	public void insertZXing(String tableName , DataLimit limit){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+ tableName +" where _id = ?", new String[] { limit.getId()});
		if(cursor.moveToNext()){
			db.execSQL("update "+tableName+" set time = ?,num = ? where _id = ?" ,
					new String[] {limit.getTime(),limit.getNum()+"",limit.getId()});
		} else{
			ContentValues values = new ContentValues();
			values.put("_id", limit.getId());
			values.put("time", limit.getTime());
			values.put("num", limit.getNum());
			db.insert(tableName, null, values);
		}
		cursor.close();
	}
	public UserInfo queryUser(String tableName){
		UserInfo user =new UserInfo();
		db = helper.getWritableDatabase();
		Cursor c = db.rawQuery("select * from "+tableName, null);
		while(c.moveToNext()){
			user.setUserId(c.getString(0));
			user.setUserPwd(c.getString(1));
		}
		c.close();
		return user;
	}

	public void inserIntoUserInfo(String tableName,UserInfo user){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+ tableName +" where user_id = ?", new String[] { user.getUserId()});
		if(cursor.moveToNext()){
			db.execSQL("delete from "+tableName);
		} else{
			ContentValues values = new ContentValues();
			values.put("user_id", user.getUserId());
			values.put("user_pwd", user.getUserPwd());
			db.insert(tableName, null, values);
		}
		cursor.close();
	}
	public void deleteDataSaveEntity(String tableName) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from " + tableName);
	}

}
