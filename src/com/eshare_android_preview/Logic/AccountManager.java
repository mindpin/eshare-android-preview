package com.eshare_android_preview.Logic;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import com.eshare_android_preview.base.http.CookieHelper;
import com.eshare_android_preview.base.http.EshareHttpRequest.AuthenticateException;
import com.eshare_android_preview.model.AccountUser;
import com.eshare_android_preview.model.database.AccountUserDBHelper;

public class AccountManager {
	public static void login(String cookies, String info) throws Exception {
        AccountUser account_user = new AccountUser(cookies, info);

        if (AccountUserDBHelper.save(account_user)) {
            switch_account(account_user);
        } else {
            throw new AuthenticateException();
        }
    }
	
	public static void switch_account(AccountUser account_user) {
        APPPreferences.set_current_user_id(account_user.user_id);
    }
	
	public static boolean is_logged_in(){
		return !current_user().is_nil();
	}
	
	public static AccountUser current_user() {
		int user_id = APPPreferences.current_user_id();
        return AccountUserDBHelper.find(user_id);
	}
	
	public static CookieStore get_cookie_store() {
		String cookies_string = current_user().cookies;
		BasicCookieStore cookie_store = (BasicCookieStore) CookieHelper.parse_string_to_cookie_store(cookies_string);
		return cookie_store;
	}
}
