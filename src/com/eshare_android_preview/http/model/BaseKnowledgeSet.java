package com.eshare_android_preview.http.model;

import com.eshare_android_preview.base.view.knowledge_map.IHasChildren;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class BaseKnowledgeSet implements IHasChildren{
    public String id;
    public int deep;
    public boolean is_unlocked;
    public boolean is_learned;
    public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
    public List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();

    public void add_child(BaseKnowledgeSet child){
        this.children.add(child);
        child.parents.add(this);
    }

    public boolean is_root(){
        return this.parents.size() == 0;
    }

    public String get_name(){
        return "";
    }

    public boolean is_checkpoint(){
        return false;
    }

    @Override
    public List<BaseKnowledgeSet> children() {
        return children;
    }
}
