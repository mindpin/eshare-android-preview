package com.eshare_android_preview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 问题
public class OldQuestion implements Serializable {
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
	public List<OldQuestionChoice> choices_list;
	public String answer;


	// 选择题
	public OldQuestion(int id, String knowledge_node_id, String kind, String content, List<OldQuestionChoice> choices_list, String answer) {
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

    public static OldQuestion find(int question_id) {
        return null;
    }

    public boolean is_fill(){
        return this.kind.equals(Type.FILL);
    }

    public boolean answer(String answer){
    	return this.answer.equals(answer);
    }

    public List<OldQuestionChoice> select_choice_by_str(String answer){
        List<OldQuestionChoice> result = new ArrayList<OldQuestionChoice>();
        String[] symbols = answer.split("");
        for(OldQuestionChoice choice : this.choices_list){
            for(String symbol : symbols){
                if(symbol.equals(choice.sym)){
                    result.add(choice);
                }
            }
        }
        return result;
    }
    
    public String get_kind_str() {
        if (is_single_choice()) return "单选题";
        if (is_multiple_choice()) return "多选题";
        if (is_true_false()) return "判断题";
        if (is_fill()) return "填空题";
        return "单选题";
    }

    public String get_kind_desc_str() {
        if (is_single_choice()) return "选择唯一的正确答案";
        if (is_multiple_choice()) return "选择多个正确答案";
        if (is_true_false()) return "判断以下说法的对错";
        if (is_fill()) return "选择正确的选项来完成填空";
        return "选择唯一的正确答案";
    }

    public OldQuestionSelectAnswer build_select_answer(){
        if(is_single_choice()){
            return new OldSingleChoiceQuestionSelectAnswer(this);
        }
        if(is_multiple_choice()){
            return new OldMultipleChoiceQuestionSelectAnswer(this);
        }
        if(is_true_false()){
            return new OldTrueFalseQuestionSelectAnswer(this);
        }
        if(is_fill()){
            return new OldFillQuestionSelectAnswer(this);
        }
        return null;
    }

    public int choice_max_length(){
        int length = 0;
        for(OldQuestionChoice c : this.choices_list){
            length = Math.max(length, c.content.length());
        }
        return length;
    }

    public boolean is_choice_contain_image() {
        String content = choices_list.get(0).content;
        String f = "![file:///";
        return content.indexOf(f) >= 0;
    }
}
