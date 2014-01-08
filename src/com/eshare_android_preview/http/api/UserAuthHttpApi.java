package com.eshare_android_preview.http.api;

import org.json.JSONObject;

import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.base.http.PostParamText;
import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.base.http.EsharePostRequest;


public class UserAuthHttpApi {

    public static final String 用户登录 = "/account/sign_in";
	public static final String 用户登出 = "/account/sign_out";
	
	// 用户登录请求
    public static boolean user_authenticate(String login, String password) throws Exception {
    	System.out.println("login : = " + 用户登录 +" : " +login + " : " +  password);
    	return new EsharePostRequest<Boolean>(
                用户登录,
                new PostParamText("user[login]", login),
                new PostParamText("user[password]", password)
        ) {
            @Override
            public Boolean on_success(String response_text) throws Exception {
            	System.out.println("json authenticate " + response_text);
                JSONObject json = new JSONObject(response_text);
                AccountManager.login(get_cookies(), json.toString());
                return true;
            }
            public void on_authenticate_exception() {
            	
            };
        }.go();
    }
    
    public static boolean user_auth_out() throws Exception {
    	return new EshareGetRequest<Boolean>(用户登出) {
			@Override
			public Boolean on_success(String response_text) throws Exception {
				return true;
			}
		}.go();
    }

}
