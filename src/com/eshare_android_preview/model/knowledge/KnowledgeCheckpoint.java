package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet{
	public String id;
	public List<KnowledgeSet> learned_sets;
	public String getId() {
		return id;
	}
	
	public KnowledgeCheckpoint(String id, List<KnowledgeSet> learned_sets) {
		super();
		this.id = id;
		this.learned_sets = learned_sets;
		
		this.relations = new ArrayList<KnowledgeSetRelation>();
		this.parents = new ArrayList<BaseKnowledgeSet>();
		this.children = new ArrayList<BaseKnowledgeSet>();
	}
}
