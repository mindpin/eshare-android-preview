package com.eshare_android_preview.model.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import android.content.res.AssetManager;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.model.FillQuestionChoice;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;

public class YAMLParse {
    public static List<Question> parse_yaml(String yaml_path) {
        List<Question> list = new ArrayList<Question>();
        try {
            AssetManager asset = EshareApplication.context.getAssets();
            InputStream inputStream = asset.open(yaml_path);
            Yaml yaml = new Yaml();
            ArrayList<?> object = (ArrayList<?>) yaml.load(inputStream);
            @SuppressWarnings("unchecked")
            ArrayList<HashMap<String, JSONObject>> hms = (ArrayList<HashMap<String, JSONObject>>) object.get(1);
            for (int i = 0; i < hms.size(); i++) {
                HashMap<String, JSONObject> hashMap = hms.get(i);
                Object knowledge_node_id = hashMap.get("knowledge_node_id");
                Object kind = hashMap.get("kind");
                Object title = hashMap.get("title");

                String[] choices_sym = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P".split(",");
                List<QuestionChoice> choices_list = new ArrayList<QuestionChoice>();
                Object answer = "";

                if (kind.equals(Question.Type.SINGLE_CHOICE) ||
                    kind.equals(Question.Type.MULTIPLE_CHOICE)
                   ) {
                    Object choices = hashMap.get("choices");
                    ArrayList<?> choices_list_hm = (ArrayList<?>) choices;
                    for (int j = 0; j < choices_list_hm.size(); j++) {
                        QuestionChoice qc = new QuestionChoice(j, choices_sym[j], choices_list_hm.get(j).toString());
                        choices_list.add(qc);
                    }
                    answer = hashMap.get("answer");
                }

                if(kind.equals(Question.Type.FILL)){
                    Object choices = hashMap.get("choices");
                    ArrayList<?> choices_list_hm = (ArrayList<?>) choices;
                    for (int j = 0; j < choices_list_hm.size(); j++) {
                        QuestionChoice qc = new FillQuestionChoice(j, choices_list_hm.get(j).toString());
                        choices_list.add(qc);
                    }
                    answer = hashMap.get("answer");
                }

                if (kind.equals(Question.Type.TRUE_FALSE)) {
                    answer = hashMap.get("answer");
                    choices_list.add(new QuestionChoice(0, "T", "正确"));
                    choices_list.add(new QuestionChoice(1, "F", "错误"));
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
