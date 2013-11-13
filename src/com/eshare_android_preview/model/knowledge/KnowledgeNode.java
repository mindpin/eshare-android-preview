package com.eshare_android_preview.model.knowledge;

import java.util.HashMap;


public class KnowledgeNode{
	public String node_set_id;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	public HashMap<String, KnowledgeNodeRelation> relations;
	public HashMap<String, KnowledgeNode> parents;
	public HashMap<String, KnowledgeNode> children;

	public KnowledgeNode(String node_set_id, String id, String name,
			String required, String desc) {
		super();
		this.node_set_id = node_set_id;
		this.id = id;
		this.name = name;
		this.required = required.equals("true");
		this.desc = desc;
		
		this.relations = new HashMap<String, KnowledgeNodeRelation>();
		this.parents = new HashMap<String, KnowledgeNode>();
		this.children = new HashMap<String, KnowledgeNode>();
	}
}
