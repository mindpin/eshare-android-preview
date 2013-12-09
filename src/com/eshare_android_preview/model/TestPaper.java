package com.eshare_android_preview.model;

import com.eshare_android_preview.model.knowledge.base.TestPaperTarget;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fushang318 on 13-12-6.
 */
public class TestPaper implements Serializable{
    public TestPaperTarget target;
    public TestResult test_result;
    private ArrayList<Integer> expect_ids = new ArrayList<Integer>();

    public TestPaper(TestPaperTarget target, TestResult test_result){
        this.target = target;
        this.test_result = test_result;
    }

    public Question get_next_question(){
        Question question = this.target.get_random_question(expect_ids);
        expect_ids.add(question.id);
        return question;
    }

}
