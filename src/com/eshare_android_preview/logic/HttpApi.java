package com.eshare_android_preview.logic;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.R;
import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.KnowledgeNetNode.KnowledgeCategory;
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

	public static class HANode{
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

		public static List<KnowledgeNetNode> find_by_category(String category){
			List<KnowledgeNetNode> list = null;
			if (category.equals("javascript")) {
				list = KnowledgeNet.parse_xml(xml_path);
			}
			return list;
		}
		
		public static KnowledgeNetNode find_by_id(String node_id){
			List<KnowledgeNetNode> list = find_by_category("javascript");
			for(KnowledgeNetNode node:list){
				if (node.node_id .equals(node_id)) {
					return node;
				}
			}
			return null;
		}
		
		public static List<KnowledgeNetNode> find_by_node_ids(List<String> node_ids){
			return KnowledgeNet.array_node_list(KnowledgeNet.parse_xml(xml_path), node_ids);
		}
	}
	

	public static class HANote{
		public static List<Note> all(){
			return NoteDBHelper.all();
		}
		
		public static boolean create(Note note){
			NoteDBHelper.create(note);
			return true;
		}
	}
	
	public static class HAQuestion{
		public static List<Question> all() {
			return YAMLParse.parse_yaml(yaml_path);
		}
		public static Question find_by_id(int question_id) {
			List<Question> list = all();
			for (Question question : list) {
				if (question_id == question.id) {
					return question;
				}
			}
			return null;
		}
	}
    
    public static class HAPlan{
    	public static List<Plan> get_checked(String string) {
    		return PlanDBHelper.find_by_checked(string);
    	}
    	public static Plan find_by_id(int plan_id ){
    		return PlanDBHelper.find_by_id(plan_id);
    	}
    	public static List<Plan> all() {
    		return PlanDBHelper.all();
    	}

    	public static void update(Plan plan) {
    		PlanDBHelper.update(plan);
    	}
    }
    
    
    public static class HAFavourite{
    	public static List<Favourite> all(){
            return FavouriteDBHelper.all();
        }
    	
        public static boolean create(Favourite favourite){
            FavouriteDBHelper.create(favourite);
            return true;
        }

        public static boolean cancel(Favourite favourite){
            if (favourite == null) {
                return false;
            }
            FavouriteDBHelper.cancel(favourite);
            return true;
        }

        public static Favourite find_by_id_and_kind(String favourite_id, String kind) {
            return FavouriteDBHelper.find(favourite_id, kind);
        }
    }
}
