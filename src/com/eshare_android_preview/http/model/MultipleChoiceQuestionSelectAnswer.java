package com.eshare_android_preview.http.model;

import com.eshare_android_preview.base.utils.BaseUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by fushang318 on 13-11-11.
 */
public class MultipleChoiceQuestionSelectAnswer extends QuestionSelectAnswer {
    public Question question;
    public ArrayList<QuestionChoice> select_choices = new ArrayList<QuestionChoice>();

    public MultipleChoiceQuestionSelectAnswer(Question question){
        this.question = question;
    }

    @Override
    public void add_or_remove_choice(QuestionChoice select_choice){
        if (select_choices.indexOf(select_choice) != -1) {
            select_choices.remove(select_choice);
        } else {
            select_choices.add(select_choice);
        }
    }

    @Override
    public boolean is_correct(){
        String[] answers = question.answer.split("");
        Arrays.sort(answers);

        ArrayList<String> choice_str_list = new ArrayList<String>();
        for(QuestionChoice choice : select_choices){
            choice_str_list.add(choice.sym);
        }
        String[] select_answers = new String[choice_str_list.size()];
        choice_str_list.toArray(select_answers);
        Arrays.sort(select_answers);

        String answers_str = BaseUtils.string_list_to_string(Arrays.asList(answers));
        String select_answers_str = BaseUtils.string_list_to_string(Arrays.asList(select_answers));

        return answers_str.equals(select_answers_str);
    }

    @Override
    public boolean is_empty(){
        return select_choices.isEmpty();
    }
}
