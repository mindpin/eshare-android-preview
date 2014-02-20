package com.eshare_android_preview.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.activeandroid.query.Select;
import com.eshare_android_preview.http.model.AccountUser;

public class AccountUserDBHelper{
    final public static boolean save(AccountUser account_user) {
        try {
            account_user.save();
            return true;
        }catch(Exception e){
            e.getStackTrace();
            System.out.println(e.getMessage());
            return false;
        }

    }

    final public static AccountUser find(int user_id) {
        return new Select().from(AccountUser.class).where("user_id = ?", user_id).orderBy("RANDOM()").executeSingle();
    }

    public static void destroy(AccountUser account_user) {
        account_user.delete();
    }
}
