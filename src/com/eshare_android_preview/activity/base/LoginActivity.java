package com.eshare_android_preview.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.tab_activity.MainTabActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;

public class LoginActivity extends EshareBaseActivity{
	private String email;
    private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_login);
	}
	
	public void login_button_click(View view){
		open_activity(MainTabActivity.class);
//		prepare_email_and_password();
//        if (is_params_valid()) {
//            do_login();
//        }
	}
	
	public void register_button_click(View view){
		
	}
	
    //获取邮箱，密码字符串。作准备。
    private void prepare_email_and_password() {
        EditText email_et = (EditText) findViewById(R.id.email_et);
        EditText password_et = (EditText) findViewById(R.id.password_et);
        email = email_et.getText().toString();
        password = password_et.getText().toString();
    }

    //参数检查
    private boolean is_params_valid() {

        //邮箱，密码不可以空
        if (BaseUtils.is_str_blank(email)) {
            BaseUtils.toast(R.string.login_email_valid_blank);
            return false;
        }

        if (BaseUtils.is_str_blank(password)) {
            BaseUtils.toast(R.string.login_password_valid_blank);
            return false;
        }
        
        return true;
    }
//
//    //显示正在登录，并在一个线程中进行登录
//    private void do_login() {
//    	if (!BaseUtils.is_wifi_active(this)) {
//    		BaseUtils.toast(getResources().getString(R.string.is_wifi_active_msg));
//    	}
//        new TeamknAsyncTask<String, Void, VersionCheck>(this, R.string.login_now_login) {
//            @Override
//            public VersionCheck do_in_background(String... params) throws Exception {
//                // 为了在不联网的情况下使用，注释掉
//                String email = params[0];
//                String password = params[1];
//                HttpApi.user_authenticate(email, password);
//                String version = getResources().getString(R.string.app_version);
////                version = "0.51";
//                return HttpApi.get_version(version);
//            }
//            @Override
//            public void on_success(VersionCheck check) {
//            	if(check.action.equals(VersionCheck.Action.NEWEST)){
//            		is_first_login();
//                    finish();
//            	}else{
//            		//初始化一个自定义的Dialog                 
//            		Dialog dialog = new MyVersionDialog(MyVersionDialog.ActivityCheck.LOGIN_ACTIVITY,LoginActivity.this, R.style.MyVersionDialog,check);                   
//            		dialog.show();     
//            	}	
//            }
//        }.execute(email, password);
//    }
//    private void is_first_login(){
//    	if(current_user().is_show_tip){
//    		MainActivity.RequestCode.SHOW_HELP = MainActivity.RequestCode.SHOW_STEP_HELP;
//    		MainActivity.RequestCode.SHOW_NEXT = MainActivity.RequestCode.SHOW_STEP_HELP_CASE;
//    		open_activity(LoginSwitchViewDemoActivity.class);
//    		LoginSwitchViewDemoActivity.is_login_go_to = true;
//    		return;
//    	}
//    	open_activity(MainActivity.class);
//    }
}
