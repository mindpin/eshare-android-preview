package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.profile.IUserAvatar;
import com.eshare_android_preview.http.i.profile.IUserProfile;
import com.eshare_android_preview.model.database.base.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AccountUser extends BaseModel implements IUserProfile {
	public String cookies;
    public String info;

    public int user_id;
    public String name;
    public String login;
    public String email;
    public String avatar_url;
    private IUserAvatar user_avatar;

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
        this.avatar_url = json.getString("avatar");

        this.user_avatar = new UserAvatar(this.avatar_url);
    }

	public AccountUser(String cookies, String info, int user_id, String name,String login,String email, String avatar_url) {
		super();
		this.cookies = cookies;
		this.info = info;
		this.user_id = user_id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.avatar_url = avatar_url;
	}
	
    @Override
    public String get_email() {
        return this.email;
    }

    @Override
    public String get_name() {
        return this.name;
    }

    @Override
    public IUserAvatar get_avatar() {
        return this.user_avatar;
    }
}
