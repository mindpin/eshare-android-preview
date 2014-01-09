package com.eshare_android_preview.http.api;


import com.eshare_android_preview.http.model.TestSuccess;

public class TestSuccessHttpApi {

    public static TestSuccess build_test_success(String net_id, String id) {
        try {
            return TestSuccess.build_test_success(net_id, id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
