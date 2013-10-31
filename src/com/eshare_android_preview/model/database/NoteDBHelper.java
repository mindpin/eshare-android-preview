package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshare_android_preview.model.Note;
import com.eshare_android_preview.model.base.BaseModelDBHelper;
import com.eshare_android_preview.model.base.Constants;

import java.util.ArrayList;
import java.util.List;

public class NoteDBHelper extends BaseModelDBHelper{

	public static void create(Note note) {
		SQLiteDatabase db = get_write_db();
		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_NOTES__TYPE, note.type);
		values.put(Constants.TABLE_NOTES__TYPE_ID, note.type_id);
		values.put(Constants.TABLE_NOTES__CONTENT, note.content);
		values.put(Constants.TABLE_NOTES__IMG, note.img);
		
		db.insert(Constants.TABLE_NOTES, null, values);
		db.close();
	}
	
	public static List<Note> all(){
		SQLiteDatabase db = get_read_db();
		Cursor cursor = db.query(
                Constants.TABLE_NOTES,
                get_columns()
                , null, null, null, null,
                Constants.KEY_ID + " ASC"
        );
		List<Note> blogs = new ArrayList<Note>();
        while (cursor.moveToNext()) {
        	blogs.add(build_by_cursor(cursor));
//        	System.out.println("all -------------------" + build_by_cursor(cursor).toString());
        }
        cursor.close();
        db.close();
        return blogs;
	}

    public static Boolean has_note_from(String notable_id, String notable_type) {
        SQLiteDatabase db = NoteDBHelper.get_read_db();
        String[] columns = new String[] {
            Constants.TABLE_NOTES__TYPE_ID,
            Constants.TABLE_NOTES__TYPE
        };
        String[] whereArgs = new String[] {
            notable_id,
            notable_type
        };
        String whereClause = Constants.TABLE_NOTES__TYPE_ID + " = ? AND " + Constants.TABLE_NOTES__TYPE + " = ?";
        Cursor cursor = db.query(Constants.TABLE_NOTES, columns, whereClause, whereArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

	private static Note build_by_cursor(Cursor cursor) {
	    int id = cursor.getInt(0);
	    String type = cursor.getString(1);
	    String type_id = cursor.getString(2);
	    String content = cursor.getString(3);
	    byte[] img = cursor.getBlob(4);
	    return new Note(id, type, type_id, content, img);
	}
	
	private static String[] get_columns() {
	    return new String[]{
	        Constants.KEY_ID,
	        Constants.TABLE_NOTES__TYPE,
	        Constants.TABLE_NOTES__TYPE_ID,
	        Constants.TABLE_NOTES__CONTENT,
	        Constants.TABLE_NOTES__IMG
	    };
	 }
}
