package com.eshare_android_preview.http.model;


import com.eshare_android_preview.base.http.EshareGetRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TestSuccess {
    public int add_exp_num;
    public ArrayList<DayExp> history_info;


    public static TestSuccess build_test_success(String net_id, String id) throws Exception {
        return new EshareGetRequest<TestSuccess>("/knowledge_nets/" + net_id +
                "/knowledge_nodes/" + id + "/test_success") {

            @Override
            public TestSuccess on_success(String response_text) throws Exception {
                Gson gson = new Gson();
                return gson.fromJson(response_text, TestSuccess.class);
            }


        }.go();
    }


}
