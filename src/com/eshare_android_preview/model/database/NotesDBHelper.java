package com.eshare_android_preview.model.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Notes;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

public class NotesDBHelper extends BaseModelDBHelper{

	public static void create(Notes notes) {
		SQLiteDatabase db = get_write_db();
		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_NOTES__NODE_ID, notes.question_id);
		values.put(Constants.TABLE_NOTES__CONTENT, notes.content);
		values.put(Constants.TABLE_NOTES__IMG, notes.img);
		
		db.insert(Constants.TABLE_NOTES, null, values);
		db.close();
	}
	
	public static List<Notes> all(){
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(
                Constants.TABLE_NOTES,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
		List<Notes> blogs = new ArrayList<Notes>();
        while (cursor.moveToNext()) {
        	blogs.add(build_by_cursor(cursor));
//        	System.out.println("all -------------------" + build_by_cursor(cursor).toString());
        }
        cursor.close();
        db.close();
        return blogs;
	}
	
	private static Notes build_by_cursor(Cursor cursor) {
	    int id = cursor.getInt(0);
	    String question_id = cursor.getString(1);
	    String content = cursor.getString(2);
	    byte[] img = cursor.getBlob(3);
	    return new Notes(id,question_id, content, img);
	}
	
	private static String[] get_columns() {
	    return new String[]{
	        Constants.KEY_ID,
	        Constants.TABLE_NOTES__NODE_ID,
	        Constants.TABLE_NOTES__CONTENT,
	        Constants.TABLE_NOTES__IMG
	    };
	 }
}
