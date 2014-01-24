package com.eshare_android_preview.controller.activity;

import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.utils.BaseUtils;

public class AuthActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.auth);

        if (BaseUtils.net_is_active(this)) {
            load_app();
        } else {
            show_no_network();
        }

        super.onCreate(savedInstanceState);
    }

    private void load_app() {
        new BaseAsyncTask<Void, Void, Void>() {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                UserData.instance().refresh();
                return null;
            }

            @Override
            public void on_success(Void result) {
                if (is_logged_in()) {
                    open_activity(HomeActivity.class);
                    finish();
                    return;
                }

                show_auth_view();
            }
        }.execute();
    }

    private void show_auth_view() {
        findViewById(R.id.loading_message_view).setVisibility(View.GONE);
        findViewById(R.id.auth_form).setVisibility(View.VISIBLE);
    }

    private void show_no_network() {
        BaseUtils.toast("网络连接失败");
    }
}
