package com.eshare_android_preview.http.logic.knowledge_net;

import com.eshare_android_preview.http.model.BaseKnowledgeSet;
import com.eshare_android_preview.http.model.KnowledgeNode;
import com.eshare_android_preview.http.model.KnowledgeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fushang318 on 14-1-9.
 */
public class KnowledgeSetGsonBuilder {
    public String id;
    public String name;
    public String icon;
    public int deep;
    public boolean is_unlocked;
    public boolean is_learned;
    public int node_count;
    public int learned_node_count;
    private KnowledgeNode[] nodes;
    private KnowledgeNodeRelation[] relations;

    private Map<String,KnowledgeNode> node_map = new HashMap<String, KnowledgeNode>();

    public KnowledgeSet build(){
        _build_node_map();
        _process_relations();
        return _build_set();
    }

    private KnowledgeSet _build_set() {
        KnowledgeSet set = new KnowledgeSet();

        set.id = this.id;
        set.name = this.name;
        set.icon = this.icon;
        set.deep = this.deep;
        set.is_unlocked = this.is_unlocked;
        set.is_learned = this.is_learned;
        set.node_count = this.node_count;
        set.learned_node_count = this.learned_node_count;
        set.nodes = new ArrayList<KnowledgeNode>(this.node_map.values());
        
        return set;
    }

    private void _process_relations() {
        for(KnowledgeNodeRelation relation : relations){
            KnowledgeNode parent = find_node_by_id(relation.parent);
            KnowledgeNode child = find_node_by_id(relation.child);
            parent.add_child(child);
        }
    }

    private void _build_node_map() {
        for(KnowledgeNode node : nodes){
            node_map.put(node.id, node);
        }
    }

    private KnowledgeNode find_node_by_id(String node_id){
        return node_map.get(node_id);
    }
}
