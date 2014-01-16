package com.eshare_android_preview.http.logic.user_auth;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.eshare_android_preview.R;
import com.eshare_android_preview.EshareApplication;

public class UserAuthPreferences {
	public static final SharedPreferences PREFERENCES = PreferenceManager
			.getDefaultSharedPreferences(EshareApplication.context);

	public static void put_int(String key_name, int value) {
		Editor pre_edit = PREFERENCES.edit();
		pre_edit.putInt(key_name, value);
		pre_edit.commit();
	}

	public static void put_long(String key_name, long value) {
		Editor pre_edit = PREFERENCES.edit();
		pre_edit.putLong(key_name, value);
		pre_edit.commit();
	}

	public static void put_boolean(String key_name, boolean value) {
		Editor pre_edit = PREFERENCES.edit();
		pre_edit.putBoolean(key_name, value);
		pre_edit.commit();
	}

	public static void put_string(String key_name, String value) {
		Editor pre_edit = PREFERENCES.edit();
		pre_edit.putString(key_name, value);
		pre_edit.commit();
	}

	public static String get_resource_string(int resource_id) {
		return EshareApplication.context.getResources().getString(resource_id);
	}

	public static void set_current_user_id(int id) {
		String key = get_resource_string(R.string.preferences_key_current_user_id);
		UserAuthPreferences.put_int(key, id);
	}

	public static int current_user_id() {
		String key = get_resource_string(R.string.preferences_key_current_user_id);
		return PREFERENCES.getInt(key, 0);
	}
}
