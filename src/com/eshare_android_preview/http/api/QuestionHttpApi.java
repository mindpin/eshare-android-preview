package com.eshare_android_preview.http.api;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.base.EsharePostRequest;
import com.eshare_android_preview.http.base.PostParamText;
import com.eshare_android_preview.http.model.Question;

import java.util.List;

/**
 * Created by kaid on 1/9/14.
 */
public class QuestionHttpApi {

    public static List<Question> get_random_questions(String net_id, String node_id) throws Exception {
        return new EshareGetRequest<List<Question>>(random_questions_url(net_id, node_id)) {

            @Override
            public List<Question> on_success(String response) throws Exception {
                return Question.from_json_array(response);
            }
        }.go();
    }

    public static Question get_random_question(String net_id, String node_id) throws Exception {
        return new EshareGetRequest<Question>(random_question_url(net_id, node_id)) {

            @Override
            public Question on_success(String response) throws Exception {
                return Question.from_json(response);
            }
        }.go();
    }

    public static void do_post_result(String question_id, boolean result){
        try {
            new EsharePostRequest<Void>(
                    "/questions/" + question_id + "/do_answer",
                    new PostParamText("answer", result+"")
            ) {
                @Override
                public Void on_success(String response_text) throws Exception {
                    return null;
                };
                public void on_authenticate_exception() {
                };
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String random_questions_url(String net_id, String node_id) {
        return String.format("/api/knowledge_nets/%s/knowledge_nodes/%s/get_random_questions", net_id, node_id);
    }

    private static String random_question_url(String net_id, String node_id) {
        return String.format("/api/knowledge_nets/%s/knowledge_nodes/%s/get_random_question", net_id, node_id);
    }
}
