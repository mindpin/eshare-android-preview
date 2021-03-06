package com.eshare_android_preview.test.model.knowledge;

import junit.framework.Assert;

import com.eshare_android_preview.http.api.ExpApi;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.http.model.CurrentState;

import android.test.AndroidTestCase;


public class ExpTest extends AndroidTestCase{
	@Override
	protected void setUp() throws Exception {
		super.setUp();
        try {
            UserAuthHttpApi.user_authenticate("admin@edu.dev","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void test_exp_info(){
		Assert.assertNotNull(ExpApi.exp_info("javascript"));
		CurrentState cs = ExpApi.exp_info("javascript");
		Assert.assertEquals(cs.get_level(), 1);
		Assert.assertEquals(cs.get_next_level_total_exp(), 10);
		Assert.assertEquals(cs.get_level_exp(), 0);
	}
}
