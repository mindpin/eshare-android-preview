package com.eshare_android_preview.logic;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.model.Notes;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.NotesDBHelper;
import com.eshare_android_preview.model.database.PlanDBHelper;
import com.eshare_android_preview.model.parse.KnowledgeNet;
import com.eshare_android_preview.model.parse.YAMLParse;

public class HttpApi {
	public static final String xml_path = "javascript_core.xml";
	public static final String yaml_path = "javascript_core_knowledge_questions.yaml";
	public static final String course_xml_path = "course_names.xml";
	
	public static List<String> get_knowledge_net_category(){
		List<String> categorys = new ArrayList<String>();
		String[] cates = {"java",".net","Ruby","C","C++","Php","javascript"};
		for (int i = 0; i < cates.length; i++) {
			categorys.add(cates[i]);
		}
		return categorys;
	}
	
	public static List<Node> get_nodes(String category){
		List<Node> list = null;
		if (category.equals("javascript")) {
			list = KnowledgeNet.parse_xml(xml_path);
		}
		return list;
	}
	
	public static List<Node> get_nodes_by_node_ids(List<String> node_ids){
		return KnowledgeNet.array_node_list(KnowledgeNet.parse_xml(xml_path), node_ids);
	}
	
	public static List<Question> get_questions() {
		return YAMLParse.parse_yaml(yaml_path);
	}
	
	public static List<Notes> get_notes_list(){
		return NotesDBHelper.all();
	}
	
	public static boolean create_notes(Notes notes){
		NotesDBHelper.create(notes);
		return true;
	}

	public static List<Plan> get_plan_checked(String string) {
		return PlanDBHelper.find_by_checked(string);
	}

	public static List<Plan> get_plan_all() {
		return PlanDBHelper.all();
	}

	public static void update_plan(Plan plan) {
		PlanDBHelper.update(plan);
	}

	public static Question question_find_by(int question_id) {
		List<Question> list = get_questions();
		for (Question question : list) {
			if (question_id == question.id) {
				return question;
			}
		}
		return null;
	}
}
