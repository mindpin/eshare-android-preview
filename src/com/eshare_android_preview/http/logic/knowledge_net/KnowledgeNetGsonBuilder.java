package com.eshare_android_preview.http.logic.knowledge_net;

import com.eshare_android_preview.http.i.knowledge.IUserBaseKnowledgeSet;
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
    private CurrentState exp_status;
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
        List<IUserBaseKnowledgeSet> sets = new ArrayList<IUserBaseKnowledgeSet>();
        for(BaseKnowledgeSet set : base_set_map.values()){
            sets.add(set);
        }
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
            base_set_map.put(set.id, set);
        }
        for(KnowledgeCheckpoint checkpoint : checkpoints){
            base_set_map.put(checkpoint.id, checkpoint);
        }
    }

    private BaseKnowledgeSet find_base_set_by_id(String base_set_id){
        return base_set_map.get(base_set_id);
    }
}
