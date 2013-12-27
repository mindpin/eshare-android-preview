package com.eshare_android_preview.activity.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.knowledge_map.KnowledgeMapView;
import com.eshare_android_preview.base.view.ui.CircleAvatarDrawable;
import com.eshare_android_preview.base.view.ui.CircleImageView;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;


public class HomeActivity extends EshareBaseActivity {
    public static String course;
    public static KnowledgeMapView map_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home);

        course = KnowledgeNet.get_current_net().get_course();

        load_map_view();
        load_avatar();
        init_exp_view();

        super.onCreate(savedInstanceState);
    }

    private void load_map_view() {
        map_view = (KnowledgeMapView) findViewById(R.id.knowledge_map_view);
        map_view.init(this);
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

    private void init_exp_view() {
        ExperienceView ev = (ExperienceView) findViewById(R.id.experience_view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void change_course(View view) {
        Intent intent = new Intent(this, ChangeCourseActivity.class);
        startActivity(intent);
    }
}
