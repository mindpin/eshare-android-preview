package com.eshare_android_preview.logic;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.R;
import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.KnowledgeNetNode.KnowledgeCategory;
import com.eshare_android_preview.model.Note;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouriteDBHelper;
import com.eshare_android_preview.model.database.NoteDBHelper;
import com.eshare_android_preview.model.parse.KnowledgeNet;
import com.eshare_android_preview.model.parse.YAMLParse;

public class HttpApi {
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
