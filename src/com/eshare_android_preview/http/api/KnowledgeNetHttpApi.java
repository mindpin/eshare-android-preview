package com.eshare_android_preview.http.api;

import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeNetGsonBuilder;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeNodesGsonBuilder;
import com.eshare_android_preview.http.model.KnowledgeNet;
import com.eshare_android_preview.http.model.KnowledgeNode;
import com.eshare_android_preview.http.model.KnowledgeSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public static KnowledgeNet net(String net_id){
        try {
            return new EshareGetRequest<KnowledgeNet>("/knowledge_nets/" +  net_id) {
                @Override
                public KnowledgeNet on_success(String response_text) throws Exception {
                    Gson gson = new Gson();
                    KnowledgeNetGsonBuilder builder = gson.fromJson(response_text, KnowledgeNetGsonBuilder.class);
                    return builder.build();
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<KnowledgeNode> set_nodes(String net_id, String set_id){
        try {
            return new EshareGetRequest<List<KnowledgeNode>>("/knowledge_nets/" +  net_id + "/knowledge_sets/" + set_id) {
                @Override
                public List<KnowledgeNode> on_success(String response_text) throws Exception {
                    Gson gson = new Gson();
                    KnowledgeNodesGsonBuilder builder = gson.fromJson(response_text, KnowledgeNodesGsonBuilder.class);
                    return builder.build();
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<KnowledgeNode>();
        }
    }
}
