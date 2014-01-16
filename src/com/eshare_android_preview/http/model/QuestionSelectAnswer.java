package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.question.IChoice;

/**
 * Created by Administrator on 13-11-11.
 */
public class QuestionSelectAnswer {
    public boolean is_correct(){
        throw new UnsupportedOperationException();
    }

    public boolean is_empty(){
        throw new UnsupportedOperationException();
    }

    public void set_choice(IChoice select_choice){
        throw new UnsupportedOperationException();
    }

    public void add_or_remove_choice(IChoice select_choice){
        throw new UnsupportedOperationException();
    }

    public void set_choice(int num, IChoice select_choice){
        throw new UnsupportedOperationException();
    }
}
