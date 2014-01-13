package com.eshare_android_preview.model.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.OldFillQuestionChoice;
import com.eshare_android_preview.model.OldQuestion;
import com.eshare_android_preview.model.OldQuestionChoice;

public class QuestionJSONParse {
    public static HashMap<String, OldQuestion> map = new HashMap<String, OldQuestion>();

    public static OldQuestion parse(String json_path,int question_id) {
        OldQuestion question = map.get(json_path);
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
            List<OldQuestionChoice> choices_list = getQuestionChoices(json_obj, kind);

            OldQuestion q = new OldQuestion(question_id, knowledge_node_id.toString(), kind.toString(), content.toString(), choices_list, answer.toString());
            map.put(json_path,q);
            return q;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<OldQuestionChoice> getQuestionChoices(JSONObject json_obj, Object kind) throws JSONException {
        List<OldQuestionChoice> choices_list = new ArrayList<OldQuestionChoice>();

        if (kind.equals(OldQuestion.Type.SINGLE_CHOICE) ||
            kind.equals(OldQuestion.Type.MULTIPLE_CHOICE)
           ) {
            String[] choices_sym = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P".split(",");
            JSONArray choices = json_obj.getJSONArray("choices");
            for (int j = 0; j < choices.length(); j++) {
                OldQuestionChoice qc = new OldQuestionChoice(j, choices_sym[j], choices.get(j).toString());
                choices_list.add(qc);
            }
        }

        if(kind.equals(OldQuestion.Type.FILL)){
            JSONArray choices = json_obj.getJSONArray("choices");
            for (int j = 0; j < choices.length(); j++) {
                OldQuestionChoice qc = new OldFillQuestionChoice(j, choices.get(j).toString());
                choices_list.add(qc);
            }
        }

        if (kind.equals(OldQuestion.Type.TRUE_FALSE)) {
            choices_list.add(new OldQuestionChoice(0, "T", "正确"));
            choices_list.add(new OldQuestionChoice(1, "F", "错误"));
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
