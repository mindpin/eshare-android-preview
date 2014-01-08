package com.eshare_android_preview.http.api;

import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.http.logic.knowledge_net.BaseKnowledgeSetTreeBuilder;
import com.eshare_android_preview.http.model.KnowledgeNet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNetHttpApi {
    public static List<KnowledgeNet> net_list() throws Exception{
        return new EshareGetRequest<List<KnowledgeNet>>("/knowledge_nets/list") {
            @Override
            public List<KnowledgeNet> on_success(String response_text) throws Exception {
                Type collectionType = new TypeToken<List<KnowledgeNet>>(){}.getType();
                Gson gson = new Gson();
                return gson.fromJson(response_text, collectionType);
            }
        }.go();
    }

    public static BaseKnowledgeSetTreeBuilder net_sets(String net_id) throws Exception{
        return new EshareGetRequest<BaseKnowledgeSetTreeBuilder>("/knowledge_nets/" +  net_id + "/sets") {
            @Override
            public BaseKnowledgeSetTreeBuilder on_success(String response_text) throws Exception {
                Gson gson = new Gson();
                return gson.fromJson(response_text, BaseKnowledgeSetTreeBuilder.class);
            }
        }.go();
    }
}
