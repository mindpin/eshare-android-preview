package com.eshare_android_preview.http.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.eshare_android_preview.http.i.profile.IUserAvatar;

/**
 * Created by Administrator on 14-1-20.
 */
public class UserAvatar implements IUserAvatar {
    private String url;

    public UserAvatar(String url){
        this.url = url;
    }

    @Override
    public String get_url() {
        return null;
    }

    @Override
    public Bitmap get_bitmap() {
        return null;
    }

    @Override
    public Bitmap get_bitmap_remote() {
        return null;
    }

    @Override
    public Bitmap get_bitmap_local() {
        return null;
    }

    @Override
    public Drawable get_drawable() {
        return null;
    }

    @Override
    public Drawable get_drawable_remote() {
        return null;
    }

    @Override
    public Drawable get_drawable_local() {
        return null;
    }

    @Override
    public String get_large_url() {
        return this.url.replace("normal","large");
    }
}
