package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeCheckpoint extends BaseKnowledgeSet {
    private String learned_sets[];

    @Override
    public IUserKnowledgeNode find_node(String id) {
        return null;
    }

    public String get_name(){
        return "综合测试";
    }

    public boolean is_checkpoint(){
        return true;
    }

    @Override
    public List<IUserKnowledgeNode> nodes(boolean remote) {
        return new ArrayList<IUserKnowledgeNode>();
    }

    @Override
    public boolean is_root() {
        return false;
    }

    @Override
    public boolean is_unlocked() {
        return true;
    }
}
