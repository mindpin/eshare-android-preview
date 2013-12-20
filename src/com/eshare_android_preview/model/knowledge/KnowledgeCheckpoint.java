package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.knowledge.base.TestPaperTarget;
import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.preferences.EsharePreference;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet implements ILearn,TestPaperTarget {
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
    public boolean is_checkpoint() {
        return true;
    }

    @Override
    public boolean is_learned() {
        return EsharePreference.get_learned(this.id);
    }

    @Override
    public boolean is_unlocked() {
        return true;
    }

    @Override
    public String get_name() {
        return "综合测试";
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

    public String model(){
        return this.getClass().getName();
    }
    public String model_id(){
        return this.id;
    }

    public Question get_random_question(List<Integer> except_ids){
        return null;
    }

    public int node_count(){
        int count = 0;
        for(KnowledgeSet set : this.learned_sets){
            count += set.nodes.size();
        }
        return count;
    }

    public int exp_num(){
        return node_count() * KnowledgeNode.EXP_NUM;
    }
}
