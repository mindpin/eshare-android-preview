package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.eshare_android_preview.model.base.Constants.*;

public class FavouratesDBHelper  extends BaseModelDBHelper {

    public static class Kinds {
        public static final String QUESTION = "com.eshare_android_preview.model.Question";
        public static final String NODE = "com.eshare_android_preview.model.Node";
        public static final String PLAN = "com.eshare_android_preview.model.Plan";
        public static final String Favourate = "com.eshare_android_preview.model.Favourate";
    }


    public static void create(Favourate favourate) {
        SQLiteDatabase db = get_write_db();
        ContentValues values = new ContentValues();
        values.put(TABLE_FAVOURATES__ID, favourate.favourate_id);
        values.put(TABLE_FAVOURATES__KIND, favourate.kind);

        db.insert(TABLE_FAVOURATES, null, values);
        db.close();
    }

    public static void update(Favourate favourate) {
        SQLiteDatabase db = get_write_db();
        ContentValues values = new ContentValues();
        values.put(Constants.TABLE_FAVOURATES__ID, favourate.favourate_id);
        values.put(Constants.TABLE_FAVOURATES__KIND, favourate.kind);
        db.update(Constants.TABLE_FAVOURATES, values, Constants.KEY_ID + " = ? ", new String[]{favourate.id+""});
        db.close();
    }

    public static void cancel(Favourate favourate) {
        SQLiteDatabase db = get_read_db();
        db.delete(TABLE_FAVOURATES, KEY_ID + " = ?",
                new String[] { String.valueOf(favourate.id) });
        db.close();
    }

    public static Favourate find(int favourate_id, String kind) {
        SQLiteDatabase db = get_write_db();

        String[] tableColumns = get_columns();

        String[] whereArgs = new String[] {
                favourate_id + "",
                kind
        };

        Cursor cursor = db.query(Constants.TABLE_FAVOURATES, get_columns(),
                Constants.TABLE_FAVOURATES__ID + "=? and " +
                Constants.TABLE_FAVOURATES__KIND + "= ?",
                whereArgs, null, null, null, null);

        cursor.moveToFirst();
        Favourate favourate = build_by_cursor(cursor);

        cursor.close();
        db.close();

        return favourate;
    }


    private static Favourate build_by_cursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        if(cursor.getCount() >= 1) {
            int id = cursor.getInt(0);
            Integer _favourate_id = cursor.getInt(1);
            String _kind = cursor.getString(2);

            return new Favourate(id, _favourate_id, _kind);
        }

        return null;

    }

    private static String[] get_columns() {
        return new String[]{
                Constants.KEY_ID,
                Constants.TABLE_FAVOURATES__ID,
                Constants.TABLE_FAVOURATES__KIND
        };
    }

    public static List<Favourate> all(){
        SQLiteDatabase db = get_read_db();
        Cursor cursor = db.query(
                Constants.TABLE_FAVOURATES,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
        List<Favourate> favourates = new ArrayList<Favourate>();
        while (cursor.moveToNext()) {
            favourates.add(build_by_cursor(cursor));
        }

        cursor.close();
        db.close();
        return favourates;
    }


}