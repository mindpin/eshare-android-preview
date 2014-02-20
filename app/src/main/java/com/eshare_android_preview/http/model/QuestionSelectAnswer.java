package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.question.IChoice;

/**
 * Created by Administrator on 13-11-11.
 */
public interface QuestionSelectAnswer {
    public boolean is_correct();

    public boolean is_empty();

    public void set_choice(IChoice select_choice);

    public void add_or_remove_choice(IChoice select_choice);

    public void set_choice(int num, IChoice select_choice);
}
