package com.eshare_android_preview.http.i;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IDataIcon {
    // URL
    public String get_url();

    // Bitmap
    public Bitmap get_bitmap();
    public Bitmap get_bitmap_remote();
    public Bitmap get_bitmap_local();

    // Drawable
    public Drawable get_drawable();
    public Drawable get_drawable_remote();
    public Drawable get_drawable_local();
}
