package com.warmtel.android.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "newsDB.db";
	public static final int VERSION = 1;
	public static final String ID = "id";
	public Context context;

	public static final String TABLE_CHANNEL = "ChannelItem";// 数据表
	public static final String NAME = "name";//新闻栏目名称
	public static final String ORDERID = "orderId";//
	public static final String SELECTED = "selected";//SELECTED 用来表示是否被选中  0表示没有被选中,1表示被选中
	// 单例模式
	public static DataBaseHelper dbHelper = null;

	private DataBaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context=context;
	}

	public Context getContext() {
		return context;
	}

	public static DataBaseHelper getInstance(Context context) {
		if (dbHelper == null) {
			dbHelper = new DataBaseHelper(context);
		}
		return dbHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table if not exists " + TABLE_CHANNEL
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + ID
				+ " INTEGER , " + NAME + " varchar(30), " + ORDERID
				+ " INTEGER , " + SELECTED + " SELECTED)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
