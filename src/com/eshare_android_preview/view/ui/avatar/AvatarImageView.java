package com.eshare_android_preview.view.ui.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.eshare_android_preview.utils.ImageTools;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;

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

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;

        byte[] avatar = AccountManager.current_user().avatar;
        Bitmap b = BitmapFactory.decodeByteArray(avatar, 0, avatar.length, o);

        b = ImageTools.createBitmapBySize(b, getWidth(), getHeight());
        CircleAvatarDrawable d = new CircleAvatarDrawable(b);

        setBackgroundDrawable(d);
    }
}
