package com.eshare_android_preview.model.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eshare_android_preview.application.EshareApplication;

/**
 * Created by menxu on 13-11-18.
 */
public class EsharePreference {
    public static final SharedPreferences  PREFERENCES = PreferenceManager.getDefaultSharedPreferences(EshareApplication.context);

    public static void put_boolean(String key_name, boolean value){
        SharedPreferences.Editor editor = PREFERENCES.edit();
        editor.putBoolean(key_name,value);
        editor.commit();
    }

    public static boolean get_value(String key){
        return PREFERENCES.getBoolean(key,false);
    }

}
