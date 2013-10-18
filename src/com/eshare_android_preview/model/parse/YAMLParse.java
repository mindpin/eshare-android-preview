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
import com.eshare_android_preview.model.Question;

public class YAMLParse {
	public static List<Question> parse_yaml(String yaml_path){
		List<Question> list = new ArrayList<Question>();
		try {
			AssetManager asset = EshareApplication.context.getAssets();
			InputStream inputStream = asset.open(yaml_path);
			Yaml yaml = new Yaml();
			ArrayList<?> object = (ArrayList<?>) yaml.load(inputStream);
			ArrayList<HashMap<String, JSONObject>> hms = (ArrayList<HashMap<String, JSONObject>>) object.get(1);
			for (int i = 0; i < hms.size(); i++) {
				HashMap<String, JSONObject> hashMap = hms.get(i);
				Object knowledge_node_id = hashMap.get("knowledge_node_id");
				Object kind = hashMap.get("kind");
				Object title = hashMap.get("title");
				
				List<String> choices_list = new ArrayList<String>();
				Object answer = "";
				if (kind.equals(Question.Type.SINGLE_CHOICE) || kind.equals(Question.Type.MULTIPLE_CHOICE)) {
					Object choices = hashMap.get("choices");
					ArrayList<?> choices_list_hm = (ArrayList<?>) choices;
					for (int j = 0; j < choices_list_hm.size(); j++) {
						choices_list.add(choices_list_hm.get(j).toString());
					}
					answer = hashMap.get("answer");
				}
				
				if (kind.equals(Question.Type.TRUE_FALSE)) {
					answer = hashMap.get("answer");
				}

				if (!kind.equals(Question.Type.CODE)) {
					Question question = new Question(knowledge_node_id.toString(), kind.toString(), title.toString(), choices_list, answer.toString());
					list.add(question);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
