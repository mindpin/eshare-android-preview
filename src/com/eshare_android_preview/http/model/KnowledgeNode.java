package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-9.
 */
public class KnowledgeNode implements IUserKnowledgeNode {
    private String id;
    private String name;
    private String desc;
    private boolean required;
    private boolean is_unlocked;
    private boolean is_learned;

    private List<IUserKnowledgeNode> children = new ArrayList<IUserKnowledgeNode>();
    private List<IUserKnowledgeNode> parents = new ArrayList<IUserKnowledgeNode>();

    public void add_child(KnowledgeNode child){
        this.children.add(child);
    }

    public void add_parent(KnowledgeNode parent){
        this.parents.add(parent);
    }

    @Override
    public String get_id() {
        return id;
    }

    @Override
    public String get_name() {
        return name;
    }

    @Override
    public String get_desc() {
        return desc;
    }

    @Override
    public boolean get_required() {
        return required;
    }

    @Override
    public List<IUserKnowledgeNode> children() {
        return children;
    }

    @Override
    public List<IUserKnowledgeNode> parents() {
        return parents;
    }

    @Override
    public boolean is_unlocked() {
        return is_unlocked;
    }

    @Override
    public boolean is_learned() {
        return is_learned;
    }
}