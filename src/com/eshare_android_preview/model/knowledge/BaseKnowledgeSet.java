package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.knowledge.base.IParentAndChild;


public class BaseKnowledgeSet implements IParentAndChild<KnowledgeSetRelation,BaseKnowledgeSet>{
	protected List<KnowledgeSetRelation> relations = new ArrayList<KnowledgeSetRelation>();
    protected List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();
    protected List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();

	@Override
	public List<KnowledgeSetRelation> relations() {
		return relations;
	}

	@Override
	public List<BaseKnowledgeSet> parents() {
		return parents;
	}

	@Override
	public List<BaseKnowledgeSet> children() {
		return children;
	}
}
