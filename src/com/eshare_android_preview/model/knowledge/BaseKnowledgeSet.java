package com.eshare_android_preview.model.knowledge;

import java.util.List;


public abstract class BaseKnowledgeSet{
	public List<KnowledgeSetRelation> relations;
	public List<BaseKnowledgeSet> parents;
	public List<BaseKnowledgeSet> children;
}
