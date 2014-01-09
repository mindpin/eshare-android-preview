package com.eshare_android_preview.http.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNet {
    public String id;
    public String name;
    public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();

    public KnowledgeNet(String id, String name, List<BaseKnowledgeSet> root_base_set){
        this.id = id;
        this.name = name;
        this.children = root_base_set;
    }
}
