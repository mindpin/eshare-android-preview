package com.eshare_android_preview.activity.base;

import android.os.Bundle;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

public class LoadingActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.base_loading);
		
		// 这里是应用的入口，进入该activity后再根据当前登录状态，进入login或是main
        open_activity(is_logged_in() ? HomeActivity.class : LoginActivity.class);
        finish();
	}
}
