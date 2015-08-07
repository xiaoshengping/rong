package com.example.administrator.iclub21.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLhelper extends SQLiteOpenHelper{
	private static final String DBNAME="iclub.db";
	public static final String tableName="user";
	public static final String UID="uid";
	public static final String PERSONID="personId";
	public static final String USERNAME="userName";
	public static final String USERICON="userIcon";
	public static final String STSTE="state";
	public static final String MOBILE="mobile";
	public static final String COMPANYNAME="companyName";
	private static final int VERSION=1;

	public SQLhelper(Context context
			) {
		super(context, DBNAME, null, VERSION);
	}

	public void onCreate(SQLiteDatabase db){
//		db.execSQL("create table if not exists "+tableName+"("+ID+" integer primary key,"+NAME+" varchar,"
//				+ATT+" integer,"+AGI+" integer,"+INT+" integer)");		
		db.execSQL("create table if not exists user(uid varchar pr imary key,personId varchar,userName varchar,userIcon varchar,state varchar,mobile varchar ,companyName varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists"+tableName);
		onCreate(db);
	}

}
