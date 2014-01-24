package com.eshare_android_preview.controller.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.profile.IUserProfile;

/**
 * Created by Administrator on 14-1-20.
 */
public class AccountInfoActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.account_info);
        set_head_text("我的信息");
        hide_head_setting_button();

        init();

        super.onCreate(savedInstanceState);
    }

    private void init() {
        IUserProfile p = UserData.instance().get_profile(false);

        ((TextView) findViewById(R.id.user_name)).setText(p.get_name());
        ((TextView) findViewById(R.id.user_email)).setText(p.get_email());
    }
}
