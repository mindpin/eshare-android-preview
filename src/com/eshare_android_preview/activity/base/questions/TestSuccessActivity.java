package com.eshare_android_preview.activity.base.questions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;
import com.eshare_android_preview.base.view.ui.CircleAvatarDrawable;
import com.eshare_android_preview.base.view.ui.UiSound;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.http.model.TestPaperTarget;

/**
 * Created by fushang318 on 13-12-18.
 */
public class TestSuccessActivity extends EshareBaseActivity {
    private boolean loaded = false;

    public static class ExtraKeys {
        public static final String TEST_PAPER = "test_paper";
    }

    public TestPaper test_paper;

    private ExperienceView experience_view;
    private ExperienceChartView experience_chart_view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            run_animation();
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("loaded", loaded);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            loaded = savedInstanceState.getBoolean("loaded");
        }

        setContentView(R.layout.test_success);
        test_paper = getIntent().getParcelableExtra(ExtraKeys.TEST_PAPER);

        load_avatar();
        init_experience_view();
        init_chart_view();

        UiSound.SUCCESS.start();

        super.onCreate(savedInstanceState);
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

    private void init_experience_view() {
        experience_view = (ExperienceView) findViewById(R.id.experience_view);
    }

    private void init_chart_view() {
        experience_chart_view = (ExperienceChartView) findViewById(R.id.chart_view);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        handler.sendEmptyMessageDelayed(0, 500);
    }

    private void run_animation() {
        if (!loaded) {
//            TODO 待集成
//            TestPaperTarget target = test_paper.target;
//
//            int exp_num = target.do_learn();
//
//            experience_view.add(exp_num);
//            experience_chart_view.run_animation(exp_num);
//
//            findViewById(R.id.added_exp_rl).setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.added_exp)).setText("+" + exp_num);
//
//            loaded = true;
        }
    }

    public void finish(View view) {
        finish();
    }

}