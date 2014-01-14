package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.IDataIcon;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;
import com.eshare_android_preview.http.logic.knowledge_net.KnowledgeSetGsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeSet extends BaseKnowledgeSet implements IUserKnowledgeSet {
    private String name;
    private String icon;
    private int node_count;
    private int learned_node_count;
    public List<KnowledgeNode> nodes;

//   必须保留，不然 GSON 不能正常初始化父类上的 children parents 两个字段
    public KnowledgeSet(){
        super();
    }

    public KnowledgeSet(KnowledgeSetGsonBuilder builder){
        super();
        this.id = builder.id;
        this.name = builder.name;
        this.icon = builder.icon;
        this.deep = builder.deep;
        this.is_unlocked = builder.is_unlocked;
        this.is_learned = builder.is_learned;
        this.node_count = builder.node_count;
        this.learned_node_count = builder.learned_node_count;
        this.nodes = new ArrayList<KnowledgeNode>(builder.node_map.values());
    }

    public String get_name(){
        return name;
    }

    @Override
    public IDataIcon get_icon() {
        return null;
    }

    @Override
    public List<IUserKnowledgeNode> nodes(boolean remote) {
//        TODO 未实现
        return null;
    }

    public int get_learned_node_count(){
        return learned_node_count;
    }

    public int get_node_count(){
        return node_count;
    }

}
