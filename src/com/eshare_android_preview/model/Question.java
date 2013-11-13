package com.eshare_android_preview.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.parse.YAMLParse;

// 问题
public class Question extends LearningResource implements Serializable {
    public static final String YAML_PATH = "javascript_core_knowledge_questions.yaml";
    public static List<Question> QUESTIONS = YAMLParse.parse_yaml(YAML_PATH);
    public static class Type{
		public static final String   SINGLE_CHOICE = "single_choice";
		public static final String   MULTIPLE_CHOICE = "multiple_choices";
		public static final String   TRUE_FALSE = "true_false";
        public static final String   FILL = "fill";
	}

	private static final long serialVersionUID = 111111L;
	public int id;
	public String knowledge_node_id;
	public String kind;
	public String content;
	public List<QuestionChoice> choices_list;
	public String answer;


	// 选择题
	public Question(int id,String knowledge_node_id, String kind, String content, List<QuestionChoice> choices_list, String answer) {
		super();
		this.id = id;
		this.knowledge_node_id = knowledge_node_id;
		this.kind = kind;
		this.content = content;
		this.choices_list = choices_list;
		this.answer = answer;
	}
	
    public boolean is_single_choice(){
        return this.kind.equals(Type.SINGLE_CHOICE);
    }

    public boolean is_multiple_choice(){
        return this.kind.equals(Type.MULTIPLE_CHOICE);
    }

    public boolean is_true_false(){
        return this.kind.equals(Type.TRUE_FALSE);
    }


    public boolean is_fill(){
        return this.kind.equals(Type.FILL);
    }

    public static List<Question> all(){
        return QUESTIONS;
    }
    
    public static Question first(){
        Log.d("ddddd =", all().size() + "");
    	return all().get(0);
    }

    public static Question find(int question_id) {
    	return find_by_jump_index(question_id,0);
    }
    
    public Question next(){
        return all().get(this.id+1);
    }
    
    public boolean answer(String answer){
    	return this.answer.equals(answer);
    }

    public List<QuestionChoice> select_choice_by_str(String answer){
        List<QuestionChoice> result = new ArrayList<QuestionChoice>();
        String[] symbols = answer.split("");
        for(QuestionChoice choice : this.choices_list){
            for(String symbol : symbols){
                if(symbol.equals(choice.sym)){
                    result.add(choice);
                }
            }
        }
        return result;
    }
    
    
    private static Question find_by_jump_index(int question_id,int index){
    	 List<Question> list = all();
    	 for (int i = 0; i < list.size(); i++) {
			if (list.get(i).id == question_id) {
				int current = i +index;
				if (current >= 0 && current <= list.size()) {
					return list.get(i+index);
				}
			}
		}
    	return null;
    }

    @Override
    public String get_note_foreign_key_id() {
        return this.id + "";
    }

    public String get_kind_str() {
        if (is_single_choice()) return "单选题";
        if (is_multiple_choice()) return "多选题";
        if (is_true_false()) return "判断题";
        if (is_fill()) return "填空题";
        return "单选题";
    }

    public QuestionSelectAnswer build_select_answer(){
        if(is_single_choice()){
            return new SingleChoiceQuestionSelectAnswer(this);
        }
        if(is_multiple_choice()){
            return new MultipleChoiceQuestionSelectAnswer(this);
        }
        if(is_true_false()){
            return new TrueFalseQuestionSelectAnswer(this);
        }
        if(is_fill()){
            return new FillQuestionSelectAnswer(this);
        }
        return null;
    }
}
