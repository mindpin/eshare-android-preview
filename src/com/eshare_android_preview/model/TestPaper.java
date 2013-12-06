package com.eshare_android_preview.model;

import com.eshare_android_preview.model.knowledge.base.BaseKnowledge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 13-12-6.
 */
public class TestPaper implements Serializable{
    public TestResult test_result;
    private ArrayList<Integer> expect_question_ids = new ArrayList<Integer>();

    public TestPaper(TestResult test_result){
        this.test_result = test_result;
    }

    public Question get_current_question(){
        return null;
    }

    public void next(){
    }

}
