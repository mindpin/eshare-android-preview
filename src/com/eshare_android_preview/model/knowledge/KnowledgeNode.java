package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.knowledge.base.IParentAndChild;
import com.eshare_android_preview.model.preferences.EsharePreference;

import junit.framework.Assert;


public class KnowledgeNode implements IParentAndChild<KnowledgeNodeRelation,KnowledgeNode>,ILearn {
	public BaseKnowledgeSet base_node_set;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	private List<KnowledgeNodeRelation> relations;
	private List<KnowledgeNode> parents;
	private List<KnowledgeNode> children;

	public KnowledgeNode(BaseKnowledgeSet base_node_set, String id, String name,
			String required, String desc) {
		super();
		this.base_node_set = base_node_set;
		this.id = id;
		this.name = name;
		this.required = required.equals("true");
		this.desc = desc;
		
		this.relations = new ArrayList<KnowledgeNodeRelation>();
		this.parents = new ArrayList<KnowledgeNode>();
		this.children = new ArrayList<KnowledgeNode>();
	}

	@Override
	public List<KnowledgeNodeRelation> relations() {
		return relations;
	}

	@Override
	public List<KnowledgeNode> parents() {
		return parents;
	}

	@Override
	public List<KnowledgeNode> children() {
		return children;
	}

    @Override
    public boolean is_learned() {
        // TODO 未实现
        return EsharePreference.get_value(this.id);
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        KnowledgeSet set = (KnowledgeSet)this.base_node_set;
        if (set.getClass() == KnowledgeSet.class){
            if (this.parents().size()==0 && set.parents.size() ==0){
                return true;
            }
            if (set.is_learned()){
                return true;
            }
        }
        boolean parent_learned = true;
        for (KnowledgeNode node:this.parents){
            parent_learned = parent_learned && node.is_learned();
        }
        return false || parent_learned;
    }

    @Override
    public void do_learn() {
        // TODO 未实现
        EsharePreference.put_boolean(this.id,true);
            
    }
}
