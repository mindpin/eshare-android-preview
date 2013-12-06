package com.eshare_android_preview.model;

import com.eshare_android_preview.model.knowledge.base.BaseKnowledge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fushang318 on 13-12-6.
 */
public class TestPaper implements Serializable{
    public List<Question> questions;
    public TestResult test_result;
    private int current_index = 0;

    public TestPaper(List<Question> questions, TestResult test_result){
        this.questions = questions;
        this.test_result = test_result;
    }

    public Question get_current_question(){
        return questions.get(current_index);
    }

    public void next(){
        this.current_index+=1;
    }

}
