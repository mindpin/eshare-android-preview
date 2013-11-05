package com.eshare_android_preview.model;

import java.io.Serializable;

/**
 * Created by fushang318 on 13-11-5.
 */
public class QuestionChoice implements Serializable {
    public int index;
    public String sym;
    public String content;

    public QuestionChoice(int index, String sym, String content){
        this.index = index;
        this.sym = sym;
        this.content = content;
    }
}
