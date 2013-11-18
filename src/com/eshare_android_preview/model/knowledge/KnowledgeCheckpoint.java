package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.knowledge.base.ILearn;

import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet implements ILearn{
	public String id;
	public List<KnowledgeSet> learned_sets;

	public KnowledgeCheckpoint(String id, List<KnowledgeSet> learned_sets) {
		super();
		this.id = id;
		this.learned_sets = learned_sets;
	}

    @Override
    public boolean is_learned() {
        // TODO 未实现
        return false;
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        return false;
    }

    @Override
    public void do_learn() {
        // TODO 未实现
    }
}
