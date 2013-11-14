package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.knowledge.base.IParentAndChild;


@SuppressWarnings("rawtypes")
public class BaseKnowledgeSet implements IParentAndChild{
	public List<KnowledgeSetRelation> relations = new ArrayList<KnowledgeSetRelation>();
	public List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();
	public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
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
