package com.eshare_android_preview.model.base;

import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.application.EshareApplication;

abstract public class BaseModelDBHelper {
    final private static BaseDBHelper get_db_helper() {
        return new BaseDBHelper(EshareApplication.context,Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    final public static SQLiteDatabase get_write_db() {
        return get_db_helper().getWritableDatabase();
    }

    final public static SQLiteDatabase get_read_db() {
        return get_db_helper().getReadableDatabase();
    }
}
