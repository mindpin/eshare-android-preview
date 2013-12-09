package com.eshare_android_preview.model.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import android.content.res.AssetManager;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.FillQuestionChoice;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;

public class QuestionJSONParse {
    public static HashMap<String, Question> map = new HashMap<String, Question>();

    public static Question parse(String json_path,int question_id) {
        Question question = map.get(json_path);
        if(question != null){
            return question;
        }
        try {
            AssetManager asset = EshareApplication.context.getAssets();
            InputStream inputStream = asset.open(json_path);
            String json = BaseUtils.convert_stream_to_string(inputStream);

            JSONObject json_obj = new JSONObject(json);

            Object knowledge_node_id = json_obj.get("knowledge_node_id");
            Object kind = json_obj.get("kind");
            Object content = json_obj.get("content");
            Object answer = json_obj.get("answer");
            List<QuestionChoice> choices_list = getQuestionChoices(json_obj, kind);

            Question q = new Question(question_id, knowledge_node_id.toString(), kind.toString(), content.toString(), choices_list, answer.toString());
            map.put(json_path,q);
            return q;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<QuestionChoice> getQuestionChoices(JSONObject json_obj, Object kind) throws JSONException {
        List<QuestionChoice> choices_list = new ArrayList<QuestionChoice>();

        if (kind.equals(Question.Type.SINGLE_CHOICE) ||
            kind.equals(Question.Type.MULTIPLE_CHOICE)
           ) {
            String[] choices_sym = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P".split(",");
            JSONArray choices = json_obj.getJSONArray("choices");
            for (int j = 0; j < choices.length(); j++) {
                QuestionChoice qc = new QuestionChoice(j, choices_sym[j], choices.get(j).toString());
                choices_list.add(qc);
            }
        }

        if(kind.equals(Question.Type.FILL)){
            JSONArray choices = json_obj.getJSONArray("choices");
            for (int j = 0; j < choices.length(); j++) {
                QuestionChoice qc = new FillQuestionChoice(j, choices.get(j).toString());
                choices_list.add(qc);
            }
        }

        if (kind.equals(Question.Type.TRUE_FALSE)) {
            choices_list.add(new QuestionChoice(0, "T", "正确"));
            choices_list.add(new QuestionChoice(1, "F", "错误"));
        }
        return choices_list;
    }

    public static String get_json_path_by_id(String node_id, int question_id) {
        String path = "questions/" + node_id.replace("-","_") + "/json/" + question_id + ".json";
        return path;
    }

    public static String get_json_list_path_by_node_id(String node_id){
        String path = "questions/" + node_id.replace("-","_") + "/json";
        return path;
    }
}
