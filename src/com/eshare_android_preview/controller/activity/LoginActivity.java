package com.eshare_android_preview.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.utils.ValidateUtil;

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
    	
    	new BaseAsyncTask<String, Void, Void>(this, R.string.login_now_login) {
			@Override
			public Void do_in_background(String... params) throws Exception {
				UserAuthHttpApi.user_authenticate(login, password);
				return null;
			}

			@Override
			public void on_success(Void result) {
				open_activity(HomeActivity.class);
				finish();
			}

            @Override
            public void on_authenticate_exception() {
//                TODO 用户名或密码错误
            }

            @Override
            public void on_http_host_connect_exception() {
//                TODO 网络不给力
            }

            @Override
            public void on_response_not_200_exception() {
//                TODO 非 200 非 401 的网络请求
            }

            @Override
            public boolean on_unknown_exception() {
//               TODO 返回 false 时，不显示默认的 TOAST 提示
//                do_in_background 方法内出现异常时，会调用这个方法
                return false;
            }
        }.execute();
    }
}
