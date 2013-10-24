package com.eshare_android_preview.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.LoginActivity;

public class EshareBaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivitiesStackSingleton.tidy_and_push_activity(this);

//        如果存在顶栏，加载顶栏并进行一些必要设置
        _load_head_bar();
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

    private void _load_head_bar() {
//        RelativeLayout head_bar_rl = (RelativeLayout) findViewById(R.id.head_bar);
//
//        System.out.print(head_bar_rl);
//
//        if (null != head_bar_rl) {
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

            Button go_back_button = (Button) findViewById(R.id.button_go_back);
            go_back_button.setTypeface(font);

            Button setting_button = (Button) findViewById(R.id.button_setting);
            setting_button.setTypeface(font);
//        }
    }
}
