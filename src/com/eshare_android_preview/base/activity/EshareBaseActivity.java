package com.eshare_android_preview.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.activity.base.LoginActivity;

public class EshareBaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivitiesStackSingleton.tidy_and_push_activity(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivitiesStackSingleton.remove_activity(this);
	}
	
	// 关闭所有activity，并重新打开login
	final public void restart_to_login(){
		ActivitiesStackSingleton.clear_activities_stack();
		open_activity(LoginActivity.class);
	}

	// 绑定在顶栏 go_back 按钮上的事件处理
	final public void go_back(View view) {
		on_go_back();
		this.finish();
	}

	// 打开一个新的activity，此方法用来简化调用
	final public void open_activity(Class<?> cls) {
		startActivity(new Intent(getApplicationContext(), cls));
	}
	
	// 钩子，自行重载
	public void on_go_back() {
	};
}
