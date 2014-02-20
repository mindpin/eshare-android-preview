package com.eshare_android_preview.http.api;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.base.EsharePostRequest;
import com.eshare_android_preview.http.base.PostParamText;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.http.model.AccountUser;

import org.json.JSONObject;


public class UserAuthHttpApi {

    public static final String 用户登录 = "/account/sign_in";

	// 用户登录请求
    public static boolean user_authenticate(String login, String password) throws Exception {
    	return new EsharePostRequest<Boolean>(
                用户登录,
                new PostParamText("user[login]", login),
                new PostParamText("user[password]", password)
        ) {
            @Override
            public Boolean on_success(String response_text) throws Exception {
                System.out.println(" => " + response_text);
                JSONObject json = new JSONObject(response_text);
                AccountManager.login(get_cookies(), json.toString());
                return true;
            }
            public void on_authenticate_exception() {
                AccountManager.logout();
            };
        }.go();
    }
    
    public static boolean user_auth_out(){
        AccountManager.logout();
        return true;
    }


    public static AccountUser user_profile(){
        try {
            return new EshareGetRequest<AccountUser>("/api/user/profile") {
                @Override
                public AccountUser on_success(String response_text) throws Exception {
                    JSONObject json = new JSONObject(response_text);
                    AccountManager.login(get_cookies(), json.toString());
                    return AccountManager.current_user();
                }
                public void on_authenticate_exception() {
                    AccountManager.logout();
                };
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return AccountManager.current_user();
        }
    }
}
