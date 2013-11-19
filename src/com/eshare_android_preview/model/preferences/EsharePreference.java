package com.eshare_android_preview.model.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eshare_android_preview.application.EshareApplication;

import java.util.List;

/**
 * Created by menxu on 13-11-18.
 */
public class EsharePreference {
    public static final SharedPreferences  PREFERENCES = PreferenceManager.getDefaultSharedPreferences(EshareApplication.context);

    public static void put_learned_array(List<String> id_s){
        SharedPreferences.Editor editor = PREFERENCES.edit();
        for (String str :  id_s){
            editor.putBoolean(str,true);
        }
        editor.commit();
    }

    public static void put_learned(String key_name, boolean value){
        SharedPreferences.Editor editor = PREFERENCES.edit();
        editor.putBoolean(key_name,value);
        editor.commit();
    }

    public static boolean get_learned(String key){
        return PREFERENCES.getBoolean(key,false);
    }
    public static void cleare_data(){
        SharedPreferences.Editor editor =  PREFERENCES.edit();
        editor.clear();
        editor.commit();
    }
}
