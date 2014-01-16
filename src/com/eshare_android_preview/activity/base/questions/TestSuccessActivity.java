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
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;
import com.eshare_android_preview.base.view.ui.UiSound;
import com.eshare_android_preview.base.view.ui.avatar.CircleAvatarDrawable;
import com.eshare_android_preview.http.api.TestSuccessHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.http.model.TestSuccess;
import com.eshare_android_preview.model.TestPaper;

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
    private TestSuccess test_success;

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

        if(!loaded){
            send_http_request();
        }

        super.onCreate(savedInstanceState);
    }

    private void send_http_request() {
        new BaseAsyncTask<Void, Void, Void>(this, R.string.now_loading) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                UserData.instance().get_current_knowledge_net(true);
                String net_id = UserData.instance().get_current_knowledge_net_id();
                String node_or_checkpoint_id = test_paper.node_or_checkpoint_id;
                test_success = TestSuccessHttpApi.build_test_success(net_id, node_or_checkpoint_id);
                return null;
            }

            @Override
            public void on_success(Void result) {
                loaded = true;
                experience_view.refresh();
                experience_chart_view.init(test_success.day_exps);
                handler.sendEmptyMessageDelayed(0, 500);
            }
        }.execute();
    }

    private void load_avatar() {
        ImageView iv = (ImageView) findViewById(R.id.avatar);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;
        byte[] avatar = AccountManager.current_user().avatar;
        Bitmap b = BitmapFactory.decodeByteArray(avatar, 0, avatar.length, o);
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

    private void run_animation() {
        int exp_num = test_success.add_exp_num;
        experience_view.add(exp_num);
        experience_chart_view.run_animation(exp_num);
        findViewById(R.id.added_exp_rl).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.added_exp)).setText("+" + exp_num);
    }

    public void finish(View view) {
        finish();
    }

}