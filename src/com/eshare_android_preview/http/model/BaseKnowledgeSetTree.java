package com.eshare_android_preview.http.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class BaseKnowledgeSetTree {
    public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();

    public BaseKnowledgeSetTree(List<BaseKnowledgeSet> root_base_set){
        this.children = root_base_set;
    }
}
