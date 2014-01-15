package com.eshare_android_preview.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.LoginActivity;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.http.model.AccountUser;

public class EshareBaseActivity extends Activity {
    /**
     * 此方法务必要在子类的 onCreate 中 layout 加载后再调用
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ActivitiesStackSingleton.tidy_and_push_activity(this);
		super.onCreate(savedInstanceState);
	}
	
	final public boolean is_logged_in() {
		return AccountManager.is_logged_in();
	}

	final public AccountUser current_user() {
		return AccountManager.current_user();
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

    // 绑定在顶栏 setting 按钮上的事件处理
    final public void setting(View view) {
        on_setting();
    }

    // 钩子，自行重载
    public void on_go_back() {
    }

    ;

    // 钩子，自行重载
    public void on_setting() {
    }

    ;

    // 打开一个新的activity，此方法用来简化调用
    final public void open_activity(Class<?> cls) {
        startActivity(new Intent(getApplicationContext(), cls));
    }

    // 隐藏顶部的回退按钮
    public void hide_head_go_back_button() {
        try {
            findViewById(R.id.button_go_back).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    // 隐藏顶部的设置按钮
    public void hide_head_setting_button() {
        try {
            findViewById(R.id.button_setting).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    // 隐藏顶栏底边
    public void hide_head_bottom_line() {
        try {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.head_bottom_line);
            rl.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    // 设置顶部文字
    public void set_head_text(CharSequence text) {
        try {
            TextView tv = (TextView) findViewById(R.id.head_bar_title_tv);
            tv.setText(text);
        } catch (Exception e) {
        }
    }

    // 设置顶部文字
    public void set_head_text(int res_id) {
        try {
            TextView tv = (TextView) findViewById(R.id.head_bar_title_tv);
            tv.setText(res_id);
        } catch (Exception e) {
        }
    }

    private Typeface fontawesome_font;

    public void set_fontawesome(TextView tv) {
        if (null == fontawesome_font) {
            fontawesome_font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        }
        tv.setTypeface(fontawesome_font);
    }
}
