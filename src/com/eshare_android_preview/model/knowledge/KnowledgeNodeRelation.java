package com.eshare_android_preview.model.knowledge;


public class KnowledgeNodeRelation {
	public KnowledgeNode parent;
	public KnowledgeNode child;
	public KnowledgeNodeRelation(KnowledgeNode parent, KnowledgeNode child) {
		super();
		this.parent = parent;
		this.child = child;
	}
}
