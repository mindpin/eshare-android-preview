package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.preferences.EsharePreference;

import junit.framework.Assert;

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


    @Override
    public boolean is_learned() {
        // TODO 未实现
        return EsharePreference.get_value(this.id);
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        boolean parent_learned = true;
        for (BaseKnowledgeSet base_set : this.parents){
           if (base_set.getClass() == KnowledgeSet.class){
               parent_learned = parent_learned && ((KnowledgeSet)base_set).is_learned();
           }
        }
        return false || parent_learned;
    }

    @Override
    public void do_learn() {
        EsharePreference.put_boolean(this.id,true);
        for (KnowledgeNode node: this.nodes){
            node.do_learn();
        }
    }
}
