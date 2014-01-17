package com.eshare_android_preview.view.ui.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.utils.ImageTools;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * Created by Administrator on 14-1-13.
 */
public class AvatarImageView extends ImageView {
    private boolean loaded = false;

    public AvatarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!loaded) load();
    }

    private void load() {
        if (isInEditMode()) return;
        loaded = true;

        String url = AccountManager.current_user().avatar_url;

        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Bitmap b = ImageTools.createBitmapBySize(loadedImage, getWidth(), getHeight());
                CircleAvatarDrawable d = new CircleAvatarDrawable(b);
                setBackgroundDrawable(d);
            }
        });
    }
}
