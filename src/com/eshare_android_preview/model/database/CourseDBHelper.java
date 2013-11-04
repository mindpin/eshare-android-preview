package com.eshare_android_preview.model.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Course;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

public class CourseDBHelper  extends BaseModelDBHelper{
	public static void create(Course plan) {
		SQLiteDatabase db = get_write_db();
		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_COURSE__CONTENT, plan.content);
		values.put(Constants.TABLE_COURSE__CHECKED, plan.checked+"");
		
		db.insert(Constants.TABLE_COURSE, null, values);
		db.close();
	}
	
	public static void update(Course plan) {
		SQLiteDatabase db = get_write_db();
		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_COURSE__CONTENT, plan.content);
		values.put(Constants.TABLE_COURSE__CHECKED, plan.checked+"");
		db.update(Constants.TABLE_COURSE, values, Constants.KEY_ID + " = ? ", new String[]{plan.id+""});
		db.close();
	}
	
	public static List<Course> all(){
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(
                Constants.TABLE_COURSE,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
		List<Course> plans = new ArrayList<Course>();
        while (cursor.moveToNext()) {
        	plans.add(build_by_cursor(cursor));
        }
        cursor.close();
        db.close();
        return plans;
	}
	public static Course find_by_id(int plan_id) {
		Course plan = null;
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(Constants.TABLE_COURSE, get_columns(),
				Constants.KEY_ID+ " = ? ", 
				new String[]{ plan_id + "" },
				null, null, null);
		boolean has_value = cursor.moveToFirst();
		if(has_value){
			plan = build_by_cursor(cursor);
		}
		cursor.close();
		db.close();
		return plan;
	}
	public static List<Course> find_by_checked(boolean checked) {
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(Constants.TABLE_COURSE,
				get_columns(),
		        Constants.TABLE_COURSE__CHECKED + " = ?",
		        new String[]{checked+""},null, null, null);
		List<Course> plan_list = new ArrayList<Course>();
		while (cursor.moveToNext()) {
			plan_list.add(build_by_cursor(cursor));
		}
		cursor.close();
        db.close();
		return plan_list;
	}
	
	private static Course build_by_cursor(Cursor cursor) {
	    int id = cursor.getInt(0);
	    String content = cursor.getString(1);
	    String checked = cursor.getString(2);
	    boolean boolen_checked = checked.equals("true") ? true:false;
	    return new Course(id, content, boolen_checked);
	}
	
	private static String[] get_columns() {
	    return new String[]{
	        Constants.KEY_ID,
	        Constants.TABLE_COURSE__CONTENT,
	        Constants.TABLE_COURSE__CHECKED
	    };
	 }


}
