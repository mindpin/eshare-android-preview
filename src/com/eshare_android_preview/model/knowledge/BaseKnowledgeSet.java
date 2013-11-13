package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;


public class BaseKnowledgeSet{
	public List<KnowledgeSetRelation> relations = new ArrayList<KnowledgeSetRelation>();
	public List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();
	public List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
}
