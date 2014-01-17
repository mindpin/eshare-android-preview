package com.eshare_android_preview.model.database.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDBHelper extends SQLiteOpenHelper {
	
	private static final String create_table_account_users = "create table " +
            Constants.TABLE_ACCOUNT_USERS + " (" +
            Constants.KEY_ID + " integer primary key autoincrement, " +
            Constants.TABLE_ACCOUNT_USERS__USER_ID + " integer not null, " +
            Constants.TABLE_ACCOUNT_USERS__NAME + " text not null, " +
            Constants.TABLE_ACCOUNT_USERS__LOGIN + " text , " +
            Constants.TABLE_ACCOUNT_USERS__EMAIL + " text , " +
            Constants.TABLE_ACCOUNT_USERS__AVATAR_URL + " text, " +
            Constants.TABLE_ACCOUNT_USERS__COOKIES + " text not null, " +
            Constants.TABLE_ACCOUNT_USERS__INFO + " text not null);";
	
    public BaseDBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {	
    	db.execSQL(create_table_account_users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constants.TABLE_ACCOUNT_USERS);
        onCreate(db);
    }
}
