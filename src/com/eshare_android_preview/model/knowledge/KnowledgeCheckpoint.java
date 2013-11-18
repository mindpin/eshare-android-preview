package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.preferences.EsharePreference;

import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet implements ILearn{
	public String id;
	public List<KnowledgeSet> learned_sets;
	public String getId() {
		return id;
	}
	
	public KnowledgeCheckpoint(String id, List<KnowledgeSet> learned_sets) {
		super();
		this.id = id;
		this.learned_sets = learned_sets;
	}

    @Override
    public boolean is_learned() {
        // TODO 未实现
        return EsharePreference.get_value(this.id);
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        boolean parent_learned = true;
        for (BaseKnowledgeSet base_set : this.parents){
            if (base_set.getClass() == KnowledgeCheckpoint.class){
                parent_learned = parent_learned && ((KnowledgeCheckpoint)base_set).is_learned();
            }
        }
        return false || parent_learned;
    }

    @Override
    public void do_learn() {
        EsharePreference.put_boolean(this.id,true);
    }
}
