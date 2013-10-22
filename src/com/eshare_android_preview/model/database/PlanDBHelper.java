package com.eshare_android_preview.model.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

public class PlanDBHelper  extends BaseModelDBHelper{
	public static void create(Plan plan) {
		SQLiteDatabase db = get_write_db();
		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_PLAN__CONTENT, plan.content);
		values.put(Constants.TABLE_PLAN__CHECKED, plan.checked);
		
		db.insert(Constants.TABLE_PLAN, null, values);
		db.close();
	}
	
	public static List<Plan> all(){
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(
                Constants.TABLE_PLAN,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
		List<Plan> plans = new ArrayList<Plan>();
        while (cursor.moveToNext()) {
        	plans.add(build_by_cursor(cursor));
        }
        cursor.close();
        db.close();
        return plans;
	}
	
	public static List<Plan> find_by_checked(String string) {
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(Constants.TABLE_PLAN, 
				get_columns(),
		        Constants.TABLE_PLAN__CHECKED + " = ?", 
		        new String[]{string},null, null, null);
		List<Plan> plan_list = new ArrayList<Plan>();
		while (cursor.moveToNext()) {
			plan_list.add(build_by_cursor(cursor));
		}
		cursor.close();
        db.close();
		return plan_list;
	}
	
	private static Plan build_by_cursor(Cursor cursor) {
	    int id = cursor.getInt(0);
	    String content = cursor.getString(1);
	    String checked = cursor.getString(2);
	    return new Plan(id, content, checked);
	}
	
	private static String[] get_columns() {
	    return new String[]{
	        Constants.KEY_ID,
	        Constants.TABLE_PLAN__CONTENT,
	        Constants.TABLE_PLAN__CHECKED
	    };
	 }
}
