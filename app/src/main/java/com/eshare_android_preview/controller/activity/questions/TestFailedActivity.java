package com.eshare_android_preview.controller.activity.questions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.utils.ImageTools;
import com.eshare_android_preview.view.ui.UiSound;
import com.eshare_android_preview.view.ui.avatar.CircleAvatarDrawable;

/**
 * Created by fushang318 on 13-12-18.
 */
public class TestFailedActivity extends EshareBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.test_failed);

        load_avatar();
        UiSound.FAIL.start();

        super.onCreate(savedInstanceState);
    }

    public void finish(View view){
        finish();
    }

    private void load_avatar() {
        ImageView iv = (ImageView) findViewById(R.id.avatar);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_ben7th, o);
        b = ImageTools.createBitmapBySize(b, BaseUtils.dp_to_px(30), BaseUtils.dp_to_px(30));
        CircleAvatarDrawable d = new CircleAvatarDrawable(b);
        iv.setBackgroundDrawable(d);
    }
}