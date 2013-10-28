package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Favourates;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Favourates> all(){
        SQLiteDatabase db = get_read_db();
        Cursor cursor = db.query(
                Constants.TABLE_FAVOURATES,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
        List<Favourates> favourates = new ArrayList<Favourates>();
        while (cursor.moveToNext()) {
            favourates.add(build_by_cursor(cursor));
        }
        cursor.close();
        db.close();
        return favourates;
    }

    private static Favourates build_by_cursor(Cursor cursor) {
        int id = cursor.getInt(0);
        Integer favourate_id = cursor.getInt(1);
        String kind = cursor.getString(2);
        return new Favourates(id, favourate_id, kind);
    }

    private static String[] get_columns() {
        return new String[]{
                Constants.KEY_ID,
                Constants.TABLE_FAVOURATES__ID,
                Constants.TABLE_FAVOURATES__KIND
        };
    }
}