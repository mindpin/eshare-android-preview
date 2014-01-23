package com.eshare_android_preview.http.model;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Concept implements IConcept {
	private String id;
	private String name;
	private String desc;
	private boolean is_learned;
	private boolean is_unlocked;
	private int practicing_count;
	
	@Override
	public String get_name() {
		return this.name;
	}

	@Override
	public String get_desc() {
		return this.desc;
	}

	@Override
	public int get_practices_count() {
		return this.practicing_count;
	}

	@Override
	public List<Question> get_learned_node_random_questions(int count) {
        String url = String.format("/api/concept/%s/learned_node_random_questions?count=%d", this.id, count);

        try {
            return new EshareGetRequest<List<Question>>(url) {
                @Override
                public List<Question> on_success(String response_text) throws Exception {
                    return Question.from_json_array(response_text);
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static List<IConcept> from_json_array(String response_text) {
		Gson gson = new GsonBuilder().create();
		java.lang.reflect.Type type = new TypeToken<ArrayList<Concept>>() {}.getType();
		List<IConcept> concepts = gson.fromJson(response_text, type);
		return concepts;
	}

}
