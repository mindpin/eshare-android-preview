package com.eshare_android_preview.http.model;

import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeSet extends BaseKnowledgeSet {
    public String name;
    public String icon;
    public int node_count;
    public int learned_node_count;
    public List<KnowledgeNode> nodes;

    public String get_name(){
        return name;
    }

}
