package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.knowledge.base.IParentAndChild;


public class KnowledgeNode implements IParentAndChild<KnowledgeNodeRelation,KnowledgeNode>,ILearn {
	public String node_set_id;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	private List<KnowledgeNodeRelation> relations;
	private List<KnowledgeNode> parents;
	private List<KnowledgeNode> children;

	public KnowledgeNode(String node_set_id, String id, String name,
			String required, String desc) {
		super();
		this.node_set_id = node_set_id;
		this.id = id;
		this.name = name;
		this.required = required.equals("true");
		this.desc = desc;
		
		this.relations = new ArrayList<KnowledgeNodeRelation>();
		this.parents = new ArrayList<KnowledgeNode>();
		this.children = new ArrayList<KnowledgeNode>();
	}

    public static KnowledgeNode find(String node_id){
        return BaseParse.fileParse.node_map.get(node_id);
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
        return false;
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        return false;
    }

    @Override
    public void do_learn() {
        // TODO 未实现
    }
}
