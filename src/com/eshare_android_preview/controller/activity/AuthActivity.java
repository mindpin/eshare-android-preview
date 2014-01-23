package com.eshare_android_preview.controller.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.model.filedata.FileLoginData;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.utils.ValidateUtil;
import com.eshare_android_preview.view.adapter.LoginPopAdapter;

public class AuthActivity extends EshareBaseActivity{
	EditText login_inputer;
	EditText password_inputer;
	
	private String login;
    private String password;
    
    FileLoginData login_file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.auth);

        if (BaseUtils.net_is_active(this)) {
            load_app();
        } else {
            show_no_network();
        }

		super.onCreate(savedInstanceState);
	}

    private void load_app() {
        new BaseAsyncTask<Void, Void, Void>() {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                UserData.instance().refresh();
                return null;
            }

            @Override
            public void on_success(Void result) {
                if (is_logged_in()) {
                    open_activity(HomeActivity.class);
                    finish();
                    return;
                }

                show_auth_view();
            }
        }.execute();
    }

    private void show_auth_view() {
        findViewById(R.id.loading_message_view).setVisibility(View.GONE);
        findViewById(R.id.auth_view).setVisibility(View.VISIBLE);

        login_inputer = (EditText) findViewById(R.id.login_inputer);
        password_inputer = (EditText) findViewById(R.id.password_inputer);

        on_init();
    }

    private void show_no_network() {
        BaseUtils.toast("网络连接失败");
    }
	
	private void on_save() {
		if(BaseUtils.is_str_blank(login)) return;
		login_file.setLogin_data(login);
	}
	private void on_init(){
		login_file = new FileLoginData();
		login_inputer.setText(login_file.get_last_data());
		
		login_inputer.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && login_file.login_data != null && login_file.login_data.size() > 0) {
                    initPopup();
                }
            }
        });
	}

	private void initPopup() {
		LoginPopAdapter adapter = new LoginPopAdapter(this);
		adapter.add_items(login_file.login_data);
		View view = LayoutInflater.from(this).inflate(R.layout.l_login_tag, null);
		ListView list_view = (ListView) view.findViewById(R.id.list_view);
		list_view.setAdapter(adapter);
		
		int height = ViewGroup.LayoutParams.WRAP_CONTENT;
		int width = login_inputer.getWidth();
		final PopupWindow mPopup = new PopupWindow(view, width, height, true);
		mPopup.setBackgroundDrawable(new BitmapDrawable());
		mPopup.setOutsideTouchable(true);
		
		mPopup.showAsDropDown(login_inputer,0,0);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				login_inputer.setText(login_file.login_data.get(position));
				mPopup.dismiss();
			}
		});
	}

	public void login_button_click(View view){
		if (!is_params_valid()) return;
		do_login();
	}
	
    //参数检查
    private boolean is_params_valid() {
    	login = login_inputer.getText().toString();
        password = password_inputer.getText().toString();
        
        //邮箱，密码不可以空
        if (BaseUtils.is_str_blank(login)) {
            ValidateUtil.setViewError(login_inputer, R.string.login_email_valid_blank);
            return false;
        }

        if (BaseUtils.is_str_blank(password)) {
            ValidateUtil.setViewError(password_inputer, R.string.login_password_valid_blank);
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
				on_save();
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
