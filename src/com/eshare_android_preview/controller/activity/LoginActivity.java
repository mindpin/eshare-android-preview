package com.eshare_android_preview.controller.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.eshare_android_preview.EshareApplication;
import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.model.filedata.FileLoginData;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.utils.ValidateUtil;
import com.eshare_android_preview.view.adapter.LoginPopAdapter;

public class LoginActivity extends EshareBaseActivity{
	EditText login_et;
	EditText password_et;
	
	private String login;
    private String password;
    
    FileLoginData login_file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_login);
		
		login_et = (EditText) findViewById(R.id.login_et);
        password_et = (EditText) findViewById(R.id.password_et);
        
        init();
	}

	private void init(){
		login_file = new FileLoginData();
		if (login_file.getLogin_data().size() > 0) {
			login_et.setText(login_file.login_data.get(login_file.login_data.size() - 1));
		}
		listener();
	}
	
	private PopupWindow mPopup;
	private boolean mInitPopup;
	private boolean mShowing;
	private void listener(){
		login_et.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					if(login_file.login_data!=null && login_file.login_data.size()>0 && !mInitPopup){
						mInitPopup = true;
						initPopup();
						}
						if (mPopup != null) {
							if (!mShowing) {
								mPopup.showAsDropDown(login_et,0,0);
								mShowing = true;
							} else {
								mPopup.dismiss();
							}
						}
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
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				login_et.setText(login_file.login_data.get(position));
				mPopup.dismiss();
			}
		});
		int height = ViewGroup.LayoutParams.WRAP_CONTENT;
		int width = login_et.getWidth();
		mPopup = new PopupWindow(view, width, height, true);
		mPopup.setBackgroundDrawable(new BitmapDrawable());
		mPopup.setOutsideTouchable(true);
		mPopup.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mShowing = false;
			}
		});
	}
	
	protected void onDestroy() {
		super.onDestroy();
		String login_input = login_et.getText().toString();
		login_file.setLogin_data(login_input);
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
    	}.execute();
    }
}
