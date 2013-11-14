package com.eshare_android_preview.model.knowledge;

public class KnowledgeSetRelation{
	public BaseKnowledgeSet parent;
	public BaseKnowledgeSet child;
	public KnowledgeSetRelation(BaseKnowledgeSet parent, BaseKnowledgeSet child) {
		super();
		this.parent = parent;
		this.child = child;
	}
}
