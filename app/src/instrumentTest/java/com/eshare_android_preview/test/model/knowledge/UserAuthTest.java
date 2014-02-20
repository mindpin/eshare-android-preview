package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;

import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;

import junit.framework.Assert;

/**
 * Created by fushang318 on 14-1-8.
 */
public class UserAuthTest extends AndroidTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        UserAuthHttpApi.user_auth_out();
    }

    public void test_user_login(){
        Assert.assertEquals(AccountManager.is_logged_in(), false);
        try {
            UserAuthHttpApi.user_authenticate("admin@edu.dev","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(AccountManager.is_logged_in(), true);
    }

    public void test_user_logout() {
        Assert.assertEquals(AccountManager.is_logged_in(), false);
        try {
            UserAuthHttpApi.user_authenticate("admin@edu.dev","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(AccountManager.is_logged_in(), true);

        try {
            UserAuthHttpApi.user_auth_out();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(AccountManager.is_logged_in(), false);
    }
}
