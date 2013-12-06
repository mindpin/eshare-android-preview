package com.eshare_android_preview.model.knowledge;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.knowledge.base.BaseKnowledge;
import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.knowledge.base.IParentAndChild;
import com.eshare_android_preview.model.parse.QuestionYAMLParse;
import com.eshare_android_preview.model.preferences.EsharePreference;


public class KnowledgeNode implements IParentAndChild<KnowledgeNodeRelation,KnowledgeNode>,ILearn,BaseKnowledge {
	public KnowledgeSet set;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	private List<KnowledgeNodeRelation> relations;
	private List<KnowledgeNode> parents;
	private List<KnowledgeNode> children;

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

    public static KnowledgeNode find(String node_id){
        return BaseParse.fileParse.node_map.get(node_id);
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

    public Question get_random_question(List<Integer> except_ids){
        AssetManager asset = EshareApplication.context.getAssets();
        String path = "questions/" + this.id.replace("-","_") + "/yaml";
        List<String> question_files = new ArrayList<String>();
        try {
            String[] a = asset.list(path);
            question_files = new ArrayList<String>(Arrays.asList(a));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int id : except_ids){
            String str = id + ".yaml";
            question_files.remove(str);
            String str1 = str + "";
        }
        if(question_files.size() == 0){
            return null;
        }

        int random_index = (int) (Math.random() * (question_files.size() - 1));
        String file_name = question_files.get(random_index);
        String file_path = path + "/" + file_name;
        int question_id = Integer.parseInt(file_name.replace(".yaml",""));
        return QuestionYAMLParse.parse_yaml(question_id,file_path);
    }
}
