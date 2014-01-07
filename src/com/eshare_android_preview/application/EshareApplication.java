package com.eshare_android_preview.application;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshare_android_preview.model.database.ExperienceLogDBHelper;
import com.eshare_android_preview.model.preferences.EsharePreference;

public class EshareApplication extends Application{
	public static Context context;
    public static LayoutInflater mInflater;

    public static View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return mInflater.inflate(resource, root, attachToRoot);
    }

    public static View inflate(int resource, ViewGroup root) {
        return mInflater.inflate(resource, root);
    }
    @Override
    public void onCreate() {
    	context = getApplicationContext();
        mInflater = LayoutInflater.from(context);
    }

    public static void clear_data(){
        EsharePreference.clear_data();
        ExperienceLogDBHelper.clear_data();
    }
}
