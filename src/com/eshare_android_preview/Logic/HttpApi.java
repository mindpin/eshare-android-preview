package com.eshare_android_preview.Logic;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import com.eshare_android_preview.base.http.PostParamText;
import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.base.http.EshareHttpRequest;
import com.eshare_android_preview.base.http.EsharePostRequest;


public class HttpApi {
	public static final String SITE = "http://127.0.0.1";
	
	public static final String 用户登录 = "/account/sign_in";
	public static final String 用户登出 = "/account/sign_out";
	
	// 用户登录请求
    public static boolean user_authenticate(String login, String password) throws Exception {
    	System.out.println("login : = " + 用户登录 +" : " +login + " : " +  password);
    	return new EsharePostRequest<Boolean>(
                用户登录,
                new PostParamText("login", login),
                new PostParamText("password", password)
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
				return null;
			}
		}.go();
    }
    
    public static InputStream download_image(String image_url) {
        try {
          HttpGet httpget = new HttpGet(image_url);
          HttpResponse response = EshareHttpRequest.get_httpclient_instance().execute(httpget);
          int status_code = response.getStatusLine().getStatusCode();
          if (HttpStatus.SC_OK == status_code) {
            return response.getEntity().getContent();
          } else {
            return null;
          }
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }
}
