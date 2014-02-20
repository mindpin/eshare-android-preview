package com.eshare_android_preview.view.ui;

import android.graphics.Typeface;

import com.eshare_android_preview.EshareApplication;

/**
 * Created by Administrator on 13-12-29.
 */
public class UiFont {
    final static public Typeface FONTAWESOME_FONT = Typeface.createFromAsset(
            EshareApplication.context.getAssets(),
            "fonts/fontawesome-webfont.ttf");
}
