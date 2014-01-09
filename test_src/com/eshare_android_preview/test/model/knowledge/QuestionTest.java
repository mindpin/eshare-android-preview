package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;
import com.eshare_android_preview.http.model.Question;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kaid on 1/9/14.
 */
public class QuestionTest extends AndroidTestCase {
    private Question question;
    private List<Question> questions;

    @Override
    public void setUp() throws Exception {
        InputStream json_input = getContext().getAssets().open("test_resources/question.json");
        String json = IOUtils.toString(json_input, "UTF-8");
        String json_array = "[" + json + "]";
        json_input.close();
        question = Question.from_json(json);
        questions = Question.from_json_array(json_array);
    }

    @Override
    public void tearDown() throws Exception {
        question = null;
        questions = null;
    }

    public void test_init_question_from_json() {
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

    public void test_init_questions_from_json_array() {
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
