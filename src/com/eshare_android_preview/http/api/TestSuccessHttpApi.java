package com.eshare_android_preview.http.api;


import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.http.model.TestSuccess;

public class TestSuccessHttpApi {

    public static TestSuccess build_test_success(String net_id, String id) throws Exception {
        return new EshareGetRequest<TestSuccess>("/knowledge_nets/" + net_id +
                "/knowledge_nodes/" + id + "/test_success") {

            @Override
            public TestSuccess on_success(String response_text) throws Exception {
                return TestSuccess.build_from_json(response_text);
            }
        }.go();
    }
}
