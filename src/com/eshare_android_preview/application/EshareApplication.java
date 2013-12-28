package com.eshare_android_preview.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
}
