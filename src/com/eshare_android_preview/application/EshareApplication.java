package com.eshare_android_preview.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshare_android_preview.model.database.ExperienceLogDBHelper;
import com.eshare_android_preview.model.preferences.EsharePreference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);


    }

    public static void clear_data(){
        EsharePreference.clear_data();
        ExperienceLogDBHelper.clear_data();
    }
}
