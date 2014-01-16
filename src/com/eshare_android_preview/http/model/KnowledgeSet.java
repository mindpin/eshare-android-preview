package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeSet extends BaseKnowledgeSet {
    private String name;
    private String icon;
    private int node_count;
    private int learned_node_count;
    private List<IUserKnowledgeNode> nodes;

    public String get_name(){
        return name;
    }

    @Override
    public boolean is_checkpoint() {
        return false;
    }

    @Override
    public List<IUserKnowledgeNode> nodes(boolean remote) {
        if(remote){
            String net_id = UserData.instance().get_current_knowledge_net_id();
            List<KnowledgeNode> temp_nodes = KnowledgeNetHttpApi.set_nodes(net_id, id);
            nodes = new ArrayList<IUserKnowledgeNode>();
            for(KnowledgeNode node :temp_nodes){
                nodes.add(node);
            }
        }

        return nodes;
    }

    public int get_learned_node_count(){
        return learned_node_count;
    }

    public int get_node_count(){
        return node_count;
    }

    @Override
    public boolean is_root(){
        return this.parents.size() == 0;
    }

}
