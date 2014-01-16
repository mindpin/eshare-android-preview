package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eshare_android_preview.http.model.AccountUser;
import com.eshare_android_preview.model.database.base.BaseModelDBHelper;
import com.eshare_android_preview.model.database.base.Constants;

public class AccountUserDBHelper extends BaseModelDBHelper{
	// 保存
    final public static boolean save(final AccountUser account_user) {
        if (account_user == null) return false;
        SQLiteDatabase db = get_write_db();

        try {
            // 保存数据库信息
            ContentValues values = new ContentValues();
            values.put(Constants.TABLE_ACCOUNT_USERS__USER_ID, account_user.user_id);
            values.put(Constants.TABLE_ACCOUNT_USERS__NAME, account_user.name);
            values.put(Constants.TABLE_ACCOUNT_USERS__LOGIN, account_user.login);
            values.put(Constants.TABLE_ACCOUNT_USERS__EMAIL, account_user.email);
            values.put(Constants.TABLE_ACCOUNT_USERS__COOKIES, account_user.cookies);
            values.put(Constants.TABLE_ACCOUNT_USERS__INFO, account_user.info);
            values.put(Constants.TABLE_ACCOUNT_USERS__AVATAR,account_user.avatar);
           
            AccountUser o_user = find(account_user.user_id);
           
            if (o_user.is_nil()) {
                db.insert(Constants.TABLE_ACCOUNT_USERS, null, values);
            } else {
                db.update(Constants.TABLE_ACCOUNT_USERS, values, Constants.TABLE_ACCOUNT_USERS__USER_ID + " = " + account_user.user_id, null);
            }
            return true;
        } catch (Exception e) {
            Log.e("AccountUserDBHelper", "save", e);
            return false;
        } finally {
            db.close();
        }
    }
    
    final public static AccountUser find(int user_id) {
        SQLiteDatabase db = get_read_db();
     
        try {
            Cursor cursor = db.query(
                    Constants.TABLE_ACCOUNT_USERS, 
                    get_columns(),
                    Constants.TABLE_ACCOUNT_USERS__USER_ID + " = " + user_id,
                    null, null, null, null
            );
            if (cursor.moveToFirst()) {
                return build_by_cursor(cursor);
            } else {
                return AccountUser.NIL_ACCOUNT_USER;
            }
        } catch (Exception e) {
            Log.e("AccountUserDBHelper", "find", e);
            return AccountUser.NIL_ACCOUNT_USER;
        } finally {
            db.close();
        }
    }
	
	private static AccountUser build_by_cursor(Cursor cursor) {
		int user_id = cursor.getInt(1);
		String name = cursor.getString(2);
		String login = cursor.getString(3);
		String email = cursor.getString(4);
		byte[] avatar = cursor.getBlob(5);
		String cookies = cursor.getString(6);
		String info = cursor.getString(7);

		return new AccountUser(cookies, info, user_id, name,login,email, avatar);
	}

	private static String[] get_columns() {
		return new String[] { Constants.KEY_ID,
                Constants.TABLE_ACCOUNT_USERS__USER_ID,
                Constants.TABLE_ACCOUNT_USERS__NAME,
                Constants.TABLE_ACCOUNT_USERS__LOGIN,
                Constants.TABLE_ACCOUNT_USERS__EMAIL,
                Constants.TABLE_ACCOUNT_USERS__AVATAR,
                Constants.TABLE_ACCOUNT_USERS__COOKIES,
                Constants.TABLE_ACCOUNT_USERS__INFO};
	}

	public static boolean destroy(AccountUser account_user) {
		if (account_user.is_nil()) return false;
        SQLiteDatabase db = get_write_db();
        
        try {
			db.delete(Constants.TABLE_ACCOUNT_USERS, 
					Constants.TABLE_ACCOUNT_USERS__USER_ID + " = ?",
					new String[]{account_user.user_id+""});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
