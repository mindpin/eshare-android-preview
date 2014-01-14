package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.IDataIcon;
import com.eshare_android_preview.http.i.knowledge.IUserBaseKnowledgeSet;
import com.eshare_android_preview.http.i.knowledge.IUserExp;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNet extends IUserKnowledgeNet {
    private String id;
    private String name;
    private List<IUserBaseKnowledgeSet> children = new ArrayList<IUserBaseKnowledgeSet>();
    private CurrentState exp_status;

    public KnowledgeNet(String id, String name, List<IUserBaseKnowledgeSet> root_base_set, CurrentState exp_status){
        this.id = id;
        this.name = name;
        this.children = root_base_set;
        this.exp_status = exp_status;
    }

    @Override
    public List<IUserBaseKnowledgeSet> children() {
        return children;
    }

    @Override
    public IUserExp get_exp() {
        return exp_status;
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
    public IDataIcon get_icon() {
//        TODO 未实现
        return null;
    }
}
