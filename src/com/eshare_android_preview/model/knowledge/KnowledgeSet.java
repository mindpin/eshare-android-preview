package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSet extends BaseKnowledgeSet{
	public String id;
	public String name;
	public String icon;
	
	public List<KnowledgeNode> nodes;

	public KnowledgeSet(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		
		this.nodes = new ArrayList<KnowledgeNode>();
		
		this.relations = new ArrayList<KnowledgeSetRelation>();
		this.parents = new ArrayList<BaseKnowledgeSet>();
		this.children = new ArrayList<BaseKnowledgeSet>();
	}
	
	public KnowledgeNode first_node(){
		for (KnowledgeNode node: nodes) {
			if (node.parents.size() == 0) {
				return node;
			}
		}
		return null;
	}
}
