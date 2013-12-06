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

        db.insert(Constants.TABLE_EXPERIENCE_LOGS, null, values);
        db.close();
    }

    public static List<ExperienceLog> all(){
        SQLiteDatabase db = get_read_db();
        Cursor cursor = db.query(
                Constants.TABLE_EXPERIENCE_LOGS,
                get_columns()
                , null, null, null, null,
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

    public static ExperienceLog find_last_data() {
        ExperienceLog elog = null;
        SQLiteDatabase db = get_read_db();

        String sql = "select * from " + Constants.TABLE_EXPERIENCE_LOGS + " order by " + Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT + " desc  limit 0,1";

        Cursor cursor = db.rawQuery(sql ,get_columns());
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

        return new ExperienceLog(id,before_exp,after_exp,model_type,model_id,data_json,created_at);
    }

    private static String[] get_columns() {
        return new String[]{
            Constants.KEY_ID,
            Constants.TABLE_EXPERIENCE_LOGS__BEFORE_EXP,
            Constants.TABLE_EXPERIENCE_LOGS__AFTER_EXP,
            Constants.TABLE_EXPERIENCE_LOGS__MODEL_TYPE,
            Constants.TABLE_EXPERIENCE_LOGS__MODEL_ID,
            Constants.TABLE_EXPERIENCE_LOGS__DATA_JSON,
            Constants.TABLE_EXPERIENCE_LOGS_CREATED_AT
        };
    }
}
