package com.eshare_android_preview.http.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-9.
 */
public class KnowledgeNode {
    public String id;
    public String name;
    public String desc;
    public boolean required;
    public boolean is_unlocked;
    public boolean is_learned;

    public List<KnowledgeNode> children = new ArrayList<KnowledgeNode>();
    public List<KnowledgeNode> parents = new ArrayList<KnowledgeNode>();

    public void add_child(KnowledgeNode child){
        this.children.add(child);
        child.parents.add(this);
    }

}
