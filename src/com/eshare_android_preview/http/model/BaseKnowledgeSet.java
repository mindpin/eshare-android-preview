package com.eshare_android_preview.http.model;

import com.eshare_android_preview.base.view.knowledge_map.IHasChildren;
import com.eshare_android_preview.http.i.knowledge.IUserBaseKnowledgeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class BaseKnowledgeSet implements IUserBaseKnowledgeSet {
    public String id;
    public int deep;
    public boolean is_unlocked;
    public boolean is_learned;
    public List<IUserBaseKnowledgeSet> children;
    public List<IUserBaseKnowledgeSet> parents;


    public BaseKnowledgeSet(){
        children = new ArrayList<IUserBaseKnowledgeSet>();
        parents = new ArrayList<IUserBaseKnowledgeSet>();
    }

    public void add_child(BaseKnowledgeSet child){
        this.children.add(child);
    }

    public void add_parent(BaseKnowledgeSet parent){
        this.parents.add(parent);
    }

    public boolean is_root(){
        return this.parents.size() == 0;
    }

    @Override
    public String get_id() {
        return id;
    }

    public String get_name(){
        return "";
    }

    @Override
    public int get_deep() {
        return deep;
    }

    public boolean is_checkpoint(){
        return false;
    }

    @Override
    public boolean is_unlocked() {
        return is_unlocked;
    }

    @Override
    public List<IUserBaseKnowledgeSet> children() {
        return children;
    }

    @Override
    public List<IUserBaseKnowledgeSet> parents() {
        return parents;
    }
}
