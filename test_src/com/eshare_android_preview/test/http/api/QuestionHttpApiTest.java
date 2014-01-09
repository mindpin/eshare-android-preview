package com.eshare_android_preview.test.http.api;

import android.test.AndroidTestCase;

import com.eshare_android_preview.http.api.QuestionHttpApi;
import com.eshare_android_preview.http.model.Question;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by kaid on 1/9/14.
 */
public class QuestionHttpApiTest extends AndroidTestCase {
    private List<Question> questions;
    private Question question;

    @Override
    public void setUp() throws Exception {
        questions = QuestionHttpApi.get_random_questions("javascript", "node-3");
        question = QuestionHttpApi.get_random_question("javascript", "node-3");
    }

    @Override
    public void tearDown() {
        questions = null;
        question = null;
    }

    public void test_get_random_question() {
        Assert.assertNotNull(question.content);
        Assert.assertTrue(question.content.size() > 0);
        Assert.assertNotNull(question.knowledge_net_id);
        Assert.assertNotNull(question.knowledge_node_id);
        Assert.assertNotNull(question.choices);
        Assert.assertTrue(question.choices.size() > 0);
        Assert.assertNotNull(question.answer);
        Assert.assertNotNull(question.difficulty);
        Assert.assertNotNull(question.kind);
        Assert.assertEquals(question.content.get(0).getClass(), Question.ContentToken.class);
    }

    public void test_get_random_questions() {
        question = questions.get(0);
        Assert.assertNotNull(question.content);
        Assert.assertTrue(question.content.size() > 0);
        Assert.assertNotNull(question.knowledge_net_id);
        Assert.assertNotNull(question.knowledge_node_id);
        Assert.assertNotNull(question.choices);
        Assert.assertTrue(question.choices.size() > 0);
        Assert.assertNotNull(question.answer);
        Assert.assertNotNull(question.difficulty);
        Assert.assertNotNull(question.kind);
        Assert.assertEquals(question.content.get(0).getClass(), Question.ContentToken.class);
    }
}
