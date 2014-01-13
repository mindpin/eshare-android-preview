package com.eshare_android_preview.http.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaid on 1/9/14.
 */
public class Question implements Serializable {

    public static class Type {
        public static final String   SINGLE_CHOICE = "single_choice";
        public static final String   MULTIPLE_CHOICE = "multiple_choices";
        public static final String   TRUE_FALSE = "true_false";
        public static final String   FILL = "fill";
    }

    public String id;
    public String knowledge_node_id;
    public String knowledge_net_id;
    public String answer;
    public List<String> choices = new ArrayList<String>();
    public List<ContentToken> content;
    public int difficulty;
    public String kind;
    public List<QuestionChoice> choices_list = new ArrayList<QuestionChoice>();

    public static Question from_json(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Question.class).set_choices_list();
    }

    public static List<Question> from_json_array(String json_array) {
        Gson gson = new GsonBuilder().create();
        java.lang.reflect.Type type = new TypeToken<ArrayList<Question>>() {}.getType();
        List<Question> questions = gson.fromJson(json_array, type);
        for (Question question : questions) {
            question.set_choices_list();
        }
        return questions;
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

    public int choice_max_length(){
        int length = 0;
        for(QuestionChoice c : this.choices_list){
            length = Math.max(length, c.content.length());
        }
        return length;
    }

    public boolean is_choice_contain_image() {
        String content = choices_list.get(0).content;
        String f = "![file:///";
        return content.contains(f);
    }

    private Question set_choices_list() {
        if (kind.equals(Question.Type.SINGLE_CHOICE) || kind.equals(Question.Type.MULTIPLE_CHOICE)) {
            String[] choices_sym = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P".split(",");
            for (int j = 0; j < choices.size(); j++) {
                QuestionChoice qc = new QuestionChoice(j, choices_sym[j], choices.get(j));
                choices_list.add(qc);
            }
        }

        if (kind.equals(Question.Type.FILL)) {
            for (int j = 0; j < choices.size(); j++) {
                QuestionChoice qc = new FillQuestionChoice(j, choices.get(j));
                choices_list.add(qc);
            }
        }

        if (kind.equals(Question.Type.TRUE_FALSE)) {
            choices_list.add(new QuestionChoice(0, "T", "正确"));
            choices_list.add(new QuestionChoice(1, "F", "错误"));
        }
        return this;
    }

    public class ContentToken implements Serializable{
        String type;
        HashMap data;
    }
}
