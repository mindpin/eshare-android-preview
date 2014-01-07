package com.eshare_android_preview.http.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eshare_android_preview.http.api.BaseHttpApi;
import com.eshare_android_preview.model.base.BaseModel;


public class AccountUser extends BaseModel{
	public String cookies;
    public String info;

    public int user_id;
    public String name;
    public String login;
    public String email;
    public String avatar_url;
    public byte[] avatar;
    
    // 用一个特殊的user实例来表示一个空user
    // 用 is_nil() 方法来判断是否空user
    // 不可用 null == user 来判断
    final public static AccountUser NIL_ACCOUNT_USER = new AccountUser();

    private AccountUser() {
        set_nil();
    }
    
	public AccountUser(String cookies, String info) throws JSONException, IOException {
        this.cookies = cookies;
        this.info = info;
        JSONObject json = new JSONObject(info);
       
        this.user_id = json.getInt("id");
        this.name = json.getString("name");
        this.login = json.getString("login");
        this.email = json.getString("email");
        this.avatar_url = json.getString("avatar_url");
        if(this.avatar_url != null && !this.avatar_url.equals("")){
          InputStream is = BaseHttpApi.download_image(this.avatar_url);
          byte[] avatar = IOUtils.toByteArray(is);
          this.avatar = avatar;
        }
    }

	public AccountUser(String cookies, String info, int user_id, String name,String login,String email, byte[] avatar) {
		super();
		this.cookies = cookies;
		this.info = info;
		this.user_id = user_id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.avatar = avatar;
	}
	
	
}
