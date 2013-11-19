package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.preferences.EsharePreference;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet implements ILearn{
	public String id;
	public List<KnowledgeSet> learned_sets;

	public KnowledgeCheckpoint(String id, List<KnowledgeSet> learned_sets) {
		super();
		this.id = id;
		this.learned_sets = learned_sets;
	}

    public static KnowledgeCheckpoint find(String checkpoint_id){
        return BaseParse.fileParse.check_point_map.get(checkpoint_id);
    }

    @Override
    public boolean is_learned() {
        // TODO 未实现
        return EsharePreference.get_learned(this.id);
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        return true;
    }

    @Override
    public void do_learn() {
        List<String> id_s = new ArrayList<String>();
        for (KnowledgeSet set : this.learned_sets){
            for (KnowledgeNode node: set.nodes){
                id_s.add(node.id);
            }
            id_s.add(set.id);
        }
        id_s.add(this.id);
        EsharePreference.put_learned_array(id_s);
    }

    public void set_learned(){
        boolean is_learned = true;
        for (KnowledgeSet set : this.learned_sets){
            is_learned = is_learned && set.is_learned();
        }
        EsharePreference.put_learned(this.id, false || is_learned);
    }
}
