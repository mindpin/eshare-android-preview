package com.eshare_android_preview.http.api;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeNetGsonBuilder;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeNodesGsonBuilder;
import com.eshare_android_preview.http.model.KnowledgeNet;
import com.eshare_android_preview.http.model.KnowledgeNode;
import com.eshare_android_preview.http.model.KnowledgeSet;
import com.eshare_android_preview.http.model.SimpleKnowledgeNet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNetHttpApi {
    public static List<SimpleKnowledgeNet> net_list(){
        try {
            return new EshareGetRequest<List<SimpleKnowledgeNet>>("/api/knowledge_nets/list") {
                @Override
                public List<SimpleKnowledgeNet> on_success(String response_text) throws Exception {
                    Type collectionType = new TypeToken<List<SimpleKnowledgeNet>>(){}.getType();
                    Gson gson = new Gson();
                    return gson.fromJson(response_text, collectionType);
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<SimpleKnowledgeNet>();
        }
    }

    public static KnowledgeNet net(String net_id){
        try {
            return new EshareGetRequest<KnowledgeNet>("/api/knowledge_nets/" +  net_id) {
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

    public static List<KnowledgeNode> set_nodes(String net_id, final KnowledgeSet set){
        try {
            return new EshareGetRequest<List<KnowledgeNode>>("/api/knowledge_nets/" +  net_id + "/knowledge_sets/" + set.get_id()) {
                @Override
                public List<KnowledgeNode> on_success(String response_text) throws Exception {
                    Gson gson = new Gson();
                    KnowledgeNodesGsonBuilder builder = gson.fromJson(response_text, KnowledgeNodesGsonBuilder.class);
                    return builder.build(set);
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<KnowledgeNode>();
        }
    }
}
