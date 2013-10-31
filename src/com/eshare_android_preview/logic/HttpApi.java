package com.eshare_android_preview.logic;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.R;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.Note;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouriteDBHelper;
import com.eshare_android_preview.model.database.NoteDBHelper;
import com.eshare_android_preview.model.database.PlanDBHelper;
import com.eshare_android_preview.model.parse.KnowledgeNet;
import com.eshare_android_preview.model.parse.YAMLParse;

public class HttpApi {
	public static final String xml_path = "javascript_core.xml";
	public static final String yaml_path = "javascript_core_knowledge_questions.yaml";
	public static final String course_xml_path = "course_names.xml";
	
	public static List<KnowledgeCategory> get_knowledge_net_category(){
		List<KnowledgeCategory> categorys = new ArrayList<KnowledgeCategory>();
        KnowledgeCategory[] cates = {
                new KnowledgeCategory("Javascript", R.drawable.lan_js),
                new KnowledgeCategory("Rails",      R.drawable.lan_rails),
                new KnowledgeCategory("HTML5",      R.drawable.lan_html5),
                new KnowledgeCategory("MongoDB",    R.drawable.lan_mongodb),
                new KnowledgeCategory("C++",        R.drawable.lan_cpp),
                new KnowledgeCategory("PHP",        R.drawable.lan_php),
                new KnowledgeCategory(".NET",       R.drawable.lan_dotnet),
                new KnowledgeCategory("Sinatra",    R.drawable.lan_sinatra),
        };

		for (int i = 0; i < cates.length; i++) {
			categorys.add(cates[i]);
		}
		return categorys;
	}

    public static class KnowledgeCategory {
        public String name;
        public int res_id;

        public KnowledgeCategory(String name, int res_id) {
            this.name = name;
            this.res_id = res_id;
        }
    }

	
	public static List<KnowledgeNetNode> get_nodes(String category){
		List<KnowledgeNetNode> list = null;
		if (category.equals("javascript")) {
			list = KnowledgeNet.parse_xml(xml_path);
		}
		return list;
	}
	
	public static KnowledgeNetNode find_by_id(String node_id){
		List<KnowledgeNetNode> list = get_nodes("javascript");
		for(KnowledgeNetNode node:list){
			if (node.node_id .equals(node_id)) {
				return node;
			}
		}
		return null;
	}
	
	public static List<KnowledgeNetNode> get_nodes_by_node_ids(List<String> node_ids){
		return KnowledgeNet.array_node_list(KnowledgeNet.parse_xml(xml_path), node_ids);
	}
	
	public static List<Question> get_questions() {
		return YAMLParse.parse_yaml(yaml_path);
	}
	
	public static List<Note> get_notes_list(){
		return NoteDBHelper.all();
	}
	
	public static boolean create_notes(Note note){
		NoteDBHelper.create(note);
		return true;
	}

	public static List<Plan> get_plan_checked(String string) {
		return PlanDBHelper.find_by_checked(string);
	}
	public static Plan find_by_id(int plan_id ){
		return PlanDBHelper.find_by_id(plan_id);
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

    public static Plan plan_find_by(int plan_id) {
        List<Plan> list = get_plan_all();
        for (Plan plan : list) {
            if (plan_id == plan.id) {
                return plan;
            }
        }
        return null;
    }

    public static List<Favourite> get_favourites(){
        return FavouriteDBHelper.all();
    }





    public static boolean create_favourite(Favourite favourite){
        FavouriteDBHelper.create(favourite);
        return true;
    }

    public static boolean cancel_favourite(Favourite favourite){
        if (favourite == null) {
            return false;
        }
        FavouriteDBHelper.cancel(favourite);
        return true;
    }

    public static Favourite find_favourite(String favourite_id, String kind) {
        return FavouriteDBHelper.find(favourite_id, kind);
    }
}
