package com.eshare_android_preview.http.api;

import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeNetGsonBuilder;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeSetGsonBuilder;
import com.eshare_android_preview.http.model.KnowledgeNet;
import com.eshare_android_preview.http.model.KnowledgeSet;
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

    public static KnowledgeNet net(String net_id) throws Exception{
        return new EshareGetRequest<KnowledgeNet>("/knowledge_nets/" +  net_id) {
            @Override
            public KnowledgeNet on_success(String response_text) throws Exception {
                Gson gson = new Gson();
                KnowledgeNetGsonBuilder builder = gson.fromJson(response_text, KnowledgeNetGsonBuilder.class);
                return builder.build();
            }
        }.go();
    }

    public static KnowledgeSet set(String net_id, String set_id) throws Exception{
        return new EshareGetRequest<KnowledgeSet>("/knowledge_nets/" +  net_id + "/knowledge_sets/" + set_id) {
            @Override
            public KnowledgeSet on_success(String response_text) throws Exception {
                Gson gson = new Gson();
                KnowledgeSetGsonBuilder builder = gson.fromJson(response_text, KnowledgeSetGsonBuilder.class);
                return builder.build();
            }
        }.go();
    }
}
