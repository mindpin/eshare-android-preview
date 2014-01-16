package com.eshare_android_preview.http.model;


import com.eshare_android_preview.http.i.question.IChoice;
import com.eshare_android_preview.http.i.question.IQuestion;

/**
 * Created by fushang318 on 13-11-11.
 */
public class SingleChoiceQuestionSelectAnswer implements QuestionSelectAnswer {
    public IQuestion question;
    public IChoice select_choice;

    public SingleChoiceQuestionSelectAnswer(IQuestion question){
        this.question = question;
    }

    @Override
    public void set_choice(IChoice select_choice){
        this.select_choice = select_choice;
    }

    @Override
    public boolean is_correct(){
        return select_choice.sym().equals(question.answer());
    }

    @Override
    public boolean is_empty(){
        return select_choice == null;
    }

    @Override
    public void add_or_remove_choice(IChoice select_choice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set_choice(int num, IChoice select_choice) {
        throw new UnsupportedOperationException();
    }
}
