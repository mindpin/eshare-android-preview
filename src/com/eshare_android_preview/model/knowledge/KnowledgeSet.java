package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.model.TestResult;
import com.eshare_android_preview.model.knowledge.base.BaseKnowledge;
import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.preferences.EsharePreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeSet extends BaseKnowledgeSet implements ILearn,BaseKnowledge{
	public String name;
	public String icon;

	public List<KnowledgeNode> nodes;
    public List<KnowledgeNode> root_nodes;

	public KnowledgeSet(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;

		this.nodes = new ArrayList<KnowledgeNode>();
	}

    public static KnowledgeSet find(String set_id){
        return BaseParse.fileParse.node_set_map.get(set_id);
    }

    @Override
    public boolean is_learned() {
        return EsharePreference.get_learned(this.id);
    }

    @Override
    public boolean is_unlocked() {
        for (BaseKnowledgeSet base_set : this.parents){
            if (!base_set.is_learned()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void do_learn() {}

    public boolean required_nodes_is_learned(){
        for (KnowledgeNode node:this.nodes){
            if (node.required && !node.is_learned()){
              return false;
            }
        }
        return  true;
    }

    @Override
    public boolean is_checkpoint() {
        return false;
    }

    @Override
    public String get_name() {
        return name;
    }

    // 返回已学的节点数
    public int get_learned_nodes_count(){
        int learned_nodes_count = 0 ;
        for (KnowledgeNode node : this.nodes){
            if (node.is_learned()){
                learned_nodes_count++;
            }
        }
        return learned_nodes_count;
    }

    public String model(){
        return this.getClass().getName();
    }

    public String model_id(){
        return this.id;
    }
    public TestPaper get_test_paper(){
        ArrayList<Question> questions = new ArrayList<Question>(Question.all().subList(0, 12));
        return new TestPaper(questions,new TestResult(3,10));
    }
}
