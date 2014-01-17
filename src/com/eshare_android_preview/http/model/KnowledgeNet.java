package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.IDataIcon;
import com.eshare_android_preview.http.i.knowledge.ICanbeLearned;
import com.eshare_android_preview.http.i.knowledge.IUserExp;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;
import com.eshare_android_preview.http.i.question.IQuestionLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNet extends IUserKnowledgeNet {
    private String id;
    private String name;
    private List<IUserKnowledgeSet> children = new ArrayList<IUserKnowledgeSet>();
    private Map<String,BaseKnowledgeSet> base_set_map = new HashMap<String, BaseKnowledgeSet>();
    private CurrentState exp_status;

    public KnowledgeNet(String id, String name, CurrentState exp_status, Map<String,BaseKnowledgeSet> base_set_map){
        this.id = id;
        this.name = name;
        this.exp_status = exp_status;
        this.base_set_map = base_set_map;
        for(IUserKnowledgeSet set : base_set_map.values()){
            if(set.is_root()){
                this.children.add(set);
            }
        }
    }

    @Override
    public List<IUserKnowledgeSet> children() {
        return children;
    }

    @Override
    public IUserExp get_exp() {
        return exp_status;
    }

    @Override
    public IUserKnowledgeSet find_by_set_id(String set_id) {
        BaseKnowledgeSet set = base_set_map.get(set_id);
        if(set.getClass() == KnowledgeSet.class){
            return set;
        }
        return null;
    }

    @Override
    public IQuestionLoader get_iquestion_loader(String type, String id) {
        Object result = find_object(type, id);
        return result == null ? null : (IQuestionLoader)result;
    }

    @Override
    public ICanbeLearned find_learn_target(String type, String id) {
        Object result = find_object(type,id);
        return result == null ? null : (ICanbeLearned)result;
    }

    private Object find_object(String type, String id){
        if(type.equals("KnowledgeSet") || type.equals("KnowledgeCheckpoint")){
            return base_set_map.get(id);
        }else if(type.equals("KnowledgeNode")){
            for(BaseKnowledgeSet set : base_set_map.values()){
                Object object = set.find_node(id);
                if(object != null) return object;
            }
        }
        return null;
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
