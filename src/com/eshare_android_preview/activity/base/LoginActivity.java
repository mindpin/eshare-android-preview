package com.eshare_android_preview.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eshare_android_preview.R;
import com.eshare_android_preview.Logic.HttpApi;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.ValidateUtil;
import com.eshare_android_preview.model.VersionCheck;

public class LoginActivity extends EshareBaseActivity{
	EditText login_et;
	EditText password_et;
	
	private String login;
    private String password;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_login);
		
		login_et = (EditText) findViewById(R.id.login_et);
        password_et = (EditText) findViewById(R.id.password_et);
	}
	
	public void login_button_click(View view){
		if (!is_params_valid()) return;
		do_login();
	}
	
    //参数检查
    private boolean is_params_valid() {
    	login = login_et.getText().toString();
        password = password_et.getText().toString();
        
        //邮箱，密码不可以空
        if (BaseUtils.is_str_blank(login)) {
            ValidateUtil.setViewError(login_et, R.string.login_email_valid_blank);
            return false;
        }

        if (BaseUtils.is_str_blank(password)) {
            ValidateUtil.setViewError(password_et, R.string.login_password_valid_blank);
            return false;
        }
        return true;
    }
    
    //显示正在登录，并在一个线程中进行登录
    private void do_login() {
    	if (!BaseUtils.is_wifi_active(this)) {
    		BaseUtils.toast(getResources().getString(R.string.is_wifi_active_msg));
    		return;
    	}
    	
    	new BaseAsyncTask<String, Void, VersionCheck>(this, R.string.login_now_login) {
			@Override
			public VersionCheck do_in_background(String... params) throws Exception {
				HttpApi.user_authenticate(login, password);
				String version = getResources().getString(R.string.app_version);
				return null;
			}

			@Override
			public void on_success(VersionCheck result) {
				open_activity(HomeActivity.class);
				finish();
			}
    	}.execute();
    }
}
