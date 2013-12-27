package com.eshare_android_preview.model.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDBHelper extends SQLiteOpenHelper {
	
    private static final String create_experience_logs = "create table " +
            Constants.TABLE_EXPERIENCE_LOGS + " (" +
            Constants.KEY_ID + " integer primary key, " +
            Constants.TABLE_EXPERIENCE_LOGS__BEFORE_EXP + " integer not null, " +
            Constants.TABLE_EXPERIENCE_LOGS__AFTER_EXP + " integer not null, " +
            Constants.TABLE_EXPERIENCE_LOGS__MODEL_TYPE + " text, " +
            Constants.TABLE_EXPERIENCE_LOGS__MODEL_ID + " text, " +
            Constants.TABLE_EXPERIENCE_LOGS__DATA_JSON + " text, " +
            Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT + " long);";


    public BaseDBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {	
        db.execSQL(create_experience_logs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constants.TABLE_EXPERIENCE_LOGS);

        onCreate(db);
    }
}
