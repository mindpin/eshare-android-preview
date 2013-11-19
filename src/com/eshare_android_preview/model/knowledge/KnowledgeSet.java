package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.preferences.EsharePreference;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSet extends BaseKnowledgeSet implements ILearn{
	public String id;
	public String name;
	public String icon;

	public List<KnowledgeNode> nodes;
    public List<KnowledgeNode> root_nodes;

	public KnowledgeSet(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;

		this.nodes = new ArrayList<KnowledgeNode>();
	}

    public static KnowledgeSet find(String set_id){
        return BaseParse.fileParse.node_set_map.get(set_id);
    }

    @Override
    public boolean is_learned() {
        // TODO 未实现
        return EsharePreference.get_learned(this.id);
    }

    @Override
    public boolean is_unlocked() {
        for (BaseKnowledgeSet base_set : this.parents){
            if (!base_set.is_learned()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void do_learn() {}

    public boolean required_nodes_is_learned(){
        boolean is_learned = true;
        for (KnowledgeNode node:this.nodes){
            if (node.required){
                is_learned = is_learned && node.is_learned();
            }
        }
        return  false||is_learned;
    }
}
