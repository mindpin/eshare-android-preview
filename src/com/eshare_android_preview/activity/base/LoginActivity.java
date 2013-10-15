package com.eshare_android_preview.activity.base;

import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.tab_activity.MainTabActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

public class LoginActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_login);
	}
	
	public void login_button_click(View view){
		open_activity(MainTabActivity.class);
	}
	
	public void register_button_click(View view){
		
	}
}
