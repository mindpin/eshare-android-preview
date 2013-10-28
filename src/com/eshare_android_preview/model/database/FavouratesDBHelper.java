package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Favourates;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

import static com.eshare_android_preview.model.base.Constants.*;

public class FavouratesDBHelper  extends BaseModelDBHelper {
    public static void create(Favourates favourates) {
        SQLiteDatabase db = get_write_db();
        ContentValues values = new ContentValues();
        values.put(TABLE_FAVOURATES__ID, favourates.favourate_id);
        values.put(TABLE_FAVOURATES__KIND, favourates.kind);

        db.insert(TABLE_FAVOURATES, null, values);
        db.close();
    }

    public static void update(Favourates favourates) {
        SQLiteDatabase db = get_write_db();
        ContentValues values = new ContentValues();
        values.put(Constants.TABLE_FAVOURATES__ID, favourates.favourate_id);
        values.put(Constants.TABLE_FAVOURATES__KIND, favourates.kind);
        db.update(Constants.TABLE_FAVOURATES, values, Constants.KEY_ID + " = ? ", new String[]{favourates.id+""});
        db.close();
    }
}