package com.eshare_android_preview.model.knowledge;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.knowledge.base.TestPaperTarget;
import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.knowledge.base.IParentAndChild;
import com.eshare_android_preview.model.parse.QuestionJSONParse;
import com.eshare_android_preview.model.parse.node.KnowledgeNetXMLParse;
import com.eshare_android_preview.model.preferences.EsharePreference;


public class KnowledgeNode implements IParentAndChild<KnowledgeNodeRelation,KnowledgeNode>,ILearn,TestPaperTarget {
    public static final int EXP_NUM = 10;
	public KnowledgeSet set;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	private List<KnowledgeNodeRelation> relations;
	private List<KnowledgeNode> parents;
	private List<KnowledgeNode> children;

    private List<Integer> question_ids = null;

	public KnowledgeNode(KnowledgeSet set, String id, String name,
			String required, String desc) {
		super();
		this.set = set;
		this.id = id;
		this.name = name;
		this.required = required.equals("true");
		this.desc = desc;
		
		this.relations = new ArrayList<KnowledgeNodeRelation>();
		this.parents = new ArrayList<KnowledgeNode>();
		this.children = new ArrayList<KnowledgeNode>();
	}

	@Override
	public List<KnowledgeNodeRelation> relations() {
		return relations;
	}

	@Override
	public List<KnowledgeNode> parents() {
		return parents;
	}

	@Override
	public List<KnowledgeNode> children() {
		return children;
	}

    @Override
    public boolean is_learned() {
        return EsharePreference.get_learned(this.id);
    }

    @Override
    public boolean is_unlocked() {
        if (this.parents().size() == 0){
            return this.set.is_unlocked();
        }

        for (KnowledgeNode node:this.parents){
            if (!node.is_learned()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void do_learn() {
        if (!is_unlocked()){
            return;
        }
        EsharePreference.put_learned(this.id, true);

        boolean required_nodes_is_learned = this.set.required_nodes_is_learned();
        EsharePreference.put_learned(this.set.id,required_nodes_is_learned);
    }

    public String model(){
        return this.getClass().getName();
    }
    public String model_id(){
        return this.id;
    }

    public List<Integer> all_question_ids(){
        if(question_ids != null){
            return new ArrayList<Integer>(question_ids);
        }

        AssetManager asset = EshareApplication.context.getAssets();
        String path = QuestionJSONParse.get_json_list_path_by_node_id(this.id);
        List<Integer> question_ids = new ArrayList<Integer>();
        try {
            String[] file_names = asset.list(path);
            for(String file_name : file_names){
                String id = file_name.replace(".json","");
                question_ids.add(Integer.parseInt(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.question_ids = question_ids;
        return new ArrayList<Integer>(question_ids);
    }

    public Question get_random_question(List<Integer> except_ids){
        List<Integer> all_ids = all_question_ids();

        for(int id : except_ids){
            all_ids.remove((Integer)id);
        }
        if(all_ids.size() == 0){
            return null;
        }

        int random_index = (int) (Math.random() * (all_ids.size() - 1));
        int question_id = all_ids.get(random_index);
        question_id = 3108;

        System.out.println("~~~   " + question_id);
        String json_path = QuestionJSONParse.get_json_path_by_id(this.id, question_id);
        return QuestionJSONParse.parse(json_path, question_id);
    }

    public int exp_num(){
        return EXP_NUM;
    }

    public String get_course(){
        return this.set.net.parse.name;
    }
}
