package com.eshare_android_preview.controller.activity;

import android.os.Bundle;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.utils.BaseUtils;

public class LoadingActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.base_loading);

        if(BaseUtils.net_is_active(this)){
            user_auth();
        }else{
            BaseUtils.toast("网络连接失败");
        }
	}

    private void user_auth(){
        new BaseAsyncTask<Void, Void, Void>() {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                if(is_logged_in()){
                    UserData.instance().get_profile(true);
                }
                return null;
            }

            @Override
            public void on_success(Void result) {
                open_activity(is_logged_in() ? HomeActivity.class : LoginActivity.class);
                finish();
            }
        }.execute();
    }
}
