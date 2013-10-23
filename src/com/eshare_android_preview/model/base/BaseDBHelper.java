package com.eshare_android_preview.model.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDBHelper extends SQLiteOpenHelper {
	
    private static final String create_table_notes = "create table " +
            Constants.TABLE_NOTES + " (" +
            Constants.KEY_ID + " integer primary key autoincrement, " +
            Constants.TABLE_NOTES__QUESTION_ID + " integer not null, " +
            Constants.TABLE_NOTES__CONTENT + " text, " +
            Constants.TABLE_NOTES__IMG + " blob );";
    
    private static final String create_plan = "create table " +
            Constants.TABLE_PLAN + " (" +
            Constants.KEY_ID + " integer primary key, " +
            Constants.TABLE_PLAN__CONTENT + " text, " +
            Constants.TABLE_PLAN__CHECKED + " text default false);";
 
    public BaseDBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {	
        db.execSQL(create_table_notes);
        db.execSQL(create_plan);    
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constants.TABLE_NOTES);
        db.execSQL("drop table if exists " + Constants.TABLE_PLAN);       
        onCreate(db);
    }
}
