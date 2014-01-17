package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.IDataIcon;
import com.eshare_android_preview.http.i.knowledge.ICanbeLearned;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public abstract class BaseKnowledgeSet implements IUserKnowledgeSet, ICanbeLearned {
    protected String id;
    protected int deep;
    protected boolean is_learned;
    protected List<IUserKnowledgeSet> children;
    protected List<IUserKnowledgeSet> parents;

    public BaseKnowledgeSet(){
        children = new ArrayList<IUserKnowledgeSet>();
        parents = new ArrayList<IUserKnowledgeSet>();
    }

    public void add_child(BaseKnowledgeSet child){
        this.children.add(child);
    }

    public void add_parent(BaseKnowledgeSet parent){
        this.parents.add(parent);
    }

    @Override
    public IDataIcon get_icon() {
        return null;
    }

    @Override
    public String get_id() {
        return id;
    }

    @Override
    public int get_deep() {
        return deep;
    }

    @Override
    public boolean is_learned(){
        return is_learned;
    }

    @Override
    public List<IUserKnowledgeSet> children() {
        return children;
    }

    @Override
    public List<IUserKnowledgeSet> parents() {
        return parents;
    }
    
    @Override
    public void set_learned(){
        this.is_learned = true;
    }


    abstract public IUserKnowledgeNode find_node(String id);
    abstract public String get_name();
    abstract public boolean is_checkpoint();
    abstract public List<IUserKnowledgeNode> nodes(boolean remote);
    abstract public boolean is_root();
    abstract public boolean is_unlocked();
}
