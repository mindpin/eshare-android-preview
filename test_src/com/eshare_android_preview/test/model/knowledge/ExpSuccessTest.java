package com.eshare_android_preview.test.model.knowledge;


import android.test.AndroidTestCase;

import com.eshare_android_preview.http.api.TestSuccessHttpApi;
import com.eshare_android_preview.http.model.TestSuccess;

import junit.framework.Assert;

public class ExpSuccessTest extends AndroidTestCase {
    private TestSuccess test_success;

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void test_add_exp_num() {
        try {
            test_success = TestSuccessHttpApi.build_test_success("javascript", "node-31");
            Assert.assertEquals(test_success.add_exp_num, 0);
            Assert.assertEquals(test_success.history_info.length, 5);
            Assert.assertEquals(test_success.day_exp(0).week_day, "周三");
            Assert.assertEquals(test_success.day_exp(1).month_day, "7");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}