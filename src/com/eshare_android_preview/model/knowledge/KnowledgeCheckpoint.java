package com.eshare_android_preview.model.knowledge;

import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet{
	public String id;
	public List<KnowledgeSet> learned_sets;
    public int deep;

	public KnowledgeCheckpoint(String id, List<KnowledgeSet> learned_sets) {
		super();
		this.id = id;
		this.learned_sets = learned_sets;
	}
}
