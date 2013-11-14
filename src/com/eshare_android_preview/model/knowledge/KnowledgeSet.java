package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSet extends BaseKnowledgeSet{
	public String id;
	public String name;
	public String icon;
	
	public List<KnowledgeNode> nodes;
	public KnowledgeNode first_node;

	public KnowledgeSet(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		
		this.nodes = new ArrayList<KnowledgeNode>();
	}
}
