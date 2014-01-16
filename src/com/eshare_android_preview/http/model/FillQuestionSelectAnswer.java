package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.question.IChoice;
import com.eshare_android_preview.http.i.question.IQuestion;

import java.util.HashMap;

/**
 * Created by fushang318 on 13-11-11.
 */
public class FillQuestionSelectAnswer implements QuestionSelectAnswer {
    public IQuestion question;
    public HashMap<Integer,IChoice> select_choices = new HashMap<Integer, IChoice>();

    public FillQuestionSelectAnswer(IQuestion question){
        this.question = question;
    }

    @Override
    public void set_choice(int num, IChoice select_choice){
        if(select_choice == null){
            select_choices.remove(num);
        }else{
            select_choices.put(num, select_choice);
        }
    }

    @Override
    public boolean is_correct(){
        return question.answer().equals(select_answer());
    }

    @Override
    public boolean is_empty(){
        return select_choices.size() == 0;
    }

    @Override
    public void set_choice(IChoice select_choice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add_or_remove_choice(IChoice select_choice) {
        throw new UnsupportedOperationException();
    }

    private String select_answer(){
        String select_answer = "";
        for(int i=1; i<=select_choices.size(); i++){
            IChoice choice = select_choices.get(i);
            String current_choice_answer = choice == null ? "" : choice.content();
            select_answer += current_choice_answer;
            if(i != select_choices.size()){
                select_answer += ",";
            }
        }
        return select_answer;
    }
}
