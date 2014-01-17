package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.question.IChoice;

import java.io.Serializable;

/**
 * Created by fushang318 on 13-11-5.
 */
public class QuestionChoice implements Serializable, IChoice {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int index;
    protected String sym;
    protected String content;

    public QuestionChoice(int index, String sym, String content){
        this.index = index;
        this.sym = sym;
        this.content = content;
    }
    
    @Override
    public String content() {
        return null == content ? "" : content;
    }

    @Override
    public String sym() {
        return sym;
    }
}
