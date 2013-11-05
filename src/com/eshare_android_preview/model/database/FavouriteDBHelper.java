package com.eshare_android_preview.model.database;

import static com.eshare_android_preview.model.base.Constants.KEY_ID;
import static com.eshare_android_preview.model.base.Constants.TABLE_FAVOURITES;
import static com.eshare_android_preview.model.base.Constants.TABLE_FAVOURITES__ID;
import static com.eshare_android_preview.model.base.Constants.TABLE_FAVOURITES__KIND;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

public class FavouriteDBHelper extends BaseModelDBHelper {

    public static void create(Favourite favourite) {
        SQLiteDatabase db = get_write_db();
        ContentValues values = new ContentValues();
        values.put(TABLE_FAVOURITES__ID, favourite.favourite_id);
        values.put(TABLE_FAVOURITES__KIND, favourite.kind);

        db.insert(TABLE_FAVOURITES, null, values);
        db.close();
    }

    public static void cancel(Favourite favourite) {
        SQLiteDatabase db = get_read_db();
        db.delete(TABLE_FAVOURITES, KEY_ID + " = ?",
                new String[] { String.valueOf(favourite.id) });
        db.close();
    }

    public static Favourite find(String favourite_id, String kind) {
        SQLiteDatabase db = get_read_db();

        String[] whereArgs = new String[] {
                favourite_id + "",
                kind
        };

        Cursor cursor = db.query(Constants.TABLE_FAVOURITES, get_columns(),
                Constants.TABLE_FAVOURITES__ID + "=? and " +
                Constants.TABLE_FAVOURITES__KIND + "= ?",
                whereArgs, null, null, null, null);

        cursor.moveToFirst();
        Favourite favourite = build_by_cursor(cursor);

        cursor.close();
        db.close();

        return favourite;
    }


    private static Favourite build_by_cursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        if(cursor.getCount() >= 1) {
            int id = cursor.getInt(0);
            String _favourite_id = cursor.getString(1);
            String _kind = cursor.getString(2);

            return new Favourite(id, _favourite_id, _kind);
        }

        return null;

    }

    private static String[] get_columns() {
        return new String[]{
                Constants.KEY_ID,
                Constants.TABLE_FAVOURITES__ID,
                Constants.TABLE_FAVOURITES__KIND
        };
    }

    public static List<Favourite> all(){
        SQLiteDatabase db = get_read_db();
        Cursor cursor = db.query(
                Constants.TABLE_FAVOURITES,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
        List<Favourite> favourites = new ArrayList<Favourite>();
        while (cursor.moveToNext()) {
            favourites.add(build_by_cursor(cursor));
        }

        cursor.close();
        db.close();
        return favourites;
    }


}