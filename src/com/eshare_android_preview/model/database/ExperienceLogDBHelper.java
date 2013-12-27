package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.elog.ExperienceLog;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menxu on 13-12-5.
 */
public class ExperienceLogDBHelper extends BaseModelDBHelper{

    public static void create(ExperienceLog elog) {
        SQLiteDatabase db = get_write_db();
        ContentValues values = new ContentValues();
        values.put(Constants.TABLE_EXPERIENCE_LOGS__BEFORE_EXP,elog.before_exp);
        values.put(Constants.TABLE_EXPERIENCE_LOGS__AFTER_EXP, elog.after_exp);
        values.put(Constants.TABLE_EXPERIENCE_LOGS__MODEL_TYPE,elog.model_type);
        values.put(Constants.TABLE_EXPERIENCE_LOGS__MODEL_ID,  elog.model_id);
        values.put(Constants.TABLE_EXPERIENCE_LOGS__DATA_JSON, elog.data_json);
        values.put(Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT, elog.created_at);
        values.put(Constants.TABLE_EXPERIENCE_LOGS_COURSE, elog.course);

        db.insert(Constants.TABLE_EXPERIENCE_LOGS, null, values);
        db.close();
    }

    public static List<ExperienceLog> all(String coruse){
        SQLiteDatabase db = get_read_db();
        Cursor cursor = db.query(
                Constants.TABLE_EXPERIENCE_LOGS,
                get_columns(),
                Constants.TABLE_EXPERIENCE_LOGS_COURSE + " = ?",
                new String[]{coruse},
                null, null,
                Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT + " ASC"
        );
        List<ExperienceLog> elogs = new ArrayList<ExperienceLog>();
        while (cursor.moveToNext()) {
            elogs.add(build_by_cursor(cursor));
        }
        cursor.close();
        db.close();
        return elogs;
    }

    public static ExperienceLog find_last_data(String course) {
        ExperienceLog elog = null;
        SQLiteDatabase db = get_read_db();

        String sql = "select * from " + Constants.TABLE_EXPERIENCE_LOGS + " where " + Constants.TABLE_EXPERIENCE_LOGS_COURSE + " = '" + course + "' order by " + Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT + " desc  limit 0,1";
        System.out.println("find_last_data_sql = " + sql);
        Cursor cursor = db.rawQuery(sql ,null);
        boolean has_value = cursor.moveToFirst();
        if(has_value){
            elog = build_by_cursor(cursor);
        }
        cursor.close();
        db.close();
        return elog;
    }

    private static ExperienceLog build_by_cursor(Cursor cursor) {
        int id = cursor.getInt(0);
        int before_exp = cursor.getInt(1);
        int after_exp = cursor.getInt(2);
        String model_type = cursor.getString(3);
        String model_id = cursor.getString(4);
        String data_json = cursor.getString(5);
        long created_at = cursor.getLong(6);
        String course = cursor.getString(7);

        return new ExperienceLog(id,before_exp,after_exp,model_type,model_id,data_json,created_at,course);
    }

    private static String[] get_columns() {
        return new String[]{
            Constants.KEY_ID,
            Constants.TABLE_EXPERIENCE_LOGS__BEFORE_EXP,
            Constants.TABLE_EXPERIENCE_LOGS__AFTER_EXP,
            Constants.TABLE_EXPERIENCE_LOGS__MODEL_TYPE,
            Constants.TABLE_EXPERIENCE_LOGS__MODEL_ID,
            Constants.TABLE_EXPERIENCE_LOGS__DATA_JSON,
            Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT,
            Constants.TABLE_EXPERIENCE_LOGS_COURSE,
        };
    }
}
