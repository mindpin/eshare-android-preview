package com.eshare_android_preview.http.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class BaseKnowledgeSet {
    public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
    public List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();

    public void add_child(BaseKnowledgeSet child){
        this.children.add(child);
        child.parents.add(this);
    }

    public boolean is_root(){
        return this.parents.size() == 0;
    }

}
