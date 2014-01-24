package com.eshare_android_preview.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.http.c.UserData;

/**
 * Created by Administrator on 13-12-26.
 */
public class ChangeNetActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.change_net);

        set_head_text("选择课程");
        hide_head_setting_button();

        super.onCreate(savedInstanceState);
    }

    public void open_javascript(View view) {
        UserData.instance().set_current_knowledge_net_id("javascript");
        Intent intent = new Intent(ChangeNetActivity.this, HomeActivity.class);
        intent.putExtra(HomeActivity.ExtraKeys.CHANGE_NET, true);
        startActivity(intent);
        finish();
    }

    public void open_english(View view) {
        UserData.instance().set_current_knowledge_net_id("english");
        Intent intent = new Intent(ChangeNetActivity.this, HomeActivity.class);
        intent.putExtra(HomeActivity.ExtraKeys.CHANGE_NET, true);
        startActivity(intent);
        finish();
    }
}
