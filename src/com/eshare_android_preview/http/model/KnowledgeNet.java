package com.eshare_android_preview.http.model;

import com.eshare_android_preview.base.view.knowledge_map.IHasChildren;
import com.eshare_android_preview.model.preferences.EsharePreference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNet implements IHasChildren {
    public static KnowledgeNet current_net;

    public String id;
    public String name;
    public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
    public CurrentState exp_status;

    public KnowledgeNet(String id, String name, List<BaseKnowledgeSet> root_base_set, CurrentState exp_status){
        this.id = id;
        this.name = name;
        this.children = root_base_set;
        this.exp_status = exp_status;
    }

    public static String current_net_id(){
       return EsharePreference.get_current_net();
    }

    public static void switch_to(String net_id){
        EsharePreference.switch_to(net_id);
    }

    @Override
    public List<BaseKnowledgeSet> children() {
        return children;
    }
}
