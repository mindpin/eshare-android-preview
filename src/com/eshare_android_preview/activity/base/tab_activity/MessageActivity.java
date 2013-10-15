package com.eshare_android_preview.activity.base.tab_activity;

import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

public class MessageActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_message);
	}
	public void on_click_hard_right(View view){
		
	}
}
