package com.eshare_android_preview.http.logic.knowledge_net;

import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;
import com.eshare_android_preview.http.model.BaseKnowledgeSet;
import com.eshare_android_preview.http.model.CurrentState;
import com.eshare_android_preview.http.model.KnowledgeCheckpoint;
import com.eshare_android_preview.http.model.KnowledgeNet;
import com.eshare_android_preview.http.model.KnowledgeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNetGsonBuilder {
    private int total_exp_num;
    private String id;
    private String name;
    private KnowledgeSet[] sets;
    private KnowledgeCheckpoint[] checkpoints;
    private BaseKnowledgeSetRelation[] relations;

    private Map<String,BaseKnowledgeSet> base_set_map = new HashMap<String, BaseKnowledgeSet>();

    public KnowledgeNet build(){
        _build_base_set_map();
        _process_relations();
        return _build_root_base_set();
    }

    private KnowledgeNet _build_root_base_set(){

        List<IUserKnowledgeSet> sets = new ArrayList<IUserKnowledgeSet>();
        for(BaseKnowledgeSet set : base_set_map.values()){
            sets.add(set);
        }
        CurrentState exp_status = new CurrentState(total_exp_num);
        return new KnowledgeNet(id, name, exp_status, base_set_map);
    }

    private void _process_relations(){
        for(BaseKnowledgeSetRelation relation : relations){
            BaseKnowledgeSet parent = find_base_set_by_id(relation.parent);
            BaseKnowledgeSet child = find_base_set_by_id(relation.child);
            parent.add_child(child);
            child.add_parent(parent);
        }
    }

    private void _build_base_set_map(){
        for(KnowledgeSet set : sets){
            base_set_map.put(set.get_id(), set);
        }
        for(KnowledgeCheckpoint checkpoint : checkpoints){
            base_set_map.put(checkpoint.get_id(), checkpoint);
        }
    }

    private BaseKnowledgeSet find_base_set_by_id(String base_set_id){
        return base_set_map.get(base_set_id);
    }
}
