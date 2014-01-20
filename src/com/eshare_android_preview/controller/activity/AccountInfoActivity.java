package com.eshare_android_preview.controller.activity;

import android.os.Bundle;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;

/**
 * Created by Administrator on 14-1-20.
 */
public class AccountInfoActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.account_info);
        set_head_text("我的信息");
        hide_head_setting_button();

        super.onCreate(savedInstanceState);
    }
}
