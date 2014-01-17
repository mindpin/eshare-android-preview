package com.eshare_android_preview.controller.activity;

import android.os.Bundle;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;

public class LoadingActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.base_loading);
		
		// 这里是应用的入口，进入该activity后再根据当前登录状态，进入login或是main
//        TODO cookie 失效的情况没有考虑
        open_activity(is_logged_in() ? HomeActivity.class : LoginActivity.class);
        finish();
	}
}
