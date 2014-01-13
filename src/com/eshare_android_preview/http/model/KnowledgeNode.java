package com.eshare_android_preview.http.model;

import com.eshare_android_preview.model.OldQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-9.
 */
public class KnowledgeNode implements TestPaperTarget {
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

//    TODO TestPaperTarget 方法实现的梳理
    @Override
    public String model() {
        return null;
    }

    @Override
    public String model_id() {
        return null;
    }

    @Override
    public String get_course() {
        return null;
    }

    @Override
    public OldQuestion get_random_question(List<Integer> except_ids) {
        return null;
    }

    @Override
    public boolean is_learned() {
        return false;
    }

    @Override
    public int do_learn() {
        return 0;
    }
}
