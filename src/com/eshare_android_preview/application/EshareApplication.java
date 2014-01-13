package com.eshare_android_preview.application;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

import com.eshare_android_preview.model.preferences.EsharePreference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class EshareApplication extends Application{
	public static Context context;
    public static LayoutInflater mInflater;

    @Override
    public void onCreate() {
    	context = getApplicationContext();
        mInflater = LayoutInflater.from(context);

        init_image_loader();
    }

    private void init_image_loader() {
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
    }
}
