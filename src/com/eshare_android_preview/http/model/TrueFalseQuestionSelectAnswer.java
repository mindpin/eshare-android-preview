package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.question.IChoice;

/**
 * Created by fushang318 on 13-11-11.
 */
public class TrueFalseQuestionSelectAnswer extends QuestionSelectAnswer {
    public Question question;
    public IChoice select_choice;

    public TrueFalseQuestionSelectAnswer(Question question){
        this.question = question;
    }

    @Override
    public void set_choice(IChoice select_choice){
        this.select_choice = select_choice;
    }

    @Override
    public boolean is_correct(){
        return select_choice.sym().equals(question.answer);
    }

    @Override
    public boolean is_empty(){
        return select_choice == null;
    }

}
