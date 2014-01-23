package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.api.QuestionHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.http.i.knowledge.ICanbeLearned;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;
import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.http.i.question.IQuestionLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-9.
 */
public class KnowledgeNode implements IUserKnowledgeNode, ICanbeLearned,IQuestionLoader {
    private KnowledgeSet set;
    private String id;
    private String name;
    private String desc;
    private boolean required;
    private boolean is_learned;

    private List<IUserKnowledgeNode> children = new ArrayList<IUserKnowledgeNode>();
    private List<IUserKnowledgeNode> parents = new ArrayList<IUserKnowledgeNode>();

    public void add_child(KnowledgeNode child){
        this.children.add(child);
    }

    public void add_parent(KnowledgeNode parent){
        this.parents.add(parent);
    }

    public void set_set(KnowledgeSet set){
        this.set = set;
    }

    public boolean is_root(){
        return this.parents.size() == 0;
    }

    @Override
    public String get_id() {
        return id;
    }

    @Override
    public String get_type() {
        return "KnowledgeNode";
    }

    @Override
    public String get_name() {
        return name;
    }

    @Override
    public String get_desc() {
        return desc;
    }

    @Override
    public boolean get_required() {
        return required;
    }

    @Override
    public List<IUserKnowledgeNode> children() {
        return children;
    }

    @Override
    public List<IUserKnowledgeNode> parents() {
        return parents;
    }

    @Override
    public boolean is_unlocked() {
        if(this.is_root()){
            return set.is_unlocked();
        }
        for(IUserKnowledgeNode node : parents){
            if(!node.is_learned()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean is_learned() {
        return is_learned;
    }

    @Override
    public List<IQuestion> get_questions_remote() {
        String net_id = UserData.instance().get_current_knowledge_net_id();
        try {
            List<Question> qs = QuestionHttpApi.get_random_questions(net_id, this.id);
            return new ArrayList<IQuestion>(qs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void set_learned() {
        this.is_learned = true;
        this.set.refresh_learned_node_count();
    }

	@Override
	public List<IConcept> concepts(boolean remote) {
		// TODO Auto-generated method stub
		return null;
	}
}
