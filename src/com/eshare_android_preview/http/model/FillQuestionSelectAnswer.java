package com.eshare_android_preview.http.model;

import java.util.HashMap;

/**
 * Created by fushang318 on 13-11-11.
 */
public class FillQuestionSelectAnswer extends QuestionSelectAnswer {
    public Question question;
    public HashMap<Integer,QuestionChoice> select_choices = new HashMap<Integer, QuestionChoice>();

    public FillQuestionSelectAnswer(Question question){
        this.question = question;
    }

    @Override
    public void set_choice(int num, QuestionChoice select_choice){
        if(select_choice == null){
            select_choices.remove(num);
        }else{
            select_choices.put(num, select_choice);
        }
    }

    @Override
    public boolean is_correct(){
        return question.answer.equals(select_answer());
    }

    @Override
    public boolean is_empty(){
        return select_choices.size() == 0;
    }


    private String select_answer(){
        String select_answer = "";
        for(int i=1; i<=select_choices.size(); i++){
            QuestionChoice choice = select_choices.get(i);
            String current_choice_answer = choice == null ? "" : choice.content;
            select_answer += current_choice_answer;
            if(i != select_choices.size()){
                select_answer += ",";
            }
        }
        return select_answer;
    }
}
