package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.knowledge.base.IParentAndChild;


@SuppressWarnings("rawtypes")
public class KnowledgeNode implements IParentAndChild{
	public String node_set_id;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	public List<KnowledgeNodeRelation> relations;
	public List<KnowledgeNode> parents;
	public List<KnowledgeNode> children;

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

	@Override
	public List relations() {
		return relations;
	}

	@Override
	public List parents() {
		return parents;
	}

	@Override
	public List children() {
		return children;
	}
}
