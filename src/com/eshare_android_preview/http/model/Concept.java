package com.eshare_android_preview.http.model;

import java.util.List;

import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.http.i.question.IQuestion;

public class Concept implements IConcept {

	@Override
	public String get_name() {
		return null;
	}

	@Override
	public String get_desc() {
		return null;
	}

	@Override
	public int get_practices_count() {
		return 0;
	}

	@Override
	public List<IQuestion> get_learned_node_random_questions(int count) {
		return null;
	}

}
