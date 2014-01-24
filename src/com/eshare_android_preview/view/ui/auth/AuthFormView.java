package com.eshare_android_preview.view.ui.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.HomeActivity;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.model.filedata.FileLoginData;

/**
 * Created by Administrator on 14-1-24.
 */
public class AuthFormView extends RelativeLayout {
    FileLoginData login_data;
    EmailInputerView email_inputer;
    PasswordInputerView password_inputer;
    LoginButton login_button;
    TextView auth_message;

    public AuthFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.auth_form, null);
        addView(view);

        if (isInEditMode()) return;

        login_data = new FileLoginData();
        email_inputer = (EmailInputerView) findViewById(R.id.email_inputer);
        password_inputer = (PasswordInputerView) findViewById(R.id.password_inputer);
        login_button = (LoginButton) findViewById(R.id.login_button);
        auth_message = (TextView) findViewById(R.id.auth_message);

        email_inputer.set_email(login_data.get_last_data());

        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_params_valid()) return;
                do_login();
            }
        });
    }

    //参数检查
    private boolean is_params_valid() {
        if (email_inputer.is_blank()) {
            auth_message.setText("请填写邮箱");
            return false;
        }

        if (password_inputer.is_blank()) {
            auth_message.setText("请填写密码");
            return false;
        }

        return true;
    }

    //显示正在登录，并在一个线程中进行登录
    private void do_login() {
        final EshareBaseActivity activity = (EshareBaseActivity) getContext();
        auth_message.setText("正在登录…");

        new BaseAsyncTask<String, Void, Void>(activity, R.string.login_now_login) {
            @Override
            public Void do_in_background(String... params) throws Exception {
                UserAuthHttpApi.user_authenticate(email_inputer.get_email(), password_inputer.get_password());
                return null;
            }

            @Override
            public void on_success(Void result) {
                auth_message.setText("登录成功");
                activity.open_activity(HomeActivity.class);
//                login_file.setLogin_data(email);
                activity.finish();
            }

            @Override
            public void on_authenticate_exception() {
                auth_message.setText("邮箱/密码不正确");
            }

            @Override
            public void on_http_host_connect_exception() {
                auth_message.setText("网络不给力，登录失败");
            }

            @Override
            public void on_response_not_200_exception() {
                auth_message.setText("服务异常，登录失败");
            }

            @Override
            public boolean on_unknown_exception() {
                auth_message.setText("程序数据错误，登录失败");
                return false;
            }
        }.execute();
    }
}
